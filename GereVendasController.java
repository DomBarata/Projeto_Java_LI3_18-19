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
                case 2: int[] total = new int[2];
                        int mes = this.view.getMes();
                        if(mes<1 || mes>12){
                            this.view.printNumInvalido();
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
                        if(this.model.existeCodCliente(prod)){
                            this.view.printMes(this.model.querie4getClientes(prod),
                            this.model.querie4getQuantidade(prod),
                            this.model.querie4getTotalFaturado(prod));
                        }else{
                            this.view.printInvalido(prod);
                        }
                        break;
                case 5: String cli = this.view.getCodCliente();
                        if(this.model.existeCodCliente(cli)) {
                            Map<Integer, Set<String>> produtos = this.model.querie5(cli);
                            this.view.printPaginacao(produtos);
                            //this.view.printI(this.model.getI());
                        }else{
                            this.view.printInvalido(cli);
                        }
                        break;
                case 6: int x = this.view.querie6getX();
                        if(x>0) {
                            Set<String> prods = this.model.querie6PodsMaisComprados(x);
                            Map<String, Integer> prodsEClientes = this.model.querie6Clientes(prods);
                            this.view.printQuerie6(prodsEClientes);
                        }else{
                            this.view.printNumInvalido();
                        }
                        break;
                case 7:
                case 8: int x8 = this.view.querie6getX();
                        if(x8>0) {
                            Map<Integer, Set<String>> clientes;
                            clientes = this.model.querie8();
                            this.view.printQuerie8(x8,clientes);
                            //this.view.printI(this.model.getI());
                        }else{
                            this.view.printNumInvalido();
                        }
                        break;
                case 9: String codProd = this.view.getCodProduto();
                        if(this.model.existeCodCliente(codProd)) {
                            int x9 = this.view.querie6getX();
                            if(x9>0) {
                                Map<Integer, Map<String, Double>> clis;
                                clis = this.model.querie9(codProd);
                                if(clis==null){this.view.printInvalido(codProd); break;}
                                this.view.printQuerie9(clis,x9);
                            }else{
                                this.view.printNumInvalido();
                            }
                        }else{
                            this.view.printInvalido(codProd);
                        }
                        break;
                case 10: List<Map<String,Double>> filiais = new ArrayList<>();
                         Map<Integer,List<Map<String,Double>>> meses = new TreeMap<>();
                         meses = this.model.querie10();
                         this.view.printQuerie10(meses, this.model.getFILIAIS());
                         break;
                case 0: break;
                default: this.view.printOpInvalida();
            }

        }while (op != 0);
    }
}