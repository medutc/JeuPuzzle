/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jeupuzzle.controleur;

import com.mycompany.jeupuzzle.modele.Grille;
import com.mycompany.jeupuzzle.vue.FenetreJeu;
import com.mycompany.jeupuzzle.vue.PanneauGrille;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Contrôleur faisant le lien entre la Grille (Modèle) et la Fenetre (Vue).
 */
public class ControleurJeu implements ActionListener {
    
    private Grille grille;
    private FenetreJeu fenetre;
    private PanneauGrille panneauGrille;
    private int deplacements;

    public ControleurJeu() {
        // 1. Initialiser le modèle
        grille = new Grille(4); // Puzzle 4x4
        grille.melanger(150);
        deplacements = 0;

        // 2. Initialiser la vue
        fenetre = new FenetreJeu();
        panneauGrille = new PanneauGrille(4, this);
        fenetre.setPanneauGrille(panneauGrille);
        
        // 3. Mettre à jour et afficher
        panneauGrille.mettreAJour(grille);
        fenetre.setVisible(true); // L'erreur que vous aviez n'apparaîtra plus !
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] coordonnees = e.getActionCommand().split(",");
        int x = Integer.parseInt(coordonnees[0]);
        int y = Integer.parseInt(coordonnees[1]);

        if (grille.deplacer(x, y)) {
            deplacements++;
            panneauGrille.mettreAJour(grille);

            if (grille.estResolu()) {
                JOptionPane.showMessageDialog(fenetre, 
                    "Félicitations ! Vous avez gagné en " + deplacements + " déplacements.", 
                    "Victoire", 
                    JOptionPane.INFORMATION_MESSAGE);
                // La sauvegarde en Base de données se fera ici plus tard
            }
        }
    }
}