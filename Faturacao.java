import java.io.Serializable;
import java.util.*;

public class Faturacao implements InterfFaturacao, Serializable {
    private Map<String, VendaMensal[]> normal;
    private Map<String, VendaMensal[]> promo;

    public Faturacao(){
        this.normal = new HashMap<>();
        this.promo = new HashMap<>();
    }

    public void adiciona(InterfVenda venda){
        if(venda.isPromo()) {//promoçao
            if(promo.containsKey(venda.getCodPro())){//esta inserida
                //Lista de meses, com as informaçoes de vendas
                VendaMensal[] lista = promo.get(venda.getCodPro());
                //Vendas de um determinado mes
                VendaMensal v = lista[venda.getMes()-1];
                if(v != null){
                    v.insertVenda(venda.getQuant(), venda.getPreco());
                    lista[venda.getMes()-1] = v;
                    promo.put(venda.getCodPro(), lista);
                }else{
                    v = new VendaMensal(venda.getQuant(), venda.getPreco());
                    lista[venda.getMes()-1] = v;
                    promo.put(venda.getCodPro(), lista);
                }
            }else{//caso contrario
                //Inicializar lista de meses
                VendaMensal[] meses = new VendaMensal[12];
                //adicionar uma nova venda mensal à lista
                VendaMensal v = new VendaMensal(venda.getQuant(), venda.getPreco());
                meses[venda.getMes()-1] = v;
                //adicionar a lista ao map
                promo.put(venda.getCodPro(), meses);
            }
        }else{//normal
            if(normal.containsKey(venda.getCodPro())){//esta inserida
                VendaMensal[] lista = normal.get(venda.getCodPro());
                VendaMensal v = lista[venda.getMes()-1];
                if(v != null){
                    v.insertVenda(venda.getQuant(), venda.getPreco());
                    lista[venda.getMes()-1] = v;
                    normal.put(venda.getCodPro(), lista);
                }else{
                    v = new VendaMensal(venda.getQuant(), venda.getPreco());
                    lista[venda.getMes()-1] = v;
                    normal.put(venda.getCodPro(), lista);
                }
            }else{//caso contrario
                //Inicializar lista de meses
                VendaMensal[] meses = new VendaMensal[12];
                //adicionar uma nova venda mensal à lista
                VendaMensal v = new VendaMensal(venda.getQuant(), venda.getPreco());
                meses[venda.getMes()-1] = v;
                //adicionar a lista ao map
                normal.put(venda.getCodPro(), meses);
            }
        }
    }

    public Set<String> getListaOrdenadaProdutosNuncaComprados(InterfCatProds catpro){
        Set<String> prods = catpro.getProdutos();
        Set<String> nuncaComprados = new TreeSet<>(); //comparator para poder ordenar alfabeticamente
        for(String s : prods){
            if(!this.normal.containsKey(s) && !this.promo.containsKey(s))
                nuncaComprados.add(s);
        }

        return nuncaComprados;
    }

    public double getTotalFaturado(String prod, int[] quant, int mes){
        double[] total = new double[2];
        if(normal.containsKey(prod)){
            VendaMensal venda = normal.get(prod)[mes];
            if(venda != null)
                total[0] = quant[0] * venda.getPrecoUnitario();
        }
        if(promo.containsKey(prod)){
            VendaMensal venda = promo.get(prod)[mes];
            if(venda != null)
                total[1] = quant[1] * venda.getPrecoUnitario();
        }
        return total[0]+total[1];
    }

    public List<Double> totalfaturado(List<Map<String,int[]>> prodsQuant){
        List<Double> total = new ArrayList<>(12);
        double[] faturado = new double[2];
        for(int i=0; i<12; i++){
            total.add(i,0.0);
        }
        for(int i=0; i<12; i++){
            faturado[0] = 0.0;
            faturado[1] = 0.0;
            for(Map.Entry<String,int[]> entry: prodsQuant.get(i).entrySet()){
                if(normal.containsKey(entry.getKey())){
                    VendaMensal venda = normal.get(entry.getKey())[i];
                    if(venda != null)
                        faturado[0] += entry.getValue()[0] * venda.getPrecoUnitario();
                }
                if(promo.containsKey(entry.getKey())){
                    VendaMensal venda = promo.get(entry.getKey())[i];
                    if(venda != null)
                        faturado[1] += entry.getValue()[1] * venda.getPrecoUnitario();
                }
            }
            total.set(i,faturado[0]+faturado[1]);
        }
        return total;
    }

