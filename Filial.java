import java.util.*;

public class Filial implements InterfFilial{
    //key produto, value map com key mes e value informacoes necessarias
    private Map<String, Map<Integer,Set<InfoFilial>>> normal;
    private Map<String, Map<Integer,Set<InfoFilial>>> promo;

    public Filial(){
        this.normal = new HashMap<>();
        this.promo = new HashMap<>();
    }

    public void adiciona(InterfVenda venda) {
        if(venda.getCodCli().equals("Z5000"))
            System.out.println(venda);
        if(venda.isPromo()){
            if(this.promo.containsKey(venda.getCodPro())){ //mapa contem produto
                Map<Integer,Set<InfoFilial>> meses = this.promo.get(venda.getCodPro());
                Set<InfoFilial> clientes = meses.get(venda.getMes()-1);
                if(clientes!=null){//mes já iniciado
                    Iterator<InfoFilial> it = clientes.iterator();
                    boolean flag = true;
                    while(it.hasNext() && flag) {
                        InfoFilial a = it.next();
                        if (a.getCliente().equals(venda.getCodCli())) {
                            flag = false;
                            a.incrementaNumVendas();
                            a.adicionaQuantidade(venda.getQuant());
                            clientes.add(a);
                            meses.put(venda.getMes()-1,clientes);
                            this.promo.put(venda.getCodPro(), meses);
                        }
                    }
                    if(flag){
                        InfoFilial infoNovo = new InfoFilial(venda.getCodCli(),venda.getQuant());
                        clientes.add(infoNovo);
                        meses.put(venda.getMes()-1,clientes);
                        this.promo.put(venda.getCodPro(), meses);
                    }
                }else{
                    clientes = new HashSet<>();
                    InfoFilial infoNovo = new InfoFilial(venda.getCodCli(),venda.getQuant());
                    clientes.add(infoNovo);
                    meses.put(venda.getMes()-1,clientes);
                    this.promo.put(venda.getCodPro(), meses);
                }
            }else{//O map não tem o produto
                Set<InfoFilial> info = new HashSet<>();
                info.add(new InfoFilial(venda.getCodCli(), venda.getQuant()));
                Map<Integer,Set<InfoFilial>> meses = new HashMap<>(12);
                meses.put(venda.getMes()-1, info);
                promo.put(venda.getCodPro(), meses);
            }
        }else{
            if(this.normal.containsKey(venda.getCodPro())){ //mapa contem produto
                Map<Integer,Set<InfoFilial>> meses = this.normal.get(venda.getCodPro());
                //verificar a existencia do cliente no Set
                Set<InfoFilial> clientes = meses.get(venda.getMes()-1);
                if(clientes!=null){
                    Iterator<InfoFilial> it = clientes.iterator();
                    boolean flag = true;
                    while(it.hasNext() && flag){
                        InfoFilial a = it.next();
                        if(a.getCliente().equals(venda.getCodCli())){
                            flag = false;
                            a.incrementaNumVendas();
                            a.adicionaQuantidade(venda.getQuant());
                            clientes.add(a);
                            meses.put(venda.getMes()-1,clientes);
                            this.normal.put(venda.getCodPro(), meses);
                        }
                    }
                    if(flag){
                        InfoFilial infoNovo = new InfoFilial(venda.getCodCli(),venda.getQuant());
                        clientes.add(infoNovo);
                        meses.put(venda.getMes()-1,clientes);
                        this.normal.put(venda.getCodPro(), meses);
                    }
                }else{
                    clientes = new HashSet<>();
                    InfoFilial infoNovo = new InfoFilial(venda.getCodCli(),venda.getQuant());
                    clientes.add(infoNovo);
                    meses.put(venda.getMes()-1,clientes);
                    this.normal.put(venda.getCodPro(), meses);
                }
            }else{//O map não tem o produto
                Set<InfoFilial> info = new HashSet<>();
                info.add(new InfoFilial(venda.getCodCli(), venda.getQuant()));
                Map<Integer,Set<InfoFilial>> meses = new HashMap<>(12);
                meses.put(venda.getMes()-1, info);
                normal.put(venda.getCodPro(), meses);
            }
        }
    }

    public Map<Integer, Set<String>> totalVendasEClientesMes(int mes) {
        int total = 0;
        Set<String> clientes = new TreeSet<>();
        for(Map.Entry<String, Map<Integer,Set<InfoFilial>>> entry : normal.entrySet()){
            Set<InfoFilial> info = entry.getValue().get(mes-1);
            if(info != null) {
                for (InfoFilial i : info) {
                    clientes.add(i.getCliente());
                    total += i.getNumVendas();
                }
            }
        }
        for(Map.Entry<String, Map<Integer,Set<InfoFilial>>> entry : promo.entrySet()){
            Set<InfoFilial> info = entry.getValue().get(mes-1);
            if(info != null) {
                for (InfoFilial i : info) {
                    clientes.add(i.getCliente());
                    total += i.getNumVendas();
                }
            }
        }
        Map<Integer, Set<String>> ret = new HashMap<>();
        ret.put(total, clientes);
        return ret;
    }

    public int getQuantidadeTotalProduto(String prod, int mes) {
        int quantidade = 0;
        if(normal.containsKey(prod)){
            Set<InfoFilial> info = normal.get(prod).get(mes);
            if(info != null) {
                for (InfoFilial in : info) {
                    quantidade += in.getQuantidadeComprada();
                }
            }
        }
        if(promo.containsKey(prod)){
            Set<InfoFilial> info = promo.get(prod).get(mes);
            if(info != null) {
                for (InfoFilial in : info) {
                    quantidade += in.getQuantidadeComprada();
                }
            }
        }
        return quantidade;
    }

