/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jeupuzzle.controleur;

import com.mycompany.jeupuzzle.modele.Grille;
import com.mycompany.jeupuzzle.vue.FenetreJeu;
import com.mycompany.jeupuzzle.vue.PanneauGrille;
import com.mycompany.jeupuzzle.vue.PanneauMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControleurJeu implements ActionListener {
    
    private Grille grille;
    private FenetreJeu fenetre;
    private PanneauGrille panneauGrille;
    private PanneauMenu panneauMenu;
    private int deplacements;

    // Variables pour le mode Duel
    private String nomJoueur1;
    private String nomJoueur2;

    public ControleurJeu() {
        // Au démarrage, on initialise juste la fenêtre et le menu
        fenetre = new FenetreJeu();
        panneauMenu = new PanneauMenu(this);
        
        fenetre.setPanneauMenu(panneauMenu); // On affiche le menu en premier
        fenetre.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String commande = e.getActionCommand();

        // Si le joueur a cliqué sur le bouton du menu
        if (commande.equals("DEMARRER_DUEL")) {
            // 1. Récupérer les noms
            nomJoueur1 = panneauMenu.getNomJoueur1();
            nomJoueur2 = panneauMenu.getNomJoueur2();
            
            // 2. Préparer la première partie (Tour du Joueur 1)
            JOptionPane.showMessageDialog(fenetre, "C'est à " + nomJoueur1 + " de jouer !");
            lancerNouvellePartie();
        } 
        // Sinon, c'est que le joueur a cliqué sur une case de la grille (coordonnées x,y)
        else if (commande.contains(",")) {
            String[] coordonnees = commande.split(",");
            int x = Integer.parseInt(coordonnees[0]);
            int y = Integer.parseInt(coordonnees[1]);

            if (grille.deplacer(x, y)) {
                deplacements++;
                panneauGrille.mettreAJour(grille);

                if (grille.estResolu()) {
                    JOptionPane.showMessageDialog(fenetre, 
                        "Bien joué ! " + deplacements + " déplacements.", 
                        "Fin du tour", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // TODO: Plus tard, ici on passera au tour du Joueur 2 !
                }
            }
        }
    }

    /**
     * Prépare la grille et bascule l'affichage.
     */
    private void lancerNouvellePartie() {
        grille = new Grille(4);
        grille.melanger(150);
        deplacements = 0;

        panneauGrille = new PanneauGrille(4, this);
        panneauGrille.mettreAJour(grille);
        
        fenetre.setPanneauGrille(panneauGrille); // On remplace le menu par la grille
    }
}