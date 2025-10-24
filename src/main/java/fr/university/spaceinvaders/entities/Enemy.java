package fr.university.spaceinvaders.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe de base pour les ennemis
 */
public abstract class Enemy extends GameEntity {
    protected int points;
    protected double lastShotTime = 0;
    protected double shotCooldown = 2.0; // secondes
    protected EnemyType type;

    public enum EnemyType {
        BASIC(10, Color.GREEN),
        FAST(20, Color.ORANGE), 
        HEAVY(30, Color.PURPLE);

        private final int points;
        private final Color color;

        EnemyType(int points, Color color) {
            this.points = points;
            this.color = color;
        }

        public int getPoints() { return points; }
        public Color getColor() { return color; }
    }

    public Enemy(double x, double y, double width, double height, EnemyType type) {
        super(x, y, width, height);
        this.type = type;
        this.points = type.getPoints();
        this.color = type.getColor();
        this.velocityX = 50; // Mouvement latéral de base
    }

    public boolean canShoot(double currentTime) {
        return currentTime - lastShotTime >= shotCooldown;
    }

    public void shoot(double currentTime) {
        lastShotTime = currentTime;
    }

    public int getPoints() {
        return points;
    }

    public EnemyType getType() {
        return type;
    }

    /**
     * Logique de déplacement en formation
     */
    public void moveInFormation(boolean moveDown, boolean changeDirection) {
        if (moveDown) {
            y += 20;
            velocityX = 0;
        } else if (changeDirection) {
            velocityX = -velocityX;
        }
    }
}