
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.InputMismatchException;
import static java.lang.System.out;
import static java.lang.System.in;

public class Main {


    public static void main(String[] args){
        Scanner input = new Scanner(in);

        int op;
        do {
            out.println("1 - Teste 1");
            out.println("2 - Teste 2");
            out.println("3 - Teste 3");
            out.println("4 - Teste 4");
            out.println("0 - Sair");

            op = input.nextInt();

            switch (op) {
                case 1:
                    Teste1.teste1();
                    break;
                case 2:
                    Teste2.teste2();
                    break;
                case 3:
                    Teste3.teste3();
                    break;
                case 4: //Teste4.teste4();
                    break;
                case 0: break;
            }
        }while(op!=0);
    }


}
