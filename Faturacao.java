import java.util.*;

public class Faturacao implements InterfFaturacao{
    //Mapa com key codProd, e uma lista com 2 posicoes:
    //0 Venda Normal, 1 Venda em Promocao, e o seu total faturado
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
                }else{
                    v = new VendaMensal(venda.getQuant(), venda.getPreco());
                }
            }else{//caso contrario
                //Inicializar lista de meses
                VendaMensal[] meses = new VendaMensal[12];
                //adicionar uma nova venda mensal à lista
                VendaMensal v = meses[venda.getMes()-1];
                v = new VendaMensal(venda.getQuant(), venda.getPreco());
                //adicionar a lista ao map
                promo.put(venda.getCodPro(), meses);
            }
        }else{//normal
            if(normal.containsKey(venda.getCodPro())){//esta inserida
                VendaMensal[] lista = normal.get(venda.getCodPro());
                VendaMensal v = lista[venda.getMes()-1];
                if(v != null){
                    v.insertVenda(venda.getQuant(), venda.getPreco());
                }else{
                    v = new VendaMensal(venda.getQuant(), venda.getPreco());
                }
            }else{//caso contrario
                //Inicializar lista de meses
                VendaMensal[] meses = new VendaMensal[12];
                //adicionar uma nova venda mensal à lista
                VendaMensal v = meses[venda.getMes()-1];
                v = new VendaMensal(venda.getQuant(), venda.getPreco());
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
        System.out.println("nunca comprados " + nuncaComprados.size()); //a correr teste
        return nuncaComprados;
    }

    public double getTotalFaturadoProd(String codProd){
//        return this.faturacao.get(codProd).get(0)+this.faturacao.get(codProd).get(1);
        return 0;
    }

}
