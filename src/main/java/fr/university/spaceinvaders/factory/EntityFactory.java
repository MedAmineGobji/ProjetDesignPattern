package fr.university.spaceinvaders.factory;

import fr.university.spaceinvaders.entities.*;

/**
 * Factory abstraite pour créer les entités du jeu - Factory Pattern
 */
public abstract class EntityFactory {
    
    /**
     * Factory pour créer des ennemis
     */
    public static class EnemyFactory {
        
        public static Enemy createEnemy(Enemy.EnemyType type, double x, double y) {
            switch (type) {
                case BASIC:
                    return new BasicEnemy(x, y);
                case FAST:
                    return new FastEnemy(x, y);
                case HEAVY:
                    return new HeavyEnemy(x, y);
                default:
                    throw new IllegalArgumentException("Type d'ennemi inconnu: " + type);
            }
        }
        
        /**
         * Crée un ennemi aléatoire
         */
        public static Enemy createRandomEnemy(double x, double y) {
            Enemy.EnemyType[] types = Enemy.EnemyType.values();
            Enemy.EnemyType randomType = types[(int)(Math.random() * types.length)];
            return createEnemy(randomType, x, y);
        }
        
        /**
         * Crée un ennemi selon le niveau de difficulté
         */
        public static Enemy createEnemyForWave(int waveNumber, double x, double y) {
            if (waveNumber <= 2) {
                return createEnemy(Enemy.EnemyType.BASIC, x, y);
            } else if (waveNumber <= 5) {
                // Mélange de basic et fast
                return Math.random() < 0.7 ? 
                    createEnemy(Enemy.EnemyType.BASIC, x, y) :
                    createEnemy(Enemy.EnemyType.FAST, x, y);
            } else {
                // Tous types d'ennemis
                return createRandomEnemy(x, y);
            }
        }
    }
    
    /**
     * Factory pour créer des projectiles
     */
    public static class ProjectileFactory {
        
        public static Projectile createPlayerProjectile(double x, double y) {
            return new Projectile(x, y, true);
        }
        
        public static Projectile createEnemyProjectile(double x, double y) {
            return new Projectile(x, y, false);
        }
    }
    
    /**
     * Factory pour créer des power-ups
     */
    public static class PowerUpFactory {
        
        public static PowerUp createPowerUp(PowerUp.PowerUpType type, double x, double y) {
            switch (type) {
                case RAPID_FIRE:
                    return new RapidFirePowerUp(x, y);
                case TRIPLE_SHOT:
                    return new TripleShotPowerUp(x, y);
                case SPEED_BOOST:
                    return new SpeedBoostPowerUp(x, y);
                case SHIELD:
                    return new ShieldPowerUp(x, y);
                case EXTRA_LIFE:
                    return new ExtraLifePowerUp(x, y);
                default:
                    throw new IllegalArgumentException("Type de power-up inconnu: " + type);
            }
        }
        
        /**
         * Crée un power-up aléatoire
         */
        public static PowerUp createRandomPowerUp(double x, double y) {
            PowerUp.PowerUpType[] types = PowerUp.PowerUpType.values();
            PowerUp.PowerUpType randomType = types[(int)(Math.random() * types.length)];
            return createPowerUp(randomType, x, y);
        }
        
        /**
         * Crée un power-up avec une probabilité donnée
         */
        public static PowerUp createPowerUpWithProbability(double x, double y, double probability) {
            if (Math.random() < probability) {
                return createRandomPowerUp(x, y);
            }
            return null;
        }
    }
}

/**
 * Implémentations concrètes des power-ups
 */
class RapidFirePowerUp extends PowerUp {
    public RapidFirePowerUp(double x, double y) {
        super(x, y, PowerUpType.RAPID_FIRE);
    }
    
    @Override
    protected void renderIcon(javafx.scene.canvas.GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillText("R", x + 6, y + 14);
    }
}

class TripleShotPowerUp extends PowerUp {
    public TripleShotPowerUp(double x, double y) {
        super(x, y, PowerUpType.TRIPLE_SHOT);
    }
    
    @Override
    protected void renderIcon(javafx.scene.canvas.GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillText("3", x + 6, y + 14);
    }
}

class SpeedBoostPowerUp extends PowerUp {
    public SpeedBoostPowerUp(double x, double y) {
        super(x, y, PowerUpType.SPEED_BOOST);
    }
    
    @Override
    protected void renderIcon(javafx.scene.canvas.GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillText("S", x + 6, y + 14);
    }
}

class ShieldPowerUp extends PowerUp {
    public ShieldPowerUp(double x, double y) {
        super(x, y, PowerUpType.SHIELD);
    }
    
    @Override
    protected void renderIcon(javafx.scene.canvas.GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillText("◊", x + 6, y + 14);
    }
}

class ExtraLifePowerUp extends PowerUp {
    public ExtraLifePowerUp(double x, double y) {
        super(x, y, PowerUpType.EXTRA_LIFE);
    }
    
    @Override
    protected void renderIcon(javafx.scene.canvas.GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillText("♥", x + 6, y + 14);
    }
}