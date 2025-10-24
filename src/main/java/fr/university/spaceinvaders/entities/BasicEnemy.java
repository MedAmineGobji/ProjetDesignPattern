package fr.university.spaceinvaders.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Ennemi de base - implémentation concrète
 */
public class BasicEnemy extends Enemy {

    public BasicEnemy(double x, double y) {
        super(x, y, 30, 20, EnemyType.BASIC);
        this.shotCooldown = 3.0;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(color);
        // Corps principal
        gc.fillRect(x, y, width, height);
        
        // Antennes
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(x + 5, y - 5, 3, 5);
        gc.fillRect(x + width - 8, y - 5, 3, 5);
        
        // Yeux
        gc.setFill(Color.RED);
        gc.fillOval(x + 7, y + 5, 4, 4);
        gc.fillOval(x + width - 11, y + 5, 4, 4);
    }
}