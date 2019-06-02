import java.util.Set;

public interface InterfGereVendasModel {

    void createData();

    Set<String> querie1();
    int[] querie2global(int mes);
    int[] querie2filial(int mes, int numFilial);
}