import java.util.Map;
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
                        Map<Integer, int[]> res =  this.model.querie2(mes-1);
                        if(opSub == 1){
                            for(Map.Entry<Integer, int[]> entry: res.entrySet()) {
                                total[0] += entry.getValue()[0];
                                total[1] += entry.getValue()[1];
                            }
                            this.view.printQuerie2Global(total);
                        }else if(opSub == 2){
                            this.view.printQuerie2Filial(this.model.querie2(mes-1));
                        }else{
                            this.view.printOpInvalida();
                        }
                        break;
                case 3: String cod = this.view.getCodCliente();
                        if(this.model.existeCodCliente(cod)){

                        }else{
                            this.view.printInvalido(cod);
                        }

                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 0: break;
                default: this.view.printOpInvalida();
            }

        }while (op != 0);
    }
}