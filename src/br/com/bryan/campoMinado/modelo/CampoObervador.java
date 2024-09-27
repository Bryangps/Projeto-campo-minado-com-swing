package br.com.bryan.campoMinado.modelo;

@FunctionalInterface
public interface CampoObervador {

    public void eventoOcorreu(Campo campo, CampoEvento evento);
}