    public List<String> getClientesMaisGastadores(InterfFilial f){
        Map<String, Double> clientesGastadores = new TreeMap<>();
        List<Map<String, Map<Integer, Set<InfoFilial>>>> tiposDeCompras = f.getClientesInfo();
        for(Map<String, Map<Integer, Set<InfoFilial>>> clientes : tiposDeCompras){
            for(Map.Entry<String, Map<Integer, Set<InfoFilial>>> cliente : clientes.entrySet()){
                double total = 0;
                for(Map.Entry<Integer, Set<InfoFilial>> mes : cliente.getValue().entrySet()){
                    for(InfoFilial produto : mes.getValue()){
                        total += getTotalFaturado(produto.getCodigo(), produto.getQuantidadeComprada(),
                                mes.getKey(), tiposDeCompras.indexOf(clientes) == 1);
                    }
                }
                if(!clientesGastadores.containsKey(cliente.getKey()))
                    clientesGastadores.put(cliente.getKey(), total);
                else{
                    total+=clientesGastadores.get(cliente.getKey());
                    clientesGastadores.put(cliente.getKey(), total);
                }
            }
        }
        TreeMap<Double, String> ret = new TreeMap<>(Comparator.reverseOrder());
        for(Map.Entry<String, Double> entry : clientesGastadores.entrySet()){
            ret.put(entry.getValue(), entry.getKey());
        }
        clientesGastadores.clear();

        List<String> clientesBosses = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            Map.Entry<Double, String> clicli = ret.pollFirstEntry();
            clientesBosses.add(clicli.getValue());
        }
        return clientesBosses;
    }

    private double getTotalFaturado(String prod, int quant, int mes, boolean isPromo){
        if(isPromo){
            VendaMensal venda = promo.get(prod)[mes];
            return quant*venda.getPrecoUnitario();
        }else{
            VendaMensal venda = normal.get(prod)[mes];
            return quant*venda.getPrecoUnitario();
        }
    }

    public double[] getPrecoNormalProd(String codProd) {
        double[] preco = new double[12];
        if(this.normal.containsKey(codProd)){
            for(int i = 0; i<12; i++){
                if(this.normal.get(codProd)[i] == null){
                    preco[i]=0.0;
                }else {
                    preco[i] = this.normal.get(codProd)[i].getPrecoUnitario();
                }
            }
            return preco;
        }else{ return null; }
    }

    public double[] getPrecoPromoProd(String codProd) {
        double[] preco = new double[12];
        if(this.promo.containsKey(codProd)){
            for(int i = 0; i<12; i++){
                if(this.promo.get(codProd)[i] == null){
                    preco[i]=0.0;
                }else {
                    preco[i] = this.promo.get(codProd)[i].getPrecoUnitario();
                }
            }
            return preco;
        }else{ return null; }
    }

    public Double getTotalFaturado() {
        double total = 0;
        for(Map.Entry<String, VendaMensal[]> entry : normal.entrySet()){
            for(VendaMensal venda : entry.getValue()){
                if(venda != null)
                    total += venda.getTotalFaturado();
            }
        }
        for(Map.Entry<String, VendaMensal[]> entry : promo.entrySet()){
            for(VendaMensal venda : entry.getValue()){
                if(venda != null)
                    total += venda.getTotalFaturado();
            }
        }
        return total;
    }

    public Map<String, List<double[]>> getPrecoProds() {
        Map<String, List<double[]>> prodsPreco = new HashMap<>();
        List<double[]> precos = new ArrayList<>(2);
        double[] preco;

        for(Map.Entry<String,VendaMensal[]> prods: normal.entrySet()){
            preco = getPrecoNormalProd(prods.getKey());

            precos.add(0,preco);
            prodsPreco.put(prods.getKey(),precos);
        }
        for(Map.Entry<String,VendaMensal[]> prods: promo.entrySet()){
            preco = getPrecoPromoProd(prods.getKey());

            precos.add(1,preco);
            prodsPreco.put(prods.getKey(),precos);
        }
        return prodsPreco;
    }

    public boolean isEmpty(){
        return this.normal.isEmpty() && this.promo.isEmpty();
    }
}
