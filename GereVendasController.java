import java.util.*;

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
                case 2: int[] total;
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
                        List<Integer> compras = this.model.Querie3TotalComprasCliente(cod);
                        List<Integer> produtos = this.model.Querie3TotalProds(cod);
                        List<Double> gasto = this.model.Querie3TotalGasto(cod);
                        this.view.printQuerie3(compras,produtos,gasto);
                    }else{
                        this.view.printInvalido(cod);
                    }
                    break;
                case 4: String prod = this.view.getCodProduto();
                    this.view.printMes(this.model.querie4getClientes(prod),
                            this.model.querie4getQuantidade(prod),
                            this.model.querie4getTotalFaturado(prod));
                case 5: List<InterfFilial> fil = this.model.getFil();
                    for(InterfFilial f:fil) {
                        out.println(f);
                    }
                case 6: int x = this.view.querie6getX();
                        Map<String, Integer> prods = new TreeMap<>();

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