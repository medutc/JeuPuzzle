/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jeupuzzle;

import com.mycompany.jeupuzzle.controleur.ControleurJeu;
import javax.swing.SwingUtilities;

/**
 * Classe principale pour lancer l'application.
 */
public class JeuPuzzle {

    public static void main(String[] args) {
        // Lance l'interface graphique de manière sécurisée
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Instancie le contrôleur, ce qui démarre tout le jeu (Modèle et Vue)
                new ControleurJeu();
            }
        });
    }
}