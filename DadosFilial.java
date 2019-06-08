import java.io.Serializable;
import java.util.*;

public class DadosFilial implements Serializable {
    private Map<String, Map<Integer, Set<InfoFilial>>> normal;
    private Map<String, Map<Integer,Set<InfoFilial>>> promo;

    DadosFilial(){
        this.normal = new HashMap<>();
        this.promo = new HashMap<>();
    }

    public void adiciona(String cod1, String cod2, int mes, int qtd, boolean isPromo) {
        if(isPromo){
            if(this.promo.containsKey(cod1)){ //mapa contem produto
                Map<Integer,Set<InfoFilial>> meses = this.promo.get(cod1);
                Set<InfoFilial> clientes = meses.get(mes-1);
                if(clientes!=null){//mes já iniciado
                    Iterator<InfoFilial> it = clientes.iterator();
                    boolean flag = true;
                    while(it.hasNext() && flag) {
                        InfoFilial a = it.next();
                        if (a.getCodigo().equals(cod2)) {
                            flag = false;
                            a.incrementaNumVendas();
                            a.adicionaQuantidade(qtd);
                            clientes.add(a);
                            meses.put(mes-1,clientes);
                            this.promo.put(cod1, meses);
                        }
                    }
                    if(flag){
                        InfoFilial infoNovo = new InfoFilial(cod2,qtd);
                        clientes.add(infoNovo);
                        meses.put(mes-1,clientes);
                        this.promo.put(cod1, meses);
                    }
                }else{
                    clientes = new HashSet<>();
                    InfoFilial infoNovo = new InfoFilial(cod2,qtd);
                    clientes.add(infoNovo);
                    meses.put(mes-1,clientes);
                    this.promo.put(cod1, meses);
                }
            }else{//O map não tem o produto
                Set<InfoFilial> info = new HashSet<>();
                info.add(new InfoFilial(cod2, qtd));
                Map<Integer,Set<InfoFilial>> meses = new HashMap<>(12);
                meses.put(mes-1, info);
                promo.put(cod1, meses);
            }
        }else{
            if(this.normal.containsKey(cod1)){ //mapa contem produto
                Map<Integer,Set<InfoFilial>> meses = this.normal.get(cod1);
                //verificar a existencia do cliente no Set
                Set<InfoFilial> clientes = meses.get(mes-1);
                if(clientes!=null){
                    Iterator<InfoFilial> it = clientes.iterator();
                    boolean flag = true;
                    while(it.hasNext() && flag){
                        InfoFilial a = it.next();
                        if(a.getCodigo().equals(cod2)){
                            flag = false;
                            a.incrementaNumVendas();
                            a.adicionaQuantidade(qtd);
                            clientes.add(a);
                            meses.put(mes-1,clientes);
                            this.normal.put(cod1, meses);
                        }
                    }
                    if(flag){
                        InfoFilial infoNovo = new InfoFilial(cod2,qtd);
                        clientes.add(infoNovo);
                        meses.put(mes-1,clientes);
                        this.normal.put(cod1, meses);
                    }
                }else{
                    clientes = new HashSet<>();
                    InfoFilial infoNovo = new InfoFilial(cod2,qtd);
                    clientes.add(infoNovo);
                    meses.put(mes-1,clientes);
                    this.normal.put(cod1, meses);
                }
            }else{//O map não tem o produto
                Set<InfoFilial> info = new HashSet<>();
                info.add(new InfoFilial(cod2, qtd));
                Map<Integer,Set<InfoFilial>> meses = new HashMap<>(12);
                meses.put(mes-1, info);
                normal.put(cod1, meses);
            }
        }
    }

    public List<Map<String, Map<Integer, Set<InfoFilial>>>> getDados() {
        List<Map<String, Map<Integer, Set<InfoFilial>>>> ret = new ArrayList<>(2);
        ret.add(normal);
        ret.add(promo);

        return ret;
    }

    public boolean isEmpty() {
        return this.normal.isEmpty() && this.promo.isEmpty();
    }
}
