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
                        break;
                case 2: int[] total = new int[2];
                        int mes = this.view.getMes();
                        if(mes<1 || mes>12){
                           this.view.printNumMesInvalido();
                           break;
                        }
                        int opSub = this.view.submenuQuerie2();
                        switch(opSub)
                        {
                            case 1: total = this.model.querie2global(mes-1);
                                    break;
                            case 2: total = this.model.querie2filial(mes-1,this.view.getNumFilial()-1);
                                    break;
                            default: this.view.printOpInvalida();
                                    break;
                        }
                        this.view.printResQuerie2(total);
                        break;
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