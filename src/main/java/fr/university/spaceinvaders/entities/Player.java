package fr.university.spaceinvaders.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Entité représentant le joueur
 */
public class Player extends GameEntity {
    private static final double PLAYER_SPEED = 300.0;
    private static final double PLAYER_WIDTH = 40.0;
    private static final double PLAYER_HEIGHT = 30.0;
    
    private int lives;
    private double lastShotTime = 0;
    private static final double SHOT_COOLDOWN = 0.3; // secondes

    public Player(double x, double y) {
        super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
        this.color = Color.CYAN;
        this.lives = 3;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(color);
        // Dessiner un triangle pour représenter le vaisseau
        double[] xPoints = {x, x + width/2, x + width};
        double[] yPoints = {y + height, y, y + height};
        gc.fillPolygon(xPoints, yPoints, 3);
        
        // Dessiner le cockpit
        gc.setFill(Color.LIGHTBLUE);
        gc.fillOval(x + width/3, y + height/3, width/3, height/3);
    }

    public void moveLeft() {
        velocityX = -PLAYER_SPEED;
    }

    public void moveRight() {
        velocityX = PLAYER_SPEED;
    }

    public void stop() {
        velocityX = 0;
    }

    public boolean canShoot(double currentTime) {
        return currentTime - lastShotTime >= SHOT_COOLDOWN;
    }

    public void shoot(double currentTime) {
        lastShotTime = currentTime;
    }

    public void takeDamage() {
        lives--;
        if (lives <= 0) {
            destroy();
        }
    }

    public void addLife() {
        lives++;
    }

    public int getLives() {
        return lives;
    }

    // Méthodes pour les contraintes de mouvement
    public void constrainToScreen(double screenWidth) {
        if (x < 0) {
            x = 0;
            velocityX = 0;
        } else if (x + width > screenWidth) {
            x = screenWidth - width;
            velocityX = 0;
        }
    }
}