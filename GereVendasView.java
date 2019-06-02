import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static java.lang.System.out;
import static java.lang.System.in;

/**
 * Classe que abstrai a utilização da classe Scanner, escondendo todos os
 * problemas relacionados com excepções, e que oferece métodos simples e
 * robustos para a leitura de valores de tipos simples e String.
 *
 * É uma classe de serviços, como Math e outras de Java, pelo que devem ser
 * usados os métodos de classe implementados.
 *
 * Utilizável em BlueJ, NetBeans, CodeBlocks ou Eclipse.
 *
 * Utilização típica:  int x = Input.lerInt();
 *                     String nome = Input.lerString();
 *
 * @author F. Mário Martins
 * @version 1.0 (6/2006)
 */


public class GereVendasView implements InterfGereVendasView {

    /**
     * Métodos de Classe
     */
    public static String lerString() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        String txt = "";
        while(!ok) {
            try {
                txt = input.nextLine();
                ok = true;
            }
            catch(InputMismatchException e)
            { out.println("Texto Invalido");
                out.print("Novo valor: ");
                input.nextLine();
            }
        }
        //input.close();
        return txt;
    }


    public static int lerInt() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        int i = 0;
        while(!ok) {
            try {
                i = input.nextInt();
                ok = true;
            }
            catch(InputMismatchException e)
            { out.println("Inteiro Invalido");
                out.print("Novo valor: ");
                input.nextLine();
            }
        }
        //input.close();
        return i;
    }

    public static double lerDouble() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        double d = 0.0;
        while(!ok) {
            try {
                d = input.nextDouble();
                ok = true;
            }
            catch(InputMismatchException e)
            { out.println("Valor real Invalido");
                out.print("Novo valor: ");
                input.nextLine();
            }
        }
        //input.close();
        return d;
    }

    public static float lerFloat() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        float f = 0.0f;
        while(!ok) {
            try {
                f = input.nextFloat();
                ok = true;
            }
            catch(InputMismatchException e)
            { out.println("Valor real Invalido");
                out.print("Novo valor: ");
                input.nextLine();
            }
        }
        //input.close();
        return f;
    }

    public static boolean lerBoolean() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        boolean b = false;
        while(!ok) {
            try {
                b = input.nextBoolean();
                ok = true;
            }
            catch(InputMismatchException e)
            {out.println("Booleano Invalido");
                out.print("Novo valor: ");
                input.nextLine();
            }
        }
        //input.close();
        return b;
    }

    public static short lerShort() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        short s = 0;
        while(!ok) {
            try {
                s = input.nextShort();
                ok = true;
            }
            catch(InputMismatchException e)
            { out.println("Short Invalido");
                out.print("Novo valor: ");
                input.nextLine();
            }
        }
        //input.close();
        return s;
    }

    public int menu(){
        out.println("1 - Lista ordenada alfabeticamente com os códigos dos produtos nunca comprados e o seu respectivo total");
        out.println("2 - Consultar número total global de vendas realizadas");

        /*out.println("");
        out.println("");
        out.println("");
        out.println("");
        out.println("");
        out.println("");
        out.println("");
        out.println("");
        out.println("");
        out.println("");
        out.println("");*/
        out.println("0 - Sair");


        return lerInt();
    }

    public int submenuQuerie2(){
        out.println("Como quer a apresentação dos resultados?");
        out.println("1- Resultados globais");
        out.println("2- Resultados filial a filial");

        return lerInt();
    }

    public int getMes(){
        out.println("Mes: ");

        return lerInt();
    }

    public void printNumMesInvalido(){
        out.println("Numero de mês inválido!");
        waiting();
    }

    public void printQuerie2Global(int[] total) {
        out.println("Número total de vendas: " +total[0]+ "" +
                "\nNúmero total de clientes: "+total[1]+"\n");
        waiting();
    }

    public void printQuerie2Filial(Map<Integer, int[]> querie2) {
        for(Map.Entry<Integer, int[]> entry : querie2.entrySet()){
            out.println("---------FILIAL " + (entry.getKey()+1) + "---------");
            out.println("Número de vendas: " + entry.getValue()[0]);
            out.println("Número de clientes: " + entry.getValue()[1]);
        }
        waiting();
    }

    @Override
    public String getCodCliente() {
        out.println("Insira o código do cliente");

        return lerString();
    }

    @Override
    public void printInvalido(String cod) {
        out.println("O código " + cod + " não existe");
        waiting();
    }

    public void printOpInvalida(){
        out.println("Opção inválida!");
    }

    public void printPaginacao(Set<String> set){
        waiting();
    }

    private void waiting(){
        try {
            in.read();
        }catch (IOException io){out.println(io);}
    }
}