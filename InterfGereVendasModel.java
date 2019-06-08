import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InterfGereVendasModel {
    void createData();

    Set<String> querie1();

    int[] querie2(int mes);
    int[] querie2(int mes, int fil);

    List<Integer> Querie3TotalComprasCliente(String codCliente);
    List<Integer> Querie3TotalProds(String codCliente);
    List<Double> Querie3TotalGasto(String codCliente);

    List<Integer> querie4getQuantidade(String prod);
    List<Integer> querie4getClientes(String prod);
    List<Double> querie4getTotalFaturado(String prod);

    Map<Integer, Set<String>> querie5(String cli);

    List<String> querie6ProdsMaisComprados(int x);
    List<Integer> querie6Clientes(List<String> prod);

    List<List<String>> querie7();

    Map<Integer, Set<String>> querie8(int x);

    Map<Integer, Map<String, Double>> querie9(String codProd);


    boolean existeCodCliente(String codCli);
    boolean existeCodProd(String codProd);

    int getFILIAIS();

    boolean isEmpty();

    String getVENDAS();

    int getVENDASTOTAL();

    int getVENDASVALIDAS();

    int getCatProdsSize();

    int getCatCliSize();

    int getClientesQueCompraram();

    int getVENDASGRATIS();

    Double getFaturacaoTotal();

    Map<Integer, List<Map<String, Double>>> querie10();
}