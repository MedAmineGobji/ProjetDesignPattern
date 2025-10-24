package fr.university.spaceinvaders.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Entité représentant un projectile
 */
public class Projectile extends GameEntity {
    private final boolean isPlayerProjectile;
    private static final double PROJECTILE_SPEED = 400.0;

    public Projectile(double x, double y, boolean isPlayerProjectile) {
        super(x, y, 4, 10);
        this.isPlayerProjectile = isPlayerProjectile;
        
        if (isPlayerProjectile) {
            this.velocityY = -PROJECTILE_SPEED; // Vers le haut
            this.color = Color.YELLOW;
        } else {
            this.velocityY = PROJECTILE_SPEED; // Vers le bas
            this.color = Color.RED;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(x, y, width, height);
        
        // Effet de traînée
        gc.setFill(Color.color(color.getRed(), color.getGreen(), color.getBlue(), 0.5));
        if (isPlayerProjectile) {
            gc.fillRect(x, y + height, width, height/2);
        } else {
            gc.fillRect(x, y - height/2, width, height/2);
        }
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        
        // Détruire le projectile s'il sort de l'écran
        if (y < -height || y > 600 + height) {
            destroy();
        }
    }

    public boolean isPlayerProjectile() {
        return isPlayerProjectile;
    }
}