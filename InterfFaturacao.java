import java.util.Set;

public interface InterfFaturacao {
    void adiciona(InterfVenda venda);

    Set<String> getListaOrdenadaProdutosNuncaComprados(InterfCatProds catpro);
    double getTotalFaturadoProd(String codProd);
}