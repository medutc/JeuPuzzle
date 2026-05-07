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

/**
 * Contrôleur principal faisant le lien entre la logique du jeu (Modèle) et l'affichage (Vue).
 * Gère également le déroulement des tours pour le mode Duel.
 */
public class ControleurJeu implements ActionListener {
    
    // Éléments du MVC
    private Grille grille;
    private FenetreJeu fenetre;
    private PanneauGrille panneauGrille;
    private PanneauMenu panneauMenu;
    
    // Variables pour la logique de la partie
    private int deplacements;
    private String nomJoueur1;
    private String nomJoueur2;
    private int scoreJoueur1;
    private boolean estTourJoueur1;

    public ControleurJeu() {
        // Initialisation de la fenêtre principale
        fenetre = new FenetreJeu();
        
        // Initialisation du menu d'accueil
        panneauMenu = new PanneauMenu(this);
        
        // On affiche le menu au démarrage de l'application
        fenetre.setPanneauMenu(panneauMenu);
        fenetre.setVisible(true);
    }

    /**
     * Méthode déclenchée à chaque clic sur un bouton (dans le menu ou sur la grille).
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String commande = e.getActionCommand();

        // 1. Si le joueur clique sur le bouton "Démarrer le Duel !" dans le menu
        if (commande.equals("DEMARRER_DUEL")) {
            nomJoueur1 = panneauMenu.getNomJoueur1();
            nomJoueur2 = panneauMenu.getNomJoueur2();
            estTourJoueur1 = true;
            
            // On prévient que le premier joueur commence
            JOptionPane.showMessageDialog(fenetre, 
                    "C'est à " + nomJoueur1 + " de commencer !", 
                    "Début du Duel", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            lancerNouvellePartie();
        } 
        
        // 2. Si le joueur clique sur une case de la grille (les coordonnées sont du type "x,y")
        else if (commande.contains(",")) {
            String[] coordonnees = commande.split(",");
            int x = Integer.parseInt(coordonnees[0]);
            int y = Integer.parseInt(coordonnees[1]);

            // Tenter de déplacer la case dans le Modèle
            if (grille.deplacer(x, y)) {
                deplacements++; // On incrémente le compteur de coups
                panneauGrille.mettreAJour(grille); // On rafraîchit l'affichage

                // Vérifier si le puzzle est résolu après ce déplacement
                if (grille.estResolu()) {
                    gererFinDeTour();
                }
            }
        }
    }

    /**
     * Initialise une nouvelle grille mélangée et l'affiche à l'écran.
     */
    private void lancerNouvellePartie() {
        // Création d'une nouvelle grille 4x4 et mélange aléatoire
        grille = new Grille(4);
        grille.melanger(150);
        deplacements = 0; // Remise à zéro du compteur pour ce tour

        // Création de l'interface visuelle de la grille
        panneauGrille = new PanneauGrille(4, this);
        panneauGrille.mettreAJour(grille);
        
        // Affichage de la grille dans la fenêtre
        fenetre.setPanneauGrille(panneauGrille);
    }

    /**
     * Gère la transition entre le Joueur 1 et le Joueur 2, ou la fin du duel.
     */
    private void gererFinDeTour() {
        if (estTourJoueur1) {
            // -- FIN DU TOUR DU JOUEUR 1 --
            scoreJoueur1 = deplacements;
            estTourJoueur1 = false;
            
            JOptionPane.showMessageDialog(fenetre, 
                "Bravo " + nomJoueur1 + ", tu as terminé en " + scoreJoueur1 + " coups !\n" +
                "Laisse la place... C'est au tour de " + nomJoueur2 + " !", 
                "Fin du Tour 1", 
                JOptionPane.INFORMATION_MESSAGE);
            
            lancerNouvellePartie(); // On génère un nouveau puzzle pour le Joueur 2
            
        } else {
            // -- FIN DU TOUR DU JOUEUR 2 --
            int scoreJoueur2 = deplacements;
            determinerGagnant(scoreJoueur1, scoreJoueur2);
        }
    }

    /**
     * Compare les scores, annonce le vainqueur et retourne au menu principal.
     */
    private void determinerGagnant(int s1, int s2) {
        String message = "--- RÉSULTATS DU DUEL ---\n\n" +
                         "Score de " + nomJoueur1 + " : " + s1 + " coups\n" +
                         "Score de " + nomJoueur2 + " : " + s2 + " coups\n\n";
        
        if (s1 < s2) {
            message += "🏆 Félicitations, " + nomJoueur1 + " remporte la victoire !";
        } else if (s2 < s1) {
            message += "🏆 Félicitations, " + nomJoueur2 + " remporte la victoire !";
        } else {
            message += "🤝 Égalité parfaite !";
        }

        JOptionPane.showMessageDialog(fenetre, message, "Résultat Final", JOptionPane.INFORMATION_MESSAGE);
        
        // TODO: C'est ici que l'on fera appel à GestionnaireBDD pour sauvegarder les scores dans la base de données
        
        // Retour automatique au menu pour pouvoir relancer un nouveau duel
        fenetre.setPanneauMenu(panneauMenu);
    }
}