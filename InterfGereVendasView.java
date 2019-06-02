import java.util.Map;
import java.util.Set;

public interface InterfGereVendasView {

    int menu();
    int submenuQuerie2();

    int getMes();

    void printPaginacao(Set<String> nuncaComprados);

    void printOpInvalida();

    void printNumMesInvalido();

    void printQuerie2Global(int[] total);

    void printQuerie2Filial(Map<Integer,int[]> querie2);

    String getCodCliente();

    void printInvalido(String cod);
}