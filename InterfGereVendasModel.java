import java.util.Set;

public interface InterfGereVendasModel {

    void createData();

    Set<String> querie1();
    int[] querie2(int mes);
    int[] querie2(int mes, int fil);
    int[] querie3VezesComprado(String codProd);
    int[] querie3Clientes(String codProd);

    boolean existeCodCliente(String codCli);
    boolean existeCodProd(String codProd);

    int getFILIAIS();
}