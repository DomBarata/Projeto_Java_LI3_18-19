import java.util.Map;
import java.util.Set;

public interface InterfFilial {
    void adiciona(InterfVenda venda);

    Map<Integer, Set<String>> totalVendasEClientesMes(int mes);

    int[] totalCompras(String codCliente);

    int[] totalProds(String codCliente);

    Map<String,int[]> prodsQuantNormal(String codCliente);

    Map<String,int[]> prodsQuantPromo(String codCliente);
}