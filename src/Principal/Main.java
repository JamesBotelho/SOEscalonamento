package Principal;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author James
 */
public class Main {

    private static ArrayList<Processo> p = new ArrayList<>();


    private static void LeArquivo(String arq) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(arq);
        } catch (FileNotFoundException ex) {
            System.err.println("Arquivo não encontrado");
            System.exit(1);
        }
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(reader);
        String linha = null;
        try {
            linha = br.readLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        if (linha == null) {
            System.err.println("O arquivo está vazio");
            System.exit(1);
        }
        int i = 0;
        int chegada = 0;
        int duracao = 0;
        Processo pr = null;
        while (linha != null) {
            String nome = "P" + String.valueOf(i);
            try {
                chegada = Integer.parseInt(linha.substring(0, linha.indexOf(' ')));
                duracao = Integer.parseInt(linha.substring(linha.indexOf(' ') + 1, linha.length()));
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                System.err.println("O arquivo não está no formato correto!");
                System.exit(1);
            }
            pr = new Processo(nome, duracao, duracao, chegada);
            p.add(pr);
            try {
                linha = br.readLine();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            i++;
        }
    }

    public static void exibeArray() {
        if (!p.isEmpty()) {
            for (Processo p1 : p) {
                System.out.println(p1.toString());
            }
        }
    }

    public static void main(String[] args) {
        String arq = null;

        if (args.length == 0) {
            arq = "entrada.txt";
        } else {
            arq = args[0];
        }

        LeArquivo(arq);
        exibeArray();
        exibeArray();
    }
}
