import java.util.*;

public class Filial implements InterfFilial{
    //key produto, value map com key mes e value informacoes necessarias
    private Map<String, List<Set<InfoFilial>>> normal;
    private Map<String, List<Set<InfoFilial>>> promo;

    public Filial(){
        this.normal = new HashMap<>();
        this.promo = new HashMap<>();
    }

    public void adiciona(InterfVenda venda) {
        if(venda.isPromo()){
            if(this.promo.containsKey(venda.getCodPro())){ //mapa contem produto
                List<Set<InfoFilial>> meses = this.promo.get(venda.getCodPro());
                //verificar a existencia do cliente no Set
                Set<InfoFilial> clientes = meses.get(venda.getMes()-1);
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
                if(flag){
                    InfoFilial infoNovo = new InfoFilial(venda.getCodCli(),venda.getQuant());
                    clientes.add(infoNovo);
                }
            }else{//O map não tem o produto
                Set<InfoFilial> info = new HashSet<>();
                info.add(new InfoFilial(venda.getCodCli(), venda.getQuant()));
                List<Set<InfoFilial>> meses= new ArrayList<>(12);
                for(int i = 0; i <12; i++)
                    meses.add(i,new HashSet<>());
                meses.add(venda.getMes()-1, info);
                promo.put(venda.getCodPro(), meses);
            }
        }else{
            if(this.normal.containsKey(venda.getCodPro())){ //mapa contem produto
                List<Set<InfoFilial>> meses = this.normal.get(venda.getCodPro());
                //verificar a existencia do cliente no Set
                Set<InfoFilial> clientes = meses.get(venda.getMes()-1);
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
                if(flag){
                    InfoFilial infoNovo = new InfoFilial(venda.getCodCli(),venda.getQuant());
                    clientes.add(infoNovo);
                }
            }else{//O map não tem o produto
                Set<InfoFilial> info = new HashSet<>();
                info.add(new InfoFilial(venda.getCodCli(), venda.getQuant()));
                List<Set<InfoFilial>> meses= new ArrayList<>(12);
                for(int i = 0; i <12; i++)
                    meses.add(i,new HashSet<>());
                meses.add(venda.getMes()-1, info);
                normal.put(venda.getCodPro(), meses);
            }
        }
    }
}
