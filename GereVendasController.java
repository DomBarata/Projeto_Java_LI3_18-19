import java.util.List;
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
                        if(opSub == 1){
                            total = this.model.querie2(mes);
                            this.view.printQuerie2(total);
                        }else if(opSub == 2){
                            for(int i = 0; i < this.model.getFILIAIS(); i++){
                                total = this.model.querie2(mes,i);
                                this.view.printQuerie2(total,i);
                            }
                        }else{
                            this.view.printOpInvalida();
                        }
                        break;
                case 3: String cod = this.view.getCodCliente();
                    if(this.model.existeCodCliente(cod)){
                        List<Integer> compras = this.model.getTotalComprasCliente(cod);
                        List<Integer> produtos = this.model.getTotalProds(cod);
                        List<Double> gasto = this.model.getTotalGasto(cod);
                        this.view.printQuerie3(compras,produtos,gasto);
                    }else{
                        this.view.printInvalido(cod);
                    }
                    break;
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