/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jeupuzzle.vue;

import javax.swing.*;
import java.awt.*;
import com.mycompany.jeupuzzle.controleur.ControleurJeu;

/**
 * Vue représentant le menu d'accueil pour configurer le duel.
 */
public class PanneauMenu extends JPanel {
    
    private JTextField champJoueur1;
    private JTextField champJoueur2;
    private JButton btnDemarrer;

    public PanneauMenu(ControleurJeu controleur) {
        // Utilisation d'un layout simple pour empiler les éléments
        setLayout(new GridLayout(4, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Des marges esthétiques

        // Création des composants
        JLabel titre = new JLabel("Configuration du Duel", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 24));

        champJoueur1 = new JTextField("Nom du Joueur 1");
        champJoueur2 = new JTextField("Nom du Joueur 2");

        btnDemarrer = new JButton("Démarrer le Duel !");
        btnDemarrer.setFont(new Font("Arial", Font.BOLD, 18));
        btnDemarrer.setBackground(new Color(100, 200, 100)); // Un joli bouton vert
        
        // On attache le contrôleur au bouton
        btnDemarrer.setActionCommand("DEMARRER_DUEL"); // Un mot-clé pour que le contrôleur reconnaisse ce clic
        btnDemarrer.addActionListener(controleur);

        // Ajout des composants au panneau
        add(titre);
        add(champJoueur1);
        add(champJoueur2);
        add(btnDemarrer);
    }

    // Méthodes pour que le contrôleur puisse lire les noms tapés
    public String getNomJoueur1() {
        return champJoueur1.getText();
    }

    public String getNomJoueur2() {
        return champJoueur2.getText();
    }
}
