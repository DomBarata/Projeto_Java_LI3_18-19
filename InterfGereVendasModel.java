import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InterfGereVendasModel {

    InterfFaturacao getFact();
    List<InterfFilial> getFil();
    void createData();
    int getI();

    Set<String> querie1();

    int[] querie2(int mes);
    int[] querie2(int mes, int fil);

    List<Integer> Querie3TotalComprasCliente(String codCliente);
    List<Integer> Querie3TotalProds(String codCliente);
    List<Double> Querie3TotalGasto(String codCliente);

    List<Integer> querie4getQuantidade(String prod);
    List<Integer> querie4getClientes(String prod);
    List<Double> querie4getTotalFaturado(String prod);

    boolean existeCodCliente(String codCli);
    boolean existeCodProd(String codProd);

    int getFILIAIS();

    boolean isEmpty();

    Map<Integer, Set<String>> querie5(String cli);

    Set<String> querie6PodsMaisComprados(int x);

    Map<String, Integer> querie6Clientes(Set<String> prods);

    Map<Integer, Set<String>> querie8();

    Map<Integer, Map<String, Double>> querie9(String codProd);
}