package fr.university.spaceinvaders.decorator;

import fr.university.spaceinvaders.entities.Projectile;
import fr.university.spaceinvaders.factory.EntityFactory;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;

/**
 * Décorateur Tir Rapide - réduit le cooldown des tirs
 */
public class RapidFireDecorator extends PlayerDecorator {
    private static final double RAPID_FIRE_COOLDOWN = 0.1; // Très rapide
    private double lastShotTime = 0;

    public RapidFireDecorator(PlayerCapabilities player, double duration) {
        super(player, duration);
    }

    @Override
    public List<Projectile> shoot(double currentTime) {
        List<Projectile> projectiles = player.shoot(currentTime);
        
        // Tir rapide supplémentaire si possible
        if (currentTime - lastShotTime >= RAPID_FIRE_COOLDOWN && projectiles.isEmpty()) {
            lastShotTime = currentTime;
            projectiles.add(EntityFactory.ProjectileFactory.createPlayerProjectile(
                getX() + getWidth() / 2, getY()));
        }
        
        return projectiles;
    }

    @Override
    public boolean canShoot(double currentTime) {
        return currentTime - lastShotTime >= RAPID_FIRE_COOLDOWN;
    }

    @Override
    protected void renderEffect(GraphicsContext gc) {
        // Aura jaune clignotante
        if ((System.currentTimeMillis() / 100) % 2 == 0) {
            gc.setStroke(Color.YELLOW);
            gc.setLineWidth(3);
            gc.strokeOval(getX() - 5, getY() - 5, getWidth() + 10, getHeight() + 10);
        }
    }

    @Override
    public String getPowerUpName() {
        return "Tir Rapide";
    }
}