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
public class Processo {
    private String nome;
    private int duracao;
    private int tmprest;
    private int chegada;

    public Processo(String nome, int duracao, int tempo, int chegada) {
        this.nome = nome;
        this.duracao = duracao;
        this.tmprest = tempo;
        this.chegada = chegada;
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

    @Override
    public String toString() {
        return "Processo{" + "nome=" + nome + ", duracao=" + duracao + ", tmprest=" + tmprest + ", chegada=" + chegada + '}';
    }
    
    
}
