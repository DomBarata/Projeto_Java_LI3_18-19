import java.util.List;
import java.util.Set;

public interface InterfGereVendasModel {

    void createData();

    Set<String> querie1();
    int[] querie2(int mes);
    int[] querie2(int mes, int fil);

    boolean existeCodCliente(String codCli);
    boolean existeCodProd(String codProd);

    int getFILIAIS();

    List<Integer> getTotalComprasCliente(String codCliente);

    List<Integer> getTotalProds(String codCliente);

    List<Double> getTotalGasto(String codCliente);
}