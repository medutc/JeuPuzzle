# 🧩 Jeu de Puzzle Glissant - Édition Compétitive

> Une application logicielle de bureau interactive, équitable et respectant l'architecture MVC.

## 📝 À propos du projet
Le Jeu de Puzzle Glissant (ou jeu du Taquin) est une application logicielle Desktop proposant une variante compétitive en duel. 

Ce projet a été développé dans le cadre du module JAVA (1ère année du Cycle Ingénieur à l'ENSA Berrechid). Il met en pratique des concepts avancés de programmation orientée-objet, de conception d'interfaces graphiques et applique rigoureusement le patron de conception MVC (Modèle-Vue-Contrôleur).

## ✨ Fonctionnalités Principales
* **Mode Duel Équitable :** Gestion de sessions à deux joueurs avec tirage au sort initial et rechargement exact de la grille pour le second joueur.
* **Interface Interactive (UI) :** Grille fluide développée avec Java Swing, intégrant un feedback visuel dynamique (code couleur vert/rouge) pour guider les joueurs.
* **Persistance des Données :** Enregistrement automatique de l'historique des parties, des scores (nombre de déplacements) et des vainqueurs dans une base de données MySQL via JDBC.
* **Architecture MVC :** Séparation stricte entre la logique métier (Modèle), l'interface (Vue) et les actions utilisateurs (Contrôleur).

## 🛠️ Stack Technique
* **Langage :** Java
* **Interface Graphique :** Java Swing / AWT
* **Base de Données :** MySQL
* **Connectivité :** JDBC (`mysql-connector-j` 8.2.0)
* **Build Tool & Dépendances :** Maven

## 🚀 Lancement en local

Si vous souhaitez faire tourner le projet sur votre machine :

1. **Préparer la base de données (XAMPP/WAMP) :**
   Démarrez MySQL et exécutez ce script dans phpMyAdmin :
   ```sql
   CREATE DATABASE IF NOT EXISTS puzzle_db;
   USE puzzle_db;

   CREATE TABLE IF NOT EXISTS historiques (
       id INT AUTO_INCREMENT PRIMARY KEY,
       joueur1 VARCHAR(50) NOT NULL,
       score1 INT NOT NULL,
       joueur2 VARCHAR(50) NOT NULL,
       score2 INT NOT NULL,
       gagnant VARCHAR(50) NOT NULL,
       date_partie DATETIME DEFAULT CURRENT_TIMESTAMP
   ) ENGINE=InnoDB;
  
2. **Cloner le dépôt :**
   ```bash
   git clone [https://github.com/medutc/JeuPuzzle.git](https://github.com/medutc/JeuPuzzle.git)
   cd jeuPuzzle

3. **Cloner le dépot :**
   ```bash
   mvn clean install
   mvn exec:java -Dexec.mainClass="com.mycompany.jeupuzzle.JeuPuzzle"
  
## 👨‍💻 Équipe de Développement
* **Ilyas Tarzi** - *Élève Ingénieur (1ère année Cycle Ingénieur)*
* **Moataz mohamed** - *Élève Ingénieur (1ère année Cycle Ingénieur)*
