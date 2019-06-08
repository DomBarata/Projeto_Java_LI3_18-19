import java.io.Serializable;
import java.util.*;

import static java.lang.System.out;

public class GereVendasController implements InterfGereVendasController, Serializable {

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
                case 1: Crono.start();
                        Set<String> nuncaComprados = this.model.querie1();
                        this.view.tempoDeExecucao(Crono.stop());
                        out.println("O número de produtos nunca comprados é " + nuncaComprados.size());
                        this.view.printPaginacao(nuncaComprados);
                        break;
                case 2: Crono.start();
                        int[] total = new int[2];
                        int mes = this.view.getMes();
                        if(mes<1 || mes>12){
                            this.view.printNumMesInvalido();
                            break;
                        }
                        int opSub = this.view.submenuQuerie2();
                        if(opSub == 1){
                            Crono.start();
                            total = this.model.querie2(mes);
                            this.view.tempoDeExecucao(Crono.stop());
                            this.view.printQuerie2(total);
                        }else if(opSub == 2){
                            Crono.start();
                            for(int i = 0; i < this.model.getFILIAIS(); i++){
                                total = this.model.querie2(mes,i);
                                this.view.printQuerie2(total,i);
                            }
                            this.view.tempoDeExecucao(Crono.stop());
                            this.view.waiting();
                        }else{
                            this.view.printOpInvalida();
                        }
                        break;
                case 3: String cod = this.view.getCodCliente();
                        if(this.model.existeCodCliente(cod)){
                            Crono.start();
                            List<Integer> compras = this.model.Querie3TotalComprasCliente(cod);
                            List<Integer> produtos = this.model.Querie3TotalProds(cod);
                            List<Double> gasto = this.model.Querie3TotalGasto(cod);
                            this.view.tempoDeExecucao(Crono.stop());
                            this.view.printPaginacaoMes("produtos",compras,produtos,gasto);
                        }else{
                            this.view.printInvalido(cod);
                        }
                        break;
                case 4: String prod = this.view.getCodProduto();
                        if(this.model.existeCodProd(prod)){
                            Crono.start();
                                List<Integer> a = this.model.querie4getClientes(prod);
                                List<Integer> b = this.model.querie4getQuantidade(prod);
                                List<Double> c = this.model.querie4getTotalFaturado(prod);
                            this.view.tempoDeExecucao(Crono.stop());
                            this.view.printPaginacaoMes("clientes", a, b, c);
                            break;
                        }else{
                            this.view.printInvalido(prod);
                        }
                        break;
                case 5: String cli = this.view.getCodCliente();
                        if(this.model.existeCodCliente(cli)) {
                            Crono.start();
                            Map<Integer, Set<String>> produtos = this.model.querie5(cli);
                            this.view.tempoDeExecucao(Crono.stop());
                            this.view.printPaginacao(produtos);
                        }else{
                            this.view.printInvalido(cli);
                        }
                        break;
                case 6: int x = this.view.getX();
                        Crono.start();
                        List<String> prods = this.model.querie6ProdsMaisComprados(x);
                        List<Integer> prodsEClientes = this.model.querie6Clientes(prods);
                        this.view.tempoDeExecucao(Crono.stop());
                        this.view.printPaginacao(prods, prodsEClientes, x);
                        break;
                case 7: Crono.start();
                        List<List<String>> toView = this.model.querie7();
                        this.view.tempoDeExecucao(Crono.stop());
                        this.view.printPaginacao(toView);
                        break;
                case 8: int x8 = this.view.getX();
                        Map<Integer, Set<String>> clientes;
                        Crono.start();
                        clientes = this.model.querie8(x8);
                        this.view.tempoDeExecucao(Crono.stop());
                        this.view.printPaginacao(x8,clientes);
                        break;
                case 9: String codProd = this.view.getCodProduto();
                        int x9 = this.view.getX();
                        if(this.model.existeCodProd(codProd)) {
                            Map<Integer, Map<String, Double>> clis;
                            Crono.start();
                            clis = this.model.querie9(codProd);
                            this.view.tempoDeExecucao(Crono.stop());
                            this.view.printPaginacao(clis, x9);
                        }else{
                            this.view.printInvalido(codProd);
                        }
                        break;
                case 10:Map<Integer,List<Map<String,Double>>> meses;
                        Crono.start();
                        meses = this.model.querie10();
                        this.view.tempoDeExecucao(Crono.stop());
                        this.view.printQuerie10(meses);
                        break;
                case 11:out.println("Ficheiro: " + this.model.getVENDAS());
                        out.println("Vendas Erradas: " + (this.model.getVENDASTOTAL() - this.model.getVENDASVALIDAS()));
                        out.println("Número de Produtos: " + (this.model.getCatProdsSize()));
                        out.println("Número de produtos diferentes comprados: " + (this.model.getCatProdsSize()-this.model.querie1().size()));
                        out.println("Número de produtos não comprados: " + this.model.querie1().size());
                        out.println("Número de clientes: " + this.model.getCatCliSize());
                        out.println("Número de clientes diferentes comprados: " + (this.model.getClientesQueCompraram()));
                        out.println("Número de produtos não comprados: " + (this.model.getCatCliSize()-this.model.getClientesQueCompraram()));
                        out.println("Compras com valor 0.0 :" + this.model.getVENDASGRATIS());
                        out.println("Faturação total: " + this.model.getFaturacaoTotal() + "€");
                        int[] totalCompras = new int[12];
                        for(int mees=0; mees<12; mees++){
                            totalCompras[mees] = this.model.getTotalCompras(mees);
                        }
                        this.view.printTotalComprasMes(totalCompras);
                case 0: break;
                default: this.view.printOpInvalida();
            }
        }while (op != 0);
    }
}