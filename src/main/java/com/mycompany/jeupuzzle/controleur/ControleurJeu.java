package com.mycompany.jeupuzzle.controleur;

import com.mycompany.jeupuzzle.modele.Grille;
import com.mycompany.jeupuzzle.vue.FenetreJeu;
import com.mycompany.jeupuzzle.vue.PanneauGrille;
import com.mycompany.jeupuzzle.vue.PanneauMenu;
import com.mycompany.jeupuzzle.util.GestionnaireBDD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * Contrôleur principal gérant le cycle de vie du jeu (MVC).
 */
public class ControleurJeu implements ActionListener {
    
    // Attributs du Modèle et de la Vue
    private Grille grille;
    private FenetreJeu fenetre;
    private PanneauGrille panneauGrille;
    private PanneauMenu panneauMenu;
    
    // Variables de session de jeu
    private int deplacements;
    private String nomJoueur1;
    private String nomJoueur2;
    private int scoreTour1;
    private boolean estTourJoueur1; // Détermine qui est en train de jouer
    private String nomJoueurActuel;
    private Random random = new Random();

    public ControleurJeu() {
        // Initialisation des composants
        fenetre = new FenetreJeu();
        panneauMenu = new PanneauMenu(this);
        
        // Affichage du menu principal
        fenetre.setPanneauMenu(panneauMenu);
        fenetre.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String commande = e.getActionCommand();

        // Gestion du clic sur "Démarrer le Duel"
        if (commande.equals("DEMARRER_DUEL")) {
            preparerDuel();
        } 
        // Gestion du clic sur une case (ex: "2,1")
        else if (commande.contains(",")) {
            jouerCoup(commande);
        }
    }

    private void preparerDuel() {
        nomJoueur1 = panneauMenu.getNomJoueur1();
        nomJoueur2 = panneauMenu.getNomJoueur2();
        
        // Validation simple des noms
        if (nomJoueur1.isEmpty() || nomJoueur2.isEmpty()) {
            JOptionPane.showMessageDialog(fenetre, "Veuillez saisir les noms des deux joueurs !");
            return;
        }

        // TIRAGE AU SORT DU PREMIER JOUEUR
        estTourJoueur1 = random.nextBoolean();
        nomJoueurActuel = estTourJoueur1 ? nomJoueur1 : nomJoueur2;
        
        JOptionPane.showMessageDialog(fenetre, 
                "Le sort a désigné : " + nomJoueurActuel + " pour commencer !", 
                "Tirage au sort", 
                JOptionPane.INFORMATION_MESSAGE);
        
        lancerNouvellePartie();
    }

    private void jouerCoup(String coordStr) {
        String[] coord = coordStr.split(",");
        int x = Integer.parseInt(coord[0]);
        int y = Integer.parseInt(coord[1]);

        if (grille.deplacer(x, y)) {
            deplacements++;
            panneauGrille.mettreAJour(grille);

            if (grille.estResolu()) {
                gererVictoireEtape();
            }
        }
    }

    private void lancerNouvellePartie() {
        grille = new Grille(4);
        grille.melanger(150); // Mélange aléatoire
        deplacements = 0;

        panneauGrille = new PanneauGrille(4, this);
        panneauGrille.mettreAJour(grille);
        
        fenetre.setPanneauGrille(panneauGrille);
    }

    private void gererVictoireEtape() {
        if (scoreTour1 == 0) { 
            // C'est le premier joueur qui vient de finir
            scoreTour1 = deplacements;
            String message = "Bravo " + nomJoueurActuel + " !\n" +
                             "Score : " + scoreTour1 + " coups.\n\n" +
                             "Au tour de l'adversaire !";
            
            JOptionPane.showMessageDialog(fenetre, message);
            
            // Changement de joueur
            estTourJoueur1 = !estTourJoueur1;
            nomJoueurActuel = estTourJoueur1 ? nomJoueur1 : nomJoueur2;
            
            lancerNouvellePartie();
        } else {
            // C'est le deuxième joueur qui vient de finir
            int scoreTour2 = deplacements;
            conclureDuel(scoreTour1, scoreTour2);
        }
    }

    private void conclureDuel(int s1, int s2) {
        // Déterminer le gagnant
        String gagnant;
        // On compare qui a fait le moins de coups
        if (s1 < s2) {
            gagnant = estTourJoueur1 ? nomJoueur2 : nomJoueur1; // Le premier joueur
        } else if (s2 < s1) {
            gagnant = nomJoueurActuel; // Le deuxième joueur (celui qui joue maintenant)
        } else {
            gagnant = "Égalité";
        }

        String bilan = "--- RÉSULTATS DU DUEL ---\n" +
                       "Score 1er joueur : " + s1 + "\n" +
                       "Score 2ème joueur : " + s2 + "\n\n" +
                       "🏆 VAINQUEUR : " + gagnant;

        JOptionPane.showMessageDialog(fenetre, bilan, "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
        
        // SAUVEGARDE EN BASE DE DONNÉES
        // On enregistre les noms dans l'ordre de saisie pour la table historiques
        GestionnaireBDD.enregistrerDuel(nomJoueur1, s1, nomJoueur2, s2, gagnant);
        
        // Reset pour une future partie
        scoreTour1 = 0;
        fenetre.setPanneauMenu(panneauMenu);
    }

    /**
     * Point d'entrée principal de l'application.
     */
    public static void main(String[] args) {
        // Lancer l'application dans le thread de l'interface graphique
        javax.swing.SwingUtilities.invokeLater(() -> {
            new ControleurJeu();
        });
    }
}