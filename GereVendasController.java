
import java.util.Set;

import static java.lang.System.out;

public class GereVendasController implements InterfGereVendasController {

    private InterfGereVendasModel model;
    private InterfGereVendasView view;



    public void setModel(InterfGereVendasModel model) {
        this.model = model;
    }

    public void setView(InterfGereVendasView view) {
        this.view = view;
    }

    public void start() {
        int op;

        do{
            op = this.view.menu();
            switch(op)
            {
                case 1: Set<String> nuncaComprados = this.model.querie1();
                        this.view.printPaginacao(nuncaComprados);
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 0:break;
                default:out.println("Essa opção não existe, por favor selecione outra opção");
            }

        }while (op != 0);
    }
}
