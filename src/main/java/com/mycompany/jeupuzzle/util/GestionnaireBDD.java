package com.mycompany.jeupuzzle.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Gère la persistance des scores dans la base de données MySQL.
 * Utilise le modèle JDBC pour l'insertion des données.
 */
public class GestionnaireBDD {
    
    // Paramètres de connexion pour XAMPP/WAMP
    private static final String URL = "jdbc:mysql://localhost:3306/puzzle_db?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Laisser vide pour XAMPP par défaut

    /**
     * Enregistre les résultats du duel dans la table 'historiques'.
     */
    public static void enregistrerDuel(String j1, int s1, String j2, int s2, String gagnant) {
        // Requête SQL paramétrée pour éviter les injections SQL
        String sql = "INSERT INTO historiques (joueur1, score1, joueur2, score2, gagnant) VALUES (?, ?, ?, ?, ?)";

        // Utilisation du try-with-resources pour fermer automatiquement la connexion
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Remplacement des "?" par les vraies valeurs
            pstmt.setString(1, j1);
            pstmt.setInt(2, s1);
            pstmt.setString(3, j2);
            pstmt.setInt(4, s2);
            pstmt.setString(5, gagnant);

            // Exécution de la mise à jour
            pstmt.executeUpdate();
            System.out.println("Succès : Les scores du duel ont été enregistrés dans la BDD.");

        } catch (SQLException e) {
            System.err.println("Erreur de connexion BDD : " + e.getMessage());
        }
    }
}