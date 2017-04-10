package Principal;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author James
 */
public class Main {

    private static ArrayList<Processo> p = new ArrayList<>();
    private static DecimalFormat f = new DecimalFormat("#.#");

    private static void algRR() {
        int tempo_corrido = p.get(0).getChegada();
        int quantum = 2;
        int contador = 0;
        int cont_exec = 0;
        int cont_processo = 1;
        ArrayList<Processo> exec = new ArrayList<>();
        exec.add(p.get(0));
        Processo p_temp = exec.get(0);
        
        while(!exec.isEmpty()){
            p_temp.setTmprest(p_temp.getTmprest() - 1);
            contador++;
            tempo_corrido++;
            
            if(p_temp.getTmprest() == 0){ //Se o processo acaba, remove da fila de execução
                exec.remove(cont_exec);
                contador = 0;
            }else if(contador == quantum){ //Retira do processamento e substitui
                exec.set(cont_exec, p_temp);
                contador = 0;
                cont_exec++;
            }
            if(p.get(cont_processo).getChegada() == tempo_corrido){
                exec.add(p.get(cont_processo));
                cont_processo++;
            }
        }
    }

    private static void algFCFS() {
        float temp_espera = 0;
        float temp_retorno = 0;
        int difcheg = 0;
        float aux = 0;
        float aux1 = 0;

        //Ordenação
        Collections.sort(p, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Processo p1 = (Processo) o1;
                Processo p2 = (Processo) o2;
                return p1.getChegada() < p2.getChegada() ? -1 : (p1.getChegada() > p2.getChegada() ? +1 : 0);
            }
        });

        //Cálculo do tempo
        for (int i = 0; i < p.size(); i++) {
            if (i != 0) {
                difcheg = p.get(i).getChegada() - p.get(i - 1).getChegada();
                aux = (p.get(i - 1).getDuracao() - difcheg) + aux;
                temp_espera = aux + temp_espera;
                aux1 = aux1 + (p.get(i).getDuracao() - difcheg);
                temp_retorno = temp_retorno + aux1;
            } else {
                temp_retorno = p.get(i).getDuracao();
                aux1 = temp_retorno;
            }
        }
        System.out.println("FCFS " + f.format(temp_retorno / p.size()) + " " + f.format(temp_espera / p.size()) + " " + f.format(temp_espera / p.size()));
    }

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
        //exibeArray();
        algFCFS();
        //exibeArray();
        algRR();
    }
}
