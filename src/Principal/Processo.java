/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

/**
 *
 * @author James
 */
public class Processo implements Cloneable {

    private String nome;
    private int duracao;
    private int tmprest;
    private int chegada;
    private int espera_pronto;
    private int temp_resp;
    private int temp_ret;
    private int contexto;
    private boolean pexec;

    public Processo(String nome, int duracao, int tempo, int chegada) {
        this.nome = nome;
        this.duracao = duracao;
        this.tmprest = tempo;
        this.chegada = chegada;
        this.contexto = chegada;
        this.espera_pronto = 0;
        this.pexec = true;
        this.temp_resp = 0;
        this.temp_ret = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public int getTempo() {
        return tmprest;
    }

    public void setTempo(int tempo) {
        this.tmprest = tempo;
    }

    public int getTmprest() {
        return tmprest;
    }

    public void setTmprest(int tmprest) {
        this.tmprest = tmprest;
    }

    public int getChegada() {
        return chegada;
    }

    public void setChegada(int chegada) {
        this.chegada = chegada;
    }

    public int getEspera_pronto() {
        return espera_pronto;
    }

    public void setEspera_pronto(int espera_pronto) {
        this.espera_pronto = espera_pronto;
    }

    public int getTemp_resp() {
        return temp_resp;
    }

    public void setTemp_resp(int temp_resp) {
        this.temp_resp = temp_resp;
    }

    public boolean isPexec() {
        return pexec;
    }

    public void setPexec(boolean pexec) {
        this.pexec = pexec;
    }

    public int getTemp_ret() {
        return temp_ret;
    }

    public void setTemp_ret(int temp_ret) {
        this.temp_ret = temp_ret;
    }

    public int getContexto() {
        return contexto;
    }

    public void setContexto(int contexto) {
        this.contexto = contexto;
    }

    public Processo getClone() {
        try {
            // call clone in Object.
            return (Processo) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(" Cloning not allowed. ");
            return this;
        }
    }

    @Override
    public String toString() {
        return "Processo{" + "nome=" + nome + ", duracao=" + duracao + ", tmprest=" + tmprest + ", chegada=" + chegada + '}';
    }

}
