import static java.lang.System.out;

public class GereVendasAppMVC {

    public static void main(String[] args) {

        InterfGereVendasModel model = new GereVendasModel();
        model.createData();
        if(model.isEmpty()) { out.println("ERRO INICIALIZACAO"); System.exit(-1); }
        InterfGereVendasView view = new GereVendasView();
        InterfGereVendasController control = new GereVendasController();
        control.setModel(model);
        control.setView(view);
        control.start();
        System.exit(0);
    }
}
