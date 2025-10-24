package fr.university.spaceinvaders;

import fr.university.spaceinvaders.state.*;
import fr.university.spaceinvaders.entities.*;
import fr.university.spaceinvaders.composite.*;
import fr.university.spaceinvaders.decorator.*;
import fr.university.spaceinvaders.decorator.TripleShotDecorator;
import fr.university.spaceinvaders.factory.EntityFactory;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe principale du jeu - Intègre tous les design patterns
 */
public class SpaceInvadersGame {
    private static final Logger logger = LogManager.getLogger(SpaceInvadersGame.class);
    
    // Constantes du jeu
    private static final double SCREEN_WIDTH = 800;
    private static final double SCREEN_HEIGHT = 600;
    
    // État actuel du jeu (State Pattern)
    private GameState currentState;
    
    // Entités du jeu
    private PlayerCapabilities player; // Decorator Pattern
    private List<Projectile> projectiles;
    private List<PowerUp> powerUps;
    private EnemyFormation currentWave; // Composite Pattern
    
    // Contexte de rendu
    private GraphicsContext graphicsContext;
    
    // Statistiques du jeu
    private int score = 0;
    private int waveNumber = 1;
    private boolean gameRunning = true;
    private boolean gameOver = false;
    private boolean victory = false;
    
    // Temps
    private double lastTime = 0;

    public SpaceInvadersGame(GraphicsContext gc) {
        this.graphicsContext = gc;
        this.projectiles = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        
        logger.info("SpaceInvadersGame initialisé");
        
        // Commencer par l'état menu
        changeState(new MenuState(this));
    }

    /**
     * Changer l'état du jeu (State Pattern)
     */
    public void changeState(GameState newState) {
        if (currentState != null) {
            currentState.exit();
            logger.info("Sortie de l'état: {}", currentState.getStateName());
        }
        
        currentState = newState;
        currentState.enter();
        logger.info("Entrée dans l'état: {}", currentState.getStateName());
    }

    /**
     * Initialisation d'une nouvelle partie
     */
    public void initializeGame() {
        logger.info("Initialisation d'une nouvelle partie");
        
        // Créer le joueur avec capacités de base (Decorator Pattern)
        Player playerEntity = new Player(SCREEN_WIDTH / 2 - 20, SCREEN_HEIGHT - 50);
        player = new BasicPlayerCapabilities(playerEntity);
        
        // Nettoyer les listes
        projectiles.clear();
        powerUps.clear();
        
        // Réinitialiser les statistiques
        score = 0;
        waveNumber = 1;
        gameOver = false;
        victory = false;
        
        // Créer la première vague (Composite Pattern)
        createNewWave();
    }

    /**
     * Créer une nouvelle vague d'ennemis
     */
    private void createNewWave() {
        currentWave = FormationFactory.createWave(waveNumber, SCREEN_WIDTH);
        logger.info("Nouvelle vague créée: {} avec {} ennemis", waveNumber, currentWave.getAliveCount());
    }

    /**
     * Mise à jour du jeu
     */
    public void updateGame(double deltaTime) {
        if (gameOver) return;
        
        // Mise à jour du joueur
        player.update(deltaTime);
        
        // Mise à jour de la vague d'ennemis
        if (currentWave != null) {
            currentWave.update(deltaTime);
            
            // Vérifier si la vague est détruite
            if (currentWave.isDestroyed() || currentWave.getAliveCount() == 0) {
                logger.info("Vague {} terminée!", waveNumber);
                score += 100 * waveNumber; // Bonus pour compléter une vague
                waveNumber++;
                
                if (waveNumber > 10) {
                    // Victoire !
                    victory = true;
                    gameOver = true;
                    logger.info("Jeu terminé - VICTOIRE! Score final: {}", score);
                } else {
                    createNewWave();
                }
            }
            
            // Vérifier si les ennemis atteignent le joueur
            if (currentWave.getMaxY() > SCREEN_HEIGHT - 100) {
                gameOver = true;
                logger.info("Jeu terminé - Les ennemis ont atteint la base!");
            }
        }
        
        // Mise à jour des projectiles
        updateProjectiles(deltaTime);
        
        // Mise à jour des power-ups
        updatePowerUps(deltaTime);
        
        // Gestion des collisions
        handleCollisions();
        
        // Tir automatique des ennemis
        handleEnemyFiring();
        
        // Nettoyage des décorateurs expirés
        cleanupExpiredDecorators();
    }

    /**
     * Mise à jour des projectiles
     */
    private void updateProjectiles(double deltaTime) {
        Iterator<Projectile> iter = projectiles.iterator();
        while (iter.hasNext()) {
            Projectile projectile = iter.next();
            projectile.update(deltaTime);
            
            if (!projectile.isAlive()) {
                iter.remove();
            }
        }
    }

