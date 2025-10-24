package fr.university.spaceinvaders.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Ennemi lourd - plus résistant
 */
public class HeavyEnemy extends Enemy {

    private int hitPoints = 2; // Nécessite 2 coups pour être détruit

    public HeavyEnemy(double x, double y) {
        super(x, y, 40, 30, EnemyType.HEAVY);
        this.velocityX = 30; // Plus lent
        this.shotCooldown = 1.5; // Tire très souvent
    }

    @Override
    public void render(GraphicsContext gc) {
        // Couleur change selon les dégâts
        Color renderColor = hitPoints > 1 ? color : Color.DARKRED;
        gc.setFill(renderColor);
        
        // Corps massif
        gc.fillRect(x, y, width, height);
        
        // Blindage
        gc.setFill(Color.GRAY);
        gc.fillRect(x + 5, y + 5, width - 10, height - 10);
        
        // Canon
        gc.setFill(Color.BLACK);
        gc.fillRect(x + width/2 - 2, y + height, 4, 8);
    }

    @Override
    public void destroy() {
        hitPoints--;
        if (hitPoints <= 0) {
            super.destroy();
        }
    }

    public int getHitPoints() {
        return hitPoints;
    }
}