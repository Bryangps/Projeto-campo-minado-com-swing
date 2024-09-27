package br.com.bryan.campoMinado.visao;

import br.com.bryan.campoMinado.modelo.Tabuleiro;

import javax.swing.JFrame;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        Tabuleiro tabuleiro = new Tabuleiro(16,30,5);
        add(new PainelTabuleiro(tabuleiro));


        setTitle("Campo Minado");
        setSize(690, 438);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaPrincipal();


    }
}
