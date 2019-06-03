import java.util.*;

public class Filial implements InterfFilial{
    //key produto, value map com key mes e value informacoes necessarias
    private Map<String, Map<Integer,List<InfoFilial>>> normal;
    private Map<String, Map<Integer,List<InfoFilial>>> promo;
    private boolean flag = true;

    public Filial(){
        this.normal = new HashMap<>();
        this.promo = new HashMap<>();
    }

    public void adiciona(InterfVenda venda) {
        if(venda.isPromo()){
            if(this.promo.containsKey(venda.getCodPro())){ //mapa contem produto
                Map<Integer, List<InfoFilial>> meses = this.promo.get(venda.getCodPro());
                List<InfoFilial> clientes = meses.get(venda.getMes()-1);
                Iterator<InfoFilial> it = clientes.iterator();
                while(it.hasNext() && flag){
                    InfoFilial a = it.next();
                    if(a.getCliente().equals(venda.getCodCli())){
                        flag = false;
                        a.incrementaNumVendas();
                        a.setQuantidadeComprada(a.getQuantidadeComprada()+venda.getQuant());
                    }
                }
                if(flag){
                    InfoFilial infoNovo = new InfoFilial(venda.getCodCli(),venda.getQuant());
                    clientes.add(infoNovo);
                }
            }else{
                Map<Integer, List<InfoFilial>> meses = this.promo.get(venda.getCodPro());
                List<InfoFilial> clientes = new ArrayList<>();
                InfoFilial infoNovo = new InfoFilial(venda.getCodCli(),venda.getQuant());
                clientes.add(infoNovo);
                meses.put(venda.getMes()-1,clientes);
                this.promo.put(venda.getCodPro(),meses);
            }
        }
        else {
            if(this.normal.containsKey(venda.getCodPro())) { //mapa contem produto
                Map<Integer, List<InfoFilial>> meses = this.normal.get(venda.getCodPro());
                List<InfoFilial> clientes = meses.get(venda.getMes()-1);
                Iterator<InfoFilial> it = clientes.iterator();
                while(it.hasNext() && flag){
                    InfoFilial a = it.next();
                    if(a.getCliente().equals(venda.getCodCli())){
                        flag = false;
                        a.incrementaNumVendas();
                        a.setQuantidadeComprada(a.getQuantidadeComprada()+venda.getQuant());
                    }
                }
                if(flag){
                    InfoFilial infoNovo = new InfoFilial(venda.getCodCli(),venda.getQuant());
                    clientes.add(infoNovo);
                }
            }else{
                Map<Integer, List<InfoFilial>> meses = this.normal.get(venda.getCodPro());
                List<InfoFilial> clientes = new ArrayList<>();
                InfoFilial infoNovo = new InfoFilial(venda.getCodCli(),venda.getQuant());
                clientes.add(infoNovo);
                meses.put(venda.getMes()-1,clientes);
                this.normal.put(venda.getCodPro(),meses);
            }
        }
    }


    public int[] totalVendasEClientesMes(int mes){
        int[] totalVendasEClientes = new int[2];
        Set<String> clientes = new HashSet<>();

        for(Map<Integer,List<InfoFilial>> entry: this.normal.values()) {
            for (List<InfoFilial> l : entry.values()) {
                for (InfoFilial info : l) {
                    if (info.getQuantidadeComprada() > 0) {
                        totalVendasEClientes[0] += info.getNumVendas();
                        clientes.add(info.getCliente());
                    }
                }
                totalVendasEClientes[1] = clientes.size();
            }
        }
        for(Map<Integer,List<InfoFilial>> entry: this.promo.values()) {
            for (List<InfoFilial> l : entry.values()) {
                for (InfoFilial info : l) {
                    if (info.getQuantidadeComprada() > 0) {
                        totalVendasEClientes[0] += info.getNumVendas();
                        clientes.add(info.getCliente());
                    }
                }
                totalVendasEClientes[1] = clientes.size();
            }
        }

        return totalVendasEClientes;
    }
/*
    public int[] vezesProdComprado(String codProd){
        Map<Integer,List<InfoFilial>> = this.normal.get(codProd);
        List<InfoFilial> info = ;
        int[] vezesComprado = new int[12];

        //se numero de vezes comprado for a quantidade
        for(InfoFilial iF: info){
            for(int i = 0; i < 12; i++){
                vezesComprado[i] += iF.getQuantidadeComprada(i);
            }
        }
/*
        //se numero de vezes comprado for numero de vendas
        for(InfoFilial iF: info){
            for(int i = 0; i < 12; i++){
                vezesComprado[i] += iF.getNumVendas(i);
            }
        }

        return vezesComprado;
    }

    public int[] clientesProd(String codProd){
        List<InfoFilial> clientes = this.produtos.get(codProd);
        int[] total = new int[12];

        for(InfoFilial info: clientes){
            for(int i=0; i<12; i++){
                if(info.getNumVendas(i)>0){
                    total[i]++;
                    i = 12;
                }
            }
        }
        return total;
    }
    */
}
