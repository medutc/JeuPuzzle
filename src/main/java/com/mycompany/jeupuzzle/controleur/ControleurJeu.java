package com.mycompany.jeupuzzle.controleur;

import com.mycompany.jeupuzzle.modele.Grille;
import com.mycompany.jeupuzzle.modele.SessionJeu;
import com.mycompany.jeupuzzle.modele.Joueur;
import com.mycompany.jeupuzzle.vue.FenetreJeu;
import com.mycompany.jeupuzzle.vue.PanneauGrille;
import com.mycompany.jeupuzzle.vue.PanneauMenu;
import com.mycompany.jeupuzzle.util.GestionnaireBDD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JOptionPane;

public class ControleurJeu implements ActionListener {
    
    private Grille grille;
    private FenetreJeu fenetre;
    private PanneauGrille panneauGrille;
    private PanneauMenu panneauMenu;
    
    private SessionJeu session; 
    private int deplacements;

    public ControleurJeu() {
        fenetre = new FenetreJeu();
        panneauMenu = new PanneauMenu(this);
        fenetre.setPanneauMenu(panneauMenu);
        fenetre.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String commande = e.getActionCommand();

        if (commande.equals("DEMARRER_DUEL")) {
            preparerDuel();
        } else if (commande.contains(",")) {
            jouerCoup(commande);
        }
    }

    private void preparerDuel() {
        String n1 = panneauMenu.getNomJoueur1();
        String n2 = panneauMenu.getNomJoueur2();
        
        if (n1.isEmpty() || n2.isEmpty()) {
            JOptionPane.showMessageDialog(fenetre, "Veuillez saisir les noms des deux joueurs !");
            return;
        }

        // On initialise la session avec les deux joueurs
        session = new SessionJeu(n1, n2);
        session.determinerPremierJoueur(new Random().nextBoolean());
        
        JOptionPane.showMessageDialog(fenetre, 
                "Le sort a désigné : " + session.getJoueurActuel().getNom() + " pour commencer !", 
                "Tirage au sort", 
                JOptionPane.INFORMATION_MESSAGE);
        
        lancerNouvellePartie();
    }

    private void lancerNouvellePartie() {
        grille = new Grille(3);
        grille.melanger(100); 
        deplacements = 0;

        panneauGrille = new PanneauGrille(3, this);
        panneauGrille.mettreAJour(grille, session.getJoueurActuel().getNom(), deplacements);
        
        fenetre.setPanneauGrille(panneauGrille);
    }

    private void jouerCoup(String coordStr) {
        String[] coord = coordStr.split(",");
        int x = Integer.parseInt(coord[0]);
        int y = Integer.parseInt(coord[1]);

        if (grille.deplacer(x, y)) {
            deplacements++;
            panneauGrille.mettreAJour(grille, session.getJoueurActuel().getNom(), deplacements);

            if (grille.estResolu()) {
                gererVictoireEtape();
            }
        }
    }

    private void gererVictoireEtape() {
        // Le joueur vient de gagner, on enregistre son score
        session.getJoueurActuel().setScoreFinal(deplacements);
        session.incrementerToursTermines();

        if (session.getNbToursTermines() == 1) { 
            // C'est la fin du 1er tour
            String message = "Bravo " + session.getJoueurActuel().getNom() + " !\n" +
                             "Score : " + deplacements + " coups.\n\n" +
                             "Au tour de l'adversaire !";
            JOptionPane.showMessageDialog(fenetre, message);
            
            // On prépare le 2ème tour avec LA MÊME GRILLE
            session.changerJoueur();
            grille.rechargerPlateauInitial(); 
            deplacements = 0; 
            panneauGrille.mettreAJour(grille, session.getJoueurActuel().getNom(), deplacements); 
            
        } else {
            // C'est la fin du 2ème tour
            conclureDuel();
        }
    }

    private void conclureDuel() {
        Joueur j1 = session.getJoueur1();
        Joueur j2 = session.getJoueur2();
        
        String gagnant;
        if (j1.getScoreFinal() < j2.getScoreFinal()) {
            gagnant = j1.getNom();
        } else if (j2.getScoreFinal() < j1.getScoreFinal()) {
            gagnant = j2.getNom();
        } else {
            gagnant = "Égalité";
        }

        String bilan = "--- RÉSULTATS DU DUEL ---\n" +
                       j1.getNom() + " : " + j1.getScoreFinal() + " coups\n" +
                       j2.getNom() + " : " + j2.getScoreFinal() + " coups\n\n" +
                       "🏆 VAINQUEUR : " + gagnant;

        JOptionPane.showMessageDialog(fenetre, bilan, "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
        
        // Enregistrement dans MySQL
        GestionnaireBDD.enregistrerDuel(j1.getNom(), j1.getScoreFinal(), j2.getNom(), j2.getScoreFinal(), gagnant);
        
        // Retour au menu principal
        fenetre.setPanneauMenu(panneauMenu);
    }
}