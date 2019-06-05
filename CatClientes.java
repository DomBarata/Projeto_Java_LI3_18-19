import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class CatClientes implements InterfCatClientes{
    private Set<String> clientes;

    public CatClientes(){
        this.clientes = new TreeSet<>();
    }
    public void adiciona(String s) {
        this.clientes.add(s);
    }

    @Override
    public boolean contains(String codCli) {
        return this.clientes.contains(codCli);
    }

    public boolean isEmpty(){
        return this.clientes.isEmpty();
    }
}
