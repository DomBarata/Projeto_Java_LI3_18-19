import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InterfGereVendasView {

    int menu();
    int submenuQuerie2();

    int getMes();

    void printPaginacao(Set<String> nuncaComprados);

    void printOpInvalida();

    void printNumMesInvalido();

    void printQuerie2(int[] total);

    void printQuerie2(int[] total, int fil);

    String getCodCliente();

    void printInvalido(String cod);

    void printPaginacaoMes(String str, List<Integer> compras, List<Integer> produtos, List<Double> gasto);

    String getCodProduto();

    void printPaginacao(Map<Integer, Set<String>> produtos);


    void printPaginacao(List<List<String>> lista);

    void waiting();

    void printPaginacao(List<String> prods, List<Integer> prodsEClientes, int x);

    void tempoDeExecucao(double tempoDecorrido);

    int getX();

    void printPaginacao(int x, Map<Integer, Set<String>> clientes);

    void printPaginacao(Map<Integer, Map<String, Double>> clis, int x);

    void printQuerie10(Map<Integer, List<Map<String, Double>>> meses, int filiais);
}