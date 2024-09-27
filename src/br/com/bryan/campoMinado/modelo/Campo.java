package br.com.bryan.campoMinado.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {

    private final int linha;
    private final int coluna;

    private boolean aberto = false;
    private boolean minado = false;
    private boolean marcado = false;

    private List<Campo> vizinhos = new ArrayList<>();
    private List<CampoObervador> observadores = new ArrayList<>();

    Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public void registrarObservador(CampoObervador observador) {
        observadores.add(observador);
    }

    private void notificarObservador(CampoEvento evento) {
        observadores.forEach(o -> o.eventoOcorreu(this, evento));
    }

    boolean adicionarVizinho(Campo vizinho) {
        boolean linhaDiferente = this.linha != vizinho.linha;
        boolean colunaDiferente = this.coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(this.linha - vizinho.linha);
        int deltaColuna = Math.abs(this.coluna - vizinho.coluna);
        int deltaGeral = deltaLinha + deltaColuna;

        if (deltaGeral == 1 && !diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }
    }


    public void alternarMarcacao() {
        if (!aberto) {
            this.marcado = !this.marcado;

            if (marcado) {
                notificarObservador(CampoEvento.MARCAR);
            } else {
                notificarObservador(CampoEvento.DESMARCAR);
            }
        }
    }

    public boolean abrir(){

        if (!aberto && !marcado){
            if(minado){
                notificarObservador(CampoEvento.EXPLODIR);
                return true;
            }

            setAberto(true);
            notificarObservador(CampoEvento.ABRIR);
            if (vizinhancaSegura()){
                vizinhos.forEach(v -> v.abrir());
            }
            return true;

        } else {
            return false;
        }
    }

    public boolean vizinhancaSegura(){
        return vizinhos.stream().noneMatch(v -> v.minado);
    }


    void minar(){
        this.minado = true;
    }

    public boolean isMinado(){
        return minado;
    }

    public boolean isMarcado() {
        return this.marcado;
    }

    void setAberto(boolean aberto) {
        this.aberto = aberto;
        if (this.aberto) {
            notificarObservador(CampoEvento.ABRIR);
        }
    }

    public boolean isAberto() {
        return this.aberto;
    }

    public boolean isFechado() {
        return !this.isAberto();
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    boolean objetivoAlcancado(){
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }

    public int minasNaVizinhanca(){
        return (int) vizinhos.stream().filter(v -> v.minado).count();
    }

    void reiniciar(){
        aberto = false;
        minado = false;
        marcado = false;
        notificarObservador(CampoEvento.REINICIAR);
    }


}