    public int[] getQuantidadePorTipoProduto(String prod, int mes) {
        int[] total = new int[2];
        if(normal.containsKey(prod)){
            Set<InfoFilial> info = normal.get(prod).get(mes);
            if(info != null) {
                for (InfoFilial i : info) {
                    total[0] += i.getQuantidadeComprada();
                   // System.out.println("normal" + mes + "," + i.getQuantidadeComprada());
                }
            }
        }
        if(promo.containsKey(prod)){
            Set<InfoFilial> info = promo.get(prod).get(mes);
            if(info != null) {
                for (InfoFilial in : info) {
                    total[1] += in.getQuantidadeComprada();
                   // System.out.println("promo " + mes + "," + in.getQuantidadeComprada());
                }
            }
        }
        return total;
    }

    public Set<String> getClientes(String prod, int mes){
        Set<String> clientes = new TreeSet<>();
        if(normal.containsKey(prod)){
            Set<InfoFilial> infos = normal.get(prod).get(mes);
            if(infos != null) {
                for (InfoFilial info : infos) {
                    clientes.add(info.getCliente());
                }
            }
        }
        if(promo.containsKey(prod)){
            Set<InfoFilial> infos = promo.get(prod).get(mes);
            if(infos != null) {
                for (InfoFilial info : infos) {
                    clientes.add(info.getCliente());
                }
            }
        }
        return clientes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Map<Integer,Set<InfoFilial>>> entry : promo.entrySet()){
            sb.append("----------Produto: ").append(entry.getKey()).append("------------\n");
            Map<Integer,Set<InfoFilial>> meses = entry.getValue();
            for(Map.Entry<Integer,Set<InfoFilial>> infos : meses.entrySet()){
                for (InfoFilial inf : infos.getValue()) {
                    sb.append(inf.getCliente() + "," + inf.getQuantidadeComprada() + "\n");
                }
            }
        }
        return sb.toString();
    }

    public int totalCompras(String codCliente, int mes){
        int total = 0;
        for(Map.Entry<String, Map<Integer,Set<InfoFilial>>> entry : normal.entrySet()) {
            if(entry.getValue().containsKey(mes)){
                Set<InfoFilial> info = entry.getValue().get(mes);
                for (InfoFilial infoFil : info) {
                    if (infoFil.getCliente().equals(codCliente)) {
                        total += infoFil.getNumVendas();
                    }
                }
            }
        }
        for(Map.Entry<String, Map<Integer,Set<InfoFilial>>> entry : promo.entrySet()) {
            if(entry.getValue().containsKey(mes)){
                Set<InfoFilial> info = entry.getValue().get(mes);
                for (InfoFilial infoFil : info) {
                    if (infoFil.getCliente().equals(codCliente)) {
                        total += infoFil.getNumVendas();
                    }
                }
            }
        }
        return total;
    }

    public Set<String> getProdutos(String codCliente, int mes){
        Set<String> total = new TreeSet<>();
        for(Map.Entry<String, Map<Integer,Set<InfoFilial>>> entry : normal.entrySet()) {
            if(entry.getValue().containsKey(mes)){
                Set<InfoFilial> info = entry.getValue().get(mes);
                for (InfoFilial infoFil : info) {
                    if (infoFil.getCliente().equals(codCliente)) {
                        total.add(entry.getKey());
                    }
                }
            }
        }
        for(Map.Entry<String, Map<Integer,Set<InfoFilial>>> entry : promo.entrySet()) {
            if(entry.getValue().containsKey(mes)){
                Set<InfoFilial> info = entry.getValue().get(mes);
                for (InfoFilial infoFil : info) {
                    if (infoFil.getCliente().equals(codCliente)) {
                        total.add(entry.getKey());
                    }
                }
            }
        }
        return total;
    }


    public Map<String,int[]> prodsQuantNormal(String codCliente) {
        Map<String,int[]> total = new HashMap<>();
        int[] quant = new int[12];
        for(Map.Entry<String, Map<Integer,Set<InfoFilial>>> entry : this.normal.entrySet()) {
            for(Map.Entry<Integer,Set<InfoFilial>> entry2: entry.getValue().entrySet()) {
                Set<InfoFilial> info = entry2.getValue();
                for (InfoFilial infoFil : info) {
                    if (infoFil.getCliente().equals(codCliente)) {
                        quant[entry2.getKey()] += infoFil.getQuantidadeComprada();
                        total.put(entry.getKey(),quant);
                    }
                }
            }
        }
        return total;
    }
    public Map<String,int[]> prodsQuantPromo(String codCliente) {
        Map<String,int[]> total = new HashMap<>();
        int[] quant = new int[12];
        for (Map.Entry<String, Map<Integer,Set<InfoFilial>>> entry  : this.promo.entrySet()) {
            for(Map.Entry<Integer,Set<InfoFilial>> entry2: entry.getValue().entrySet()) {
                Set<InfoFilial> info = entry2.getValue();
                for (InfoFilial infoFil : info) {
                    if (infoFil.getCliente().equals(codCliente)) {
                        quant[entry2.getKey()] += infoFil.getQuantidadeComprada();
                        total.put(entry.getKey(),quant);
                    }
                }
            }
        }
        return total;
    }

    public boolean isEmpty(){
        return this.normal.isEmpty() && this.promo.isEmpty();
    }
}
