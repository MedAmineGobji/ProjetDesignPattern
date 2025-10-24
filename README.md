# 🚀 Space Invaders - Projet Design Patterns

![Java](https://img.shields.io/badge/Java-17+-orange.svg)
![JavaFX](https://img.shields.io/badge/JavaFX-21+-blue.svg)
![Maven](https://img.shields.io/badge/Maven-3.8+-green.svg)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

## 📋 Description du Projet

**Space Invaders** est un projet universitaire développé en Java qui démontre l'application de **4 patrons de conception (Design Patterns)** dans le contexte d'un jeu vidéo. Le jeu est une version améliorée du classique Space Invaders avec des fonctionnalités modernes et une architecture extensible.

### 🎯 Objectifs Pédagogiques

- Appliquer **4 Design Patterns** de manière concrète et justifiée
- Développer une architecture logicielle claire et extensible
- Utiliser JavaFX pour l'interface graphique
- Implémenter un système de logging complet
- Documenter le projet avec UML et Git

## 🎮 Fonctionnalités du Jeu

### Gameplay Principal
- **Contrôle du vaisseau** : Déplacement avec les flèches gauche/droite
- **Système de tir** : Tir avec la barre d'espace
- **Vagues d'ennemis** : Formations complexes avec différents types d'ennemis
- **Power-ups** : Améliorations temporaires du joueur
- **Progression** : 10 vagues avec difficulté croissante

### Types d'Ennemis
- **Basique** (Vert) : 10 points, lent, résistance normale
- **Rapide** (Orange) : 20 points, rapide, tire souvent
- **Lourd** (Violet) : 30 points, lent, 2 points de vie

### Power-ups Disponibles
- **🟡 Tir Rapide** : Réduit drastiquement le cooldown des tirs
- **🔵 Tir Triple** : Tire 3 projectiles simultanément
- **🟢 Vitesse+** : Augmente la vitesse de déplacement
- **🔷 Bouclier** : Protection temporaire contre 1 tir
- **💖 Vie+** : Ajoute une vie supplémentaire

### États du Jeu
- **Menu Principal** : Navigation et démarrage du jeu
- **Partie en Cours** : Gameplay principal
- **Pause** : Suspension temporaire du jeu
- **Game Over** : Écran de fin avec score final

## 🏗️ Architecture et Design Patterns

### 1. 🔄 State Pattern
**Objectif** : Gérer les différents états du jeu et leurs transitions.

**Implémentation** :
- `GameState` : Interface abstraite pour tous les états
- `MenuState` : État du menu principal
- `PlayingState` : État de jeu en cours
- `PausedState` : État de pause
- `GameOverState` : État de fin de partie

**Justification** : Permet une gestion propre des transitions d'état et évite les conditions multiples dans la logique principale.

```java
// Exemple d'utilisation
context.changeState(new PlayingState(context));
```

### 2. 🏭 Factory Pattern
**Objectif** : Créer différents types d'entités selon le contexte du jeu.

**Implémentation** :
- `EntityFactory` : Factory abstraite principale
- `EnemyFactory` : Création d'ennemis selon le type et la vague
- `ProjectileFactory` : Création de projectiles joueur/ennemi
- `PowerUpFactory` : Création de power-ups aléatoires

**Justification** : Centralise la logique de création et permet d'ajuster facilement les types d'entités créées selon la progression du jeu.

```java
// Exemple d'utilisation
Enemy enemy = EntityFactory.EnemyFactory.createEnemyForWave(waveNumber, x, y);
PowerUp powerUp = EntityFactory.PowerUpFactory.createRandomPowerUp(x, y);
```

### 3. 🎨 Decorator Pattern
**Objectif** : Ajouter dynamiquement des capacités au joueur via les power-ups.

**Implémentation** :
- `PlayerCapabilities` : Interface des capacités du joueur
- `BasicPlayerCapabilities` : Capacités de base
- `PlayerDecorator` : Décorateur abstrait
- `RapidFireDecorator`, `TripleShotDecorator`, etc. : Décorateurs concrets

**Justification** : Permet d'empiler plusieurs power-ups et de les retirer automatiquement à expiration sans modifier la classe Player.

```java
// Exemple d'utilisation
player = new RapidFireDecorator(player, 10.0);
player = new ShieldDecorator(player, 15.0);
```

### 4. 🌳 Composite Pattern
**Objectif** : Organiser les ennemis en formations hiérarchiques complexes.

**Implémentation** :
- `EnemyComponent` : Interface commune pour ennemis et formations
- `EnemyLeaf` : Ennemi individuel
- `EnemyFormation` : Groupe d'ennemis avec comportement collectif
- `FormationFactory` : Création de formations prédéfinies

**Justification** : Permet de traiter un ennemi individuel ou une formation entière de manière uniforme et de créer des vagues complexes.

```java
// Exemple d'utilisation
EnemyFormation wave = FormationFactory.createWave(waveNumber, screenWidth);
wave.update(deltaTime); // Met à jour tous les ennemis de la formation
```

## 🛠️ Technologies Utilisées

- **Java 17+** : Langage principal
- **JavaFX 21** : Interface graphique et rendu 2D
- **Maven** : Gestion des dépendances et build
- **Log4j2** : Système de logging avancé
- **PlantUML** : Génération de diagrammes UML

## 📦 Installation et Compilation

### Prérequis
- Java 17 ou supérieur
- Maven 3.8 ou supérieur

### 1. Cloner le Projet
```bash
git clone <url-du-repository>
cd Projet-designpattern
```

### 2. Compilation avec Maven
```bash
mvn clean compile
```

### 3. Exécution du Jeu
```bash
mvn javafx:run
```

### 4. Création du JAR Exécutable
```bash
mvn clean package
java -jar target/space-invaders-patterns-1.0.0-shaded.jar
```

### 5. Tests
```bash
mvn test
```

## 🎮 Contrôles du Jeu

### Menu Principal
- **↑/↓** : Navigation dans le menu
- **ENTRÉE** : Sélectionner l'option

### Jeu
- **←/→** : Déplacement du vaisseau
- **ESPACE** : Tir
- **ÉCHAP** : Pause

### Pause
- **↑/↓** : Navigation dans le menu de pause
- **ENTRÉE** : Sélectionner l'option
- **ÉCHAP** : Reprendre la partie

## 📊 Système de Score

- **Ennemi Basique** : 10 points
- **Ennemi Rapide** : 20 points
- **Ennemi Lourd** : 30 points
- **Bonus de Vague** : 100 × numéro de vague
- **Objectif** : Survivre aux 10 vagues pour la victoire

## 📝 Logging et Traçabilité

Le jeu utilise Log4j2 pour tracer tous les événements importants :

```bash
# Fichier de logs généré
logs/space-invaders.log
```

**Exemples de logs :**
```
2024-10-24 14:30:15.123 [JavaFX Application Thread] INFO  SpaceInvadersGame - Initialisation d'une nouvelle partie
2024-10-24 14:30:15.125 [JavaFX Application Thread] INFO  EnemyFormation - Formation rectangulaire créée: Escadron Principal (3x8 ennemis)
2024-10-24 14:30:25.456 [JavaFX Application Thread] INFO  PlayingState - Pause demandée
2024-10-24 14:30:30.789 [JavaFX Application Thread] DEBUG SpaceInvadersGame - Power-up créé: RAPID_FIRE
```

## 📁 Structure du Projet

```
src/main/java/fr/university/spaceinvaders/
├── SpaceInvadersApplication.java      # Point d'entrée JavaFX
├── SpaceInvadersGame.java             # Moteur principal du jeu
├── state/                             # State Pattern
│   ├── GameState.java
│   ├── MenuState.java
│   ├── PlayingState.java
│   ├── PausedState.java
│   └── GameOverState.java
├── factory/                           # Factory Pattern
│   └── EntityFactory.java
├── decorator/                         # Decorator Pattern
│   ├── PlayerCapabilities.java
│   ├── BasicPlayerCapabilities.java
│   ├── PlayerDecorator.java
│   ├── RapidFireDecorator.java
│   ├── TripleShotDecorator.java
│   ├── SpeedBoostDecorator.java
│   └── ShieldDecorator.java
├── composite/                         # Composite Pattern
│   ├── EnemyComponent.java
│   ├── EnemyLeaf.java
│   ├── EnemyFormation.java
│   └── FormationFactory.java
└── entities/                          # Entités du jeu
    ├── GameEntity.java
    ├── Player.java
    ├── Enemy.java
    ├── BasicEnemy.java
    ├── FastEnemy.java
    ├── HeavyEnemy.java
    ├── Projectile.java
    └── PowerUp.java
```

## 📈 Diagramme UML

Le diagramme UML complet est disponible dans `docs/architecture-uml.puml` et montre :
- Les relations entre toutes les classes
- L'implémentation des 4 design patterns
- Les dépendances et l'architecture générale

Pour générer l'image :
```bash
# Avec PlantUML installé
java -jar plantuml.jar docs/architecture-uml.puml
```

## 🧪 Tests et Validation

### Tests Unitaires
- Tests des design patterns individuellement
- Tests de la logique de collision
- Tests des factories
- Tests des décorateurs

### Tests d'Intégration
- Test complet d'une partie
- Test des transitions d'état
- Test des formations d'ennemis

## 🚀 Extensions Possibles

Le projet est conçu pour être facilement extensible :

1. **Nouveaux Types d'Ennemis** : Ajouter dans `EnemyFactory`
2. **Nouveaux Power-ups** : Créer de nouveaux décorateurs
3. **Nouvelles Formations** : Ajouter dans `FormationFactory`
4. **Nouveaux États** : Implémenter `GameState`
5. **Boss Enemies** : Combinaison composite + décorateur
6. **Multijoueur** : Extension du pattern State
7. **Persistance** : Sauvegarde des scores avec pattern Strategy

## 📚 Références et Apprentissages

### Design Patterns Utilisés
- **State Pattern** : [Gang of Four] - Gestion d'état comportemental
- **Factory Pattern** : [Gang of Four] - Création d'objets flexible
- **Decorator Pattern** : [Gang of Four] - Extension dynamique d'objets
- **Composite Pattern** : [Gang of Four] - Structure hiérarchique d'objets

### Bonnes Pratiques Appliquées
- **SOLID Principles** : Respect des principes de conception
- **Clean Code** : Nommage explicite et méthodes courtes
- **Logging** : Traçabilité complète des événements
- **Documentation** : Javadoc et README détaillés

## 👥 Informations du Projet

- **Étudiant** : [Votre Nom]
- **Formation** : [Votre Formation]
- **Année** : 2024-2025
- **Encadrant** : [Nom de l'encadrant]

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

---

## 💡 Notes pour la Soutenance

### Points Clés à Présenter (5 minutes)

1. **Introduction** (30s)
   - Objectifs du projet et choix du jeu Space Invaders

2. **Architecture et Patterns** (3 minutes)
   - Démonstration des 4 patterns avec exemples de code
   - Justification des choix de conception
   - Montre le diagramme UML

3. **Démonstration** (1 minute)
   - Gameplay rapide montrant les fonctionnalités
   - Power-ups en action
   - Formations d'ennemis

4. **Conclusion** (30s)
   - Extensibilité et maintenance du code
   - Apprentissages sur les design patterns

### Commits Git Exemples

```bash
git commit -m "feat: implement State Pattern for game states management"
git commit -m "feat: add Factory Pattern for entities creation"
git commit -m "feat: implement Decorator Pattern for player power-ups"
git commit -m "feat: add Composite Pattern for enemy formations"
git commit -m "fix: resolve collision detection edge cases"
git commit -m "docs: add comprehensive UML diagram and README"
```

---

*Projet réalisé dans le cadre du cours de Design Patterns - Développement d'applications avec Java*