    /**
     * Mise à jour des power-ups
     */
    private void updatePowerUps(double deltaTime) {
        Iterator<PowerUp> iter = powerUps.iterator();
        while (iter.hasNext()) {
            PowerUp powerUp = iter.next();
            powerUp.update(deltaTime);
            
            if (!powerUp.isAlive()) {
                iter.remove();
            }
        }
    }

    /**
     * Gestion des collisions
     */
    private void handleCollisions() {
        // Collisions projectiles joueur vs ennemis
        Iterator<Projectile> projIter = projectiles.iterator();
        while (projIter.hasNext()) {
            Projectile projectile = projIter.next();
            
            if (projectile.isPlayerProjectile() && currentWave != null) {
                if (currentWave.checkCollision(projectile.getX(), projectile.getY(), 
                                             projectile.getWidth(), projectile.getHeight())) {
                    // Trouver l'ennemi touché
                    destroyEnemyAt(projectile.getX(), projectile.getY());
                    projIter.remove();
                    score += 10;
                    logger.debug("Ennemi touché! Score: {}", score);
                    
                    // Chance de faire apparaître un power-up
                    if (Math.random() < 0.15) { // 15% de chance
                        PowerUp powerUp = EntityFactory.PowerUpFactory.createRandomPowerUp(
                            projectile.getX(), projectile.getY());
                        if (powerUp != null) {
                            powerUps.add(powerUp);
                            logger.debug("Power-up créé: {}", powerUp.getType());
                        }
                    }
                }
            } else if (!projectile.isPlayerProjectile()) {
                // Projectile ennemi vs joueur
                if (projectile.getX() < player.getX() + player.getWidth() &&
                    projectile.getX() + projectile.getWidth() > player.getX() &&
                    projectile.getY() < player.getY() + player.getHeight() &&
                    projectile.getY() + projectile.getHeight() > player.getY()) {
                    
                    player.takeDamage();
                    projIter.remove();
                    logger.info("Joueur touché! Vies restantes: {}", player.getLives());
                    
                    if (!player.isAlive()) {
                        gameOver = true;
                        logger.info("Jeu terminé - Plus de vies!");
                    }
                }
            }
        }
        
        // Collisions joueur vs power-ups
        Iterator<PowerUp> powerUpIter = powerUps.iterator();
        while (powerUpIter.hasNext()) {
            PowerUp powerUp = powerUpIter.next();
            
            if (powerUp.getX() < player.getX() + player.getWidth() &&
                powerUp.getX() + powerUp.getWidth() > player.getX() &&
                powerUp.getY() < player.getY() + player.getHeight() &&
                powerUp.getY() + powerUp.getHeight() > player.getY()) {
                
                applyPowerUp(powerUp.getType());
                powerUpIter.remove();
                logger.info("Power-up ramassé: {}", powerUp.getType());
            }
        }
    }

    /**
     * Détruire l'ennemi à une position donnée
     */
    private void destroyEnemyAt(double x, double y) {
        if (currentWave == null) return;
        
        // Parcourir récursivement la formation pour trouver l'ennemi
        destroyEnemyInComponent(currentWave, x, y);
    }

