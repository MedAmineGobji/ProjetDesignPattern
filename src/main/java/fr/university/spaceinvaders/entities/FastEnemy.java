package fr.university.spaceinvaders.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Ennemi rapide - plus difficile à toucher
 */
public class FastEnemy extends Enemy {

    public FastEnemy(double x, double y) {
        super(x, y, 25, 15, EnemyType.FAST);
        this.velocityX = 80; // Plus rapide
        this.shotCooldown = 2.0; // Tire plus souvent
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(color);
        // Forme triangulaire pour suggérer la vitesse
        double[] xPoints = {x, x + width/2, x + width};
        double[] yPoints = {y + height, y, y + height};
        gc.fillPolygon(xPoints, yPoints, 3);
        
        // Effet de vitesse
        gc.setFill(Color.YELLOW);
        gc.fillOval(x + width/3, y + height/3, width/3, height/3);
    }
}