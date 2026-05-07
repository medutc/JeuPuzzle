package com.mycompany.jeupuzzle.vue;

import javax.swing.*;
import java.awt.*;
import com.mycompany.jeupuzzle.modele.Grille;
import com.mycompany.jeupuzzle.controleur.ControleurJeu;

public class PanneauGrille extends JPanel {
    
    private JButton[][] boutons;
    private JLabel labelInfo; 
    private int taille;

    public PanneauGrille(int taille, ControleurJeu controleur) {
        this.taille = taille;
        this.boutons = new JButton[taille][taille];
        
        setLayout(new BorderLayout()); 

        // --- Barre d'information (Haut) ---
        labelInfo = new JLabel("Joueur : - | Coups : 0", SwingConstants.CENTER);
        labelInfo.setFont(new Font("Arial", Font.BOLD, 18));
        labelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(labelInfo, BorderLayout.NORTH);

        // --- Grille de boutons (Centre) ---
        JPanel grilleBoutons = new JPanel(new GridLayout(taille, taille, 5, 5));
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                JButton btn = new JButton();
                btn.setFont(new Font("Arial", Font.BOLD, 40));
                btn.setFocusPainted(false);
                btn.addActionListener(controleur);
                btn.setActionCommand(i + "," + j); 
                
                boutons[i][j] = btn;
                grilleBoutons.add(btn);
            }
        }
        add(grilleBoutons, BorderLayout.CENTER);
    }

    public void mettreAJour(Grille grille, String nomJoueur, int coups) {
        labelInfo.setText("Joueur : " + nomJoueur + "  |  Coups : " + coups);

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                int valeur = grille.getValeur(i, j);
                if (valeur == 0) {
                    boutons[i][j].setText("");
                    boutons[i][j].setBackground(Color.LIGHT_GRAY);
                } else {
                    boutons[i][j].setText(String.valueOf(valeur));
                    // 1. On met la couleur du texte (le numéro) en blanc
                    boutons[i][j].setForeground(Color.WHITE); 
                    
                    // 2. Formule mathématique pour deviner quelle valeur DEVRAIT être dans cette case
                    int valeurAttendue = (i * taille) + j + 1;
                    
                    // 3. Application des couleurs de fond
                    if (valeur == valeurAttendue) {
                        // Le nombre est à la bonne place -> Vert
                        boutons[i][j].setBackground(new Color(46, 204, 113)); 
                    } else {
                        // Le nombre est mal placé -> Rouge
                        boutons[i][j].setBackground(new Color(231, 76, 60)); 
                    }
                }
            }
        }
    }
}