    private boolean destroyEnemyInComponent(EnemyComponent component, double x, double y) {
        if (component instanceof EnemyLeaf) {
            EnemyLeaf leaf = (EnemyLeaf) component;
            if (leaf.checkCollision(x, y, 4, 4)) { // Taille approximative d'un projectile
                leaf.destroy();
                return true;
            }
        } else if (component instanceof EnemyFormation) {
            EnemyFormation formation = (EnemyFormation) component;
            for (EnemyComponent child : formation.getComponents()) {
                if (destroyEnemyInComponent(child, x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Appliquer un power-up au joueur (Decorator Pattern)
     */
    private void applyPowerUp(PowerUp.PowerUpType type) {
        switch (type) {
            case RAPID_FIRE:
                player = new RapidFireDecorator(player, 10.0);
                break;
            case TRIPLE_SHOT:
                player = new TripleShotDecorator(player, 8.0);
                break;
            case SPEED_BOOST:
                player = new SpeedBoostDecorator(player, 12.0);
                break;
            case SHIELD:
                player = new ShieldDecorator(player, 15.0);
                break;
            case EXTRA_LIFE:
                // Ajouter une vie directement
                if (player instanceof BasicPlayerCapabilities) {
                    ((BasicPlayerCapabilities) player).getPlayer().addLife();
                } else {
                    // Si le joueur est décoré, trouver le BasicPlayerCapabilities de base
                    PlayerCapabilities base = player;
                    while (base instanceof PlayerDecorator) {
                        base = ((PlayerDecorator) base).getDecoratedPlayer();
                    }
                    if (base instanceof BasicPlayerCapabilities) {
                        ((BasicPlayerCapabilities) base).getPlayer().addLife();
                    }
                }
                break;
        }
    }

    /**
     * Nettoyer les décorateurs expirés
     */
    private void cleanupExpiredDecorators() {
        while (player instanceof PlayerDecorator) {
            PlayerDecorator decorator = (PlayerDecorator) player;
            if (!decorator.isActive()) {
                player = decorator.getDecoratedPlayer(); // Unwrap
                logger.debug("Décorateur expiré supprimé");
            } else {
                break;
            }
        }
    }

    /**
     * Tir automatique des ennemis
     */
    private void handleEnemyFiring() {
        if (currentWave == null) return;
        
        double currentTime = System.currentTimeMillis() / 1000.0;
        
        // Faire tirer quelques ennemis aléatoirement
        if (Math.random() < 0.02) { // 2% de chance par frame
            List<EnemyLeaf> enemies = getAllEnemyLeaves(currentWave);
            if (!enemies.isEmpty()) {
                EnemyLeaf randomEnemy = enemies.get((int)(Math.random() * enemies.size()));
                Enemy enemy = randomEnemy.getEnemy();
                
                if (enemy.canShoot(currentTime)) {
                    enemy.shoot(currentTime);
                    projectiles.add(EntityFactory.ProjectileFactory.createEnemyProjectile(
                        enemy.getX() + enemy.getWidth() / 2, enemy.getY() + enemy.getHeight()));
                }
            }
        }
    }

    /**
     * Obtenir tous les ennemis vivants de la formation
     */
    private List<EnemyLeaf> getAllEnemyLeaves(EnemyComponent component) {
        List<EnemyLeaf> leaves = new ArrayList<>();
        
        if (component instanceof EnemyLeaf) {
            EnemyLeaf leaf = (EnemyLeaf) component;
            if (!leaf.isDestroyed()) {
                leaves.add(leaf);
            }
        } else if (component instanceof EnemyFormation) {
            EnemyFormation formation = (EnemyFormation) component;
            for (EnemyComponent child : formation.getComponents()) {
                leaves.addAll(getAllEnemyLeaves(child));
            }
        }
        
        return leaves;
    }

    /**
     * Rendu du jeu
     */
    public void renderGame() {
        // Rendu du joueur
        player.render(graphicsContext);
        
        // Rendu de la vague d'ennemis
        if (currentWave != null) {
            currentWave.render(graphicsContext);
        }
        
        // Rendu des projectiles
        for (Projectile projectile : projectiles) {
            projectile.render(graphicsContext);
        }
        
        // Rendu des power-ups
        for (PowerUp powerUp : powerUps) {
            powerUp.render(graphicsContext);
        }
    }

    /**
     * Mouvement du joueur
     */
    public void movePlayer(int direction) {
        if (player != null) {
            player.move(direction, 0.016, SCREEN_WIDTH); // ~60 FPS
        }
    }

    /**
     * Arrêt du joueur
     */
    public void stopPlayer() {
        if (player != null) {
            player.stop();
        }
    }

    /**
     * Tir du joueur
     */
    public void playerShoot() {
        if (player != null) {
            double currentTime = System.currentTimeMillis() / 1000.0;
            List<Projectile> newProjectiles = player.shoot(currentTime);
            projectiles.addAll(newProjectiles);
        }
    }

    /**
     * Mise à jour principale du jeu
     */
    public void update(double currentTime) {
        if (lastTime == 0) lastTime = currentTime;
        double deltaTime = (currentTime - lastTime) / 1_000_000_000.0; // Conversion ns en s
        lastTime = currentTime;
        
        currentState.update(deltaTime);
    }

    /**
     * Rendu principal
     */
    public void render() {
        currentState.render();
    }

    /**
     * Gestion des entrées clavier
     */
    public void handleKeyPressed(KeyEvent event) {
        currentState.handleKeyPressed(event);
    }

    public void handleKeyReleased(KeyEvent event) {
        currentState.handleKeyReleased(event);
    }

    /**
     * Fermeture du jeu
     */
    public void shutdown() {
        gameRunning = false;
        logger.info("Fermeture du jeu demandée");
    }

    // Getters pour les états
    public GraphicsContext getGraphicsContext() { return graphicsContext; }
    public double getWidth() { return SCREEN_WIDTH; }
    public double getHeight() { return SCREEN_HEIGHT; }
    public int getScore() { return score; }
    public int getLives() { return player != null ? player.getLives() : 0; }
    public int getWaveNumber() { return waveNumber; }
    public boolean isGameOver() { return gameOver; }
    public boolean isVictory() { return victory; }
    public boolean isRunning() { return gameRunning; }
}