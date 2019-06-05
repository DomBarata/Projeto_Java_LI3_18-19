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

    Map<String,int[]> prodsQuantNormal(String codCliente);

    Map<String,int[]> prodsQuantPromo(String codCliente);


    boolean isEmpty();
}