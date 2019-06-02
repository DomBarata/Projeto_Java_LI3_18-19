import java.util.Map;
import java.util.Set;

public interface InterfGereVendasModel {

    void createData();

    Set<String> querie1();
    Map<Integer, int[]> querie2(int mes);
    int[] querie3VezesComprado(String codProd);
    int[] querie3Clientes(String codProd);

    boolean existeCodCliente(String codCli);
    boolean existeCodProd(String codProd);
}