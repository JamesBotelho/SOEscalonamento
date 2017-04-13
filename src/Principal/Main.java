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
    private static DecimalFormat f = new DecimalFormat("0.0");

    private static void algRR() {
        int tempo_corrido = p.get(0).getClone().getChegada();
        int quantum = 2;
        int contador = 0;
        ArrayList<Processo> exec = new ArrayList<>(); //Fila de prontos
        ArrayList<Processo> conc = new ArrayList<>(); //Fila de concluídos

        for (Processo p1 : p) {
            if (p1.getChegada() == tempo_corrido) {
                exec.add(p1.getClone());
            }
        }

        Processo p_temp = exec.get(0);
        exec.remove(0);
        p_temp.setTemp_resp(tempo_corrido - p_temp.getChegada());
        p_temp.setPexec(false);

        while (true) {
            if (p_temp == null) {
                if (exec.isEmpty()) {
                    break;
                }
                p_temp = exec.get(0);
                exec.remove(0);
                p_temp.setEspera_pronto(p_temp.getEspera_pronto() + (tempo_corrido - p_temp.getContexto()));
                if (p_temp.isPexec()) {
                    p_temp.setPexec(false);
                    p_temp.setTemp_resp(tempo_corrido - p_temp.getChegada());
                }
            }
            //CPU
            p_temp.setTmprest(p_temp.getTmprest() - 1);
            contador++;
            tempo_corrido = tempo_corrido + 1;
            for (Processo p1 : p) { //Procura processo para adicionar a fila
                if (p1.getChegada() == tempo_corrido) {
                    exec.add(p1.getClone());
                } else if (p1.getChegada() > tempo_corrido) {
                    break;
                }
            }

            if (p_temp.getTmprest() == 0) { //Se o processo acaba, remove da CPU
                contador = 0;
                p_temp.setTemp_ret(tempo_corrido - p_temp.getChegada());
                conc.add(p_temp);
                p_temp = null;
            } else if (contador == quantum) { //Retira do processamento e joga na fila
                p_temp.setContexto(tempo_corrido);
                exec.add(p_temp);
                contador = 0;
                p_temp = null;
            }
        }
        float temp_espera = 0;
        float temp_resposta = 0;
        float temp_retorno = 0;
        for (int i = 0; i < conc.size(); i++) {
            temp_espera = temp_espera + conc.get(i).getEspera_pronto();
            temp_resposta = temp_resposta + conc.get(i).getTemp_resp();
            temp_retorno = temp_retorno + conc.get(i).getTemp_ret();
        }

        System.out.println("RR " + f.format(temp_retorno / conc.size()) + " " + f.format(temp_resposta / conc.size()) + " " + f.format(temp_espera / conc.size()));
    }

    private static void algSJF() {
        int tempo_corrido = p.get(0).getClone().getChegada();
        ArrayList<Processo> exec = new ArrayList<>();
        ArrayList<Processo> conc = new ArrayList<>();
        Processo p_temp = null;
        boolean alt = true;
        for (Processo p1 : p) {
            if (p1.getChegada() == tempo_corrido) {
                exec.add(p1.getClone());
            }
        }

        while (true) {
            if (exec.size() > 1 && alt) {
                Collections.sort(exec, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Processo p1 = (Processo) o1;
                        Processo p2 = (Processo) o2;
                        return p1.getDuracao() < p2.getDuracao() ? -1 : (p1.getDuracao() > p2.getDuracao() ? +1 : 0);
                    }
                });
                alt = false;
            }
            if (p_temp == null) {
                if (exec.isEmpty()) {
                    break;
                }
                p_temp = exec.get(0);
                exec.remove(0);
                p_temp.setTemp_resp(tempo_corrido - p_temp.getChegada());
            }
            while (p_temp.getTmprest() != 0) {
                if (alt) {
                    Collections.sort(exec, new Comparator() {
                        @Override
                        public int compare(Object o1, Object o2) {
                            Processo p1 = (Processo) o1;
                            Processo p2 = (Processo) o2;
                            return p1.getDuracao() < p2.getDuracao() ? -1 : (p1.getDuracao() > p2.getDuracao() ? +1 : 0);
                        }
                    });
                    alt = false;
                }
                p_temp.setTmprest(p_temp.getTmprest() - 1);

                tempo_corrido++;

                for (Processo p1 : p) {
                    if (p1.getChegada() == tempo_corrido) {
                        exec.add(p1.getClone());
                        alt = true;
                    }
                }
            }
            p_temp.setTemp_ret(tempo_corrido - p_temp.getChegada());
            conc.add(p_temp);
            p_temp = null;
        }

        float tempo_resposta = 0;
        float tempo_retorno = 0;

        for (Processo p1 : conc) {
            tempo_resposta = tempo_resposta + p1.getTemp_resp();
            tempo_retorno = tempo_retorno + p1.getTemp_ret();
        }
        System.out.println("SJF " + f.format(tempo_retorno / conc.size()) + " " + f.format(tempo_resposta / conc.size()) + " " + f.format(tempo_resposta / conc.size()));
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
            p.forEach((p1) -> {
                System.out.println(p1.toString());
            });
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
        algFCFS();
        algSJF();
        algRR();
    }
}
