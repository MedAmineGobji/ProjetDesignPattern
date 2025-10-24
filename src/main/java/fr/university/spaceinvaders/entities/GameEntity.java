package fr.university.spaceinvaders.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe de base pour toutes les entités du jeu
 */
public abstract class GameEntity {
    protected double x, y;
    protected double width, height;
    protected double velocityX, velocityY;
    protected boolean alive = true;
    protected Color color;

    public GameEntity(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocityX = 0;
        this.velocityY = 0;
        this.color = Color.WHITE;
    }

    /**
     * Met à jour l'entité
     */
    public void update(double deltaTime) {
        x += velocityX * deltaTime;
        y += velocityY * deltaTime;
    }

    /**
     * Rendu de l'entité
     */
    public abstract void render(GraphicsContext gc);

    /**
     * Vérification de collision avec une autre entité
     */
    public boolean collidesWith(GameEntity other) {
        return x < other.x + other.width &&
               x + width > other.x &&
               y < other.y + other.height &&
               y + height > other.y;
    }

    /**
     * Destruction de l'entité
     */
    public void destroy() {
        alive = false;
    }

    // Getters et Setters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getVelocityX() { return velocityX; }
    public double getVelocityY() { return velocityY; }
    public boolean isAlive() { return alive; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setVelocityX(double velocityX) { this.velocityX = velocityX; }
    public void setVelocityY(double velocityY) { this.velocityY = velocityY; }
    public void setColor(Color color) { this.color = color; }
}