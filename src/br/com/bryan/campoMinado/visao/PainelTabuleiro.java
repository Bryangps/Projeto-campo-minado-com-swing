package br.com.bryan.campoMinado.visao;

import br.com.bryan.campoMinado.modelo.Tabuleiro;

import javax.swing.*;
import java.awt.GridLayout;


public class PainelTabuleiro extends JPanel {

    public PainelTabuleiro(Tabuleiro tabuleiro) {
        setLayout(new GridLayout(tabuleiro.getLinhas(), tabuleiro.getColunas()));

        tabuleiro.paraCadaCampo(c -> add(new BotaoCampo(c)));

        tabuleiro.registrarObervador(e -> {
            SwingUtilities.invokeLater(() -> {
                if (e.isGanhou()){
                    JOptionPane.showMessageDialog(this, "Ganhou");
                } else {
                    JOptionPane.showMessageDialog(this, "Perdeu");
                }

                tabuleiro.reiniciar();
            });
        });
    }
}
