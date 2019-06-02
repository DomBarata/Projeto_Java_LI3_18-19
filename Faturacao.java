import java.util.*;

public class Faturacao implements InterfFaturacao{
    //Mapa com key codProd, e uma lista com 2 posicoes:
    //0 Venda Normal, 1 Venda em Promocao, e o seu total faturado
    private Map<String, List<Double>> faturacao;

    public Faturacao(){
        this.faturacao = new HashMap<>();
    }

    public void adiciona(InterfVenda venda){
        if(this.faturacao.containsKey(venda.getCodPro())){ //Se o mapa já tem o codigo
            List<Double> promo = this.faturacao.get(venda.getCodPro()); //vou buscar a lista
            double total;
            if(venda.getTipo().equals("N")){//vejo se a venda e tipo N
                total = promo.get(0);       //se for vou buscar o total
                promo.remove(0);      //removo o elemento da lista
                total += venda.getPreco() * venda.getQuant(); //somo ao total que ja tinha o total desta venda
                promo.add(0, total);   //volto a inserir o elemento alterado na mesma posiçao
                this.faturacao.put(venda.getCodPro(), promo); //adiciono a lista no map
            }else{                          //senao e tipo P, e repito o processo em cima
                total = promo.get(1);
                promo.remove(1);
                total += venda.getPreco() * venda.getQuant();
                promo.add(1, total);
                this.faturacao.put(venda.getCodPro(), promo);
            }
        }else{ //Se o mapa não tem o codigo
            List<Double> promo = new ArrayList<>(2); //inicializo uma lista com capacidade 2
            if(venda.getTipo().equals("N")){//vejo se a venda e tipo N
                promo.add(0,venda.getPreco()*venda.getQuant()); //na posicao 0, e o total
                promo.add(1, 0.0);//na posicao 1, e 0
                this.faturacao.put(venda.getCodPro(), promo);//adiciono a lista no map
            }else{                          //senao e tipo P, e repito o processo em cima invertido
                promo.add(0, 0.0);
                promo.add(1,venda.getPreco()*venda.getQuant());
                this.faturacao.put(venda.getCodPro(), promo);
            }
        }
    }

    public Set<String> getListaOrdenadaProdutosNuncaComprados(InterfCatProds catpro){
        Set<String> prods = catpro.getProdutos();
        Set<String> nuncaComprados = new TreeSet<>(); //comparator para poder ordenar alfabeticamente
        for(String s : prods){
            if(!this.faturacao.containsKey(s))
                nuncaComprados.add(s);
        }
        System.out.println("nunca comprados " + nuncaComprados.size()); //a correr teste
        return nuncaComprados;
    }

    public double getTotalFaturadoProd(String codProd){
        return this.faturacao.get(codProd).get(0)+this.faturacao.get(codProd).get(1);
    }
}