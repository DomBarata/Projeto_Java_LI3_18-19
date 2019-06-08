import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InterfGereVendasView {

    int menu();
    int submenuQuerie2();

    int getMes();

    void printPaginacao(Set<String> nuncaComprados);

    void printOpInvalida();

    void printQuerie2(int[] total);

    void printQuerie2(int[] total, int fil);

    String getCodCliente();

    void printInvalido(String cod);

    void printQuerie3(List<Integer> compras, List<Integer> produtos, List<Double> gasto);

    String getCodProduto();

    void printMes(List<Integer> querie4getClientes, List<Integer> querie4getQuantidade, List<Double> querie4getTotalFaturado);

    void printPaginacao(Map<Integer, Set<String>> produtos);

    int querie6getX();

    void printQuerie6(Map<String, Integer> prodsEClientes);

    void printQuerie8(int x,Map<Integer, Set<String>> clientes);

    void printI(int i);

    void printQuerie9(Map<Integer, Map<String, Double>> clis, int x9);

    void printQuerie10(Map<Integer, List<Map<String, Double>>> meses, int filiais);

    void printNumInvalido();
}