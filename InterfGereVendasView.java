import java.util.Set;

public interface InterfGereVendasView {

    int menu();
    int submenuQuerie2();

    int getMes();
    int getNumFilial();

    void printResQuerie2(int[] total);
    void printPaginacao(Set<String> nuncaComprados);

    void printOpInvalida();

    void printNumMesInvalido();
}