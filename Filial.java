import java.util.*;

public class Filial implements InterfFilial{
    //key produto, value map com key mes e value informacoes necessarias
    private Map<String, Set<InfoFilial>[]> normal;
    private Map<String, Set<InfoFilial>[]> promo;

    public Filial(){
        this.normal = new HashMap<>();
        this.promo = new HashMap<>();
    }

    public void adiciona(InterfVenda venda) {
        if(venda.isPromo()){
            if(this.promo.containsKey(venda.getCodPro())){ //mapa contem produto
                Set<InfoFilial>[] meses = this.promo.get(venda.getCodPro());
                Set<InfoFilial> clientes = meses[venda.getMes()-1];
                if(clientes!=null){
                    Iterator<InfoFilial> it = clientes.iterator();
                    boolean flag = true;
                    while(it.hasNext() && flag){
                        InfoFilial a = it.next();
                        if(a.getCliente().equals(venda.getCodCli())){
                            flag = false;
                            a.incrementaNumVendas();
                            a.adicionaQuantidade(venda.getQuant());
                        }
                    }
                }else{
                    clientes = new HashSet<>();
                    InfoFilial infoNovo = new InfoFilial(venda.getCodCli(),venda.getQuant());
                    clientes.add(infoNovo);
                }

            }else{//O map não tem o produto
                Set<InfoFilial> info = new HashSet<>();
                info.add(new InfoFilial(venda.getCodCli(), venda.getQuant()));
                Set<InfoFilial>[] meses = new Set[12];
                meses[venda.getMes()-1] = info;
                promo.put(venda.getCodPro(), meses);
            }
        }else{
            if(this.normal.containsKey(venda.getCodPro())){ //mapa contem produto
                Set<InfoFilial>[] meses = this.normal.get(venda.getCodPro());
                //verificar a existencia do cliente no Set
                Set<InfoFilial> clientes = meses[venda.getMes()-1];
                if(clientes!=null){
                    Iterator<InfoFilial> it = clientes.iterator();
                    boolean flag = true;
                    while(it.hasNext() && flag){
                        InfoFilial a = it.next();
                        if(a.getCliente().equals(venda.getCodCli())){
                            flag = false;
                            a.incrementaNumVendas();
                            a.adicionaQuantidade(venda.getQuant());
                        }
                    }
                }else{
                    clientes = new HashSet<>();
                    InfoFilial infoNovo = new InfoFilial(venda.getCodCli(),venda.getQuant());
                    clientes.add(infoNovo);
                }
            }else{//O map não tem o produto
                Set<InfoFilial> info = new HashSet<>();
                info.add(new InfoFilial(venda.getCodCli(), venda.getQuant()));
                Set<InfoFilial>[] meses = new Set[12];
                meses[venda.getMes()-1] = info;
                normal.put(venda.getCodPro(), meses);
            }
        }
    }

    public Map<Integer, Set<String>> totalVendasEClientesMes(int mes) {
        int total = 0;
        Set<String> clientes = new TreeSet<>();
        for(Map.Entry<String, Set<InfoFilial>[]> entry : normal.entrySet()){
            Set<InfoFilial> info = entry.getValue()[mes-1];
            if(info != null) {
                for (InfoFilial i : info) {
                    clientes.add(i.getCliente());
                    total += i.getNumVendas();
                }
            }
        }
        for(Map.Entry<String, Set<InfoFilial>[]> entry : promo.entrySet()){
            Set<InfoFilial> info = entry.getValue()[mes-1];
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
}
