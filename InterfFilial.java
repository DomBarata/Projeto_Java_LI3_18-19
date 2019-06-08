import java.util.*;

public interface InterfFilial {
    void adiciona(InterfVenda venda);

    AbstractMap.SimpleEntry<Integer, Set<String>> totalVendasEClientesMes(int mes);

    int getQuantidadeTotalProduto(String prod, int mes);

    int[] getQuantidadePorTipoProduto(String prod, int mes);

    Set<String> getClientes(String prod, int mes);
    Set<String> getClientes(String prod);
    Set<String> getProdutos(String codCliente, int mes);

    int totalCompras(String codCliente, int mes);

    Map<String,int[]> prodsQuant(String codCliente, int mes);
    Map<String,int[]> prodsQuant(String codCliente);

    boolean isEmpty();

    Map<String, Integer> getProdMaisComprado(Map<String, Integer> prodsCliQtd);

    Set<String> getTodosOsClientesQueCompraram();
    Set<String> getTodosOsProdutosQueCompraram();

    List<Map<String, Map<Integer, Set<InfoFilial>>>> getClientesInfo();

    Map<String, Set<String>> clientesMaisProds(Map<String, Set<String>> cliProds);

    Map<Integer, Map<String, Double>> clisProdQ9(String codProd, Map<Integer, Map<String, Double>> clis, double[] precoN, double[] precoP);

    Set<String> getClientes();

    int totalCompras(int mes);
}