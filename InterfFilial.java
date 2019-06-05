import java.util.Map;
import java.util.Set;

public interface InterfFilial {
    void adiciona(InterfVenda venda);

    Map<Integer, Set<String>> totalVendasEClientesMes(int mes);

    int getQuantidadeTotalProduto(String prod, int mes);

    int[] getQuantidadePorTipoProduto(String prod, int mes);

    Set<String> getClientes(String prod, int mes);
    Set<String> getProdutos(String codCliente, int mes);

    int totalCompras(String codCliente, int mes);

    public Map<String,int[]> prodsQuant(String codCliente, int mes);


    boolean isEmpty();

    Map<Integer,Set<String>> getProdutosEQuantidades(Map<Integer, Set<String>> prods, String cli);
}