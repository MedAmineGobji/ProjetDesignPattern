package fr.university.spaceinvaders.decorator;

import fr.university.spaceinvaders.entities.Projectile;
import fr.university.spaceinvaders.factory.EntityFactory;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;

/**
 * Décorateur Tir Triple - tire 3 projectiles à la fois
 */
public class TripleShotDecorator extends PlayerDecorator {

    public TripleShotDecorator(PlayerCapabilities player, double duration) {
        super(player, duration);
    }

    @Override
    public List<Projectile> shoot(double currentTime) {
        List<Projectile> projectiles = player.shoot(currentTime);
        
        if (!projectiles.isEmpty()) {
            // Ajouter deux projectiles supplémentaires
            double centerX = getX() + getWidth() / 2;
            projectiles.add(EntityFactory.ProjectileFactory.createPlayerProjectile(
                centerX - 15, getY()));
            projectiles.add(EntityFactory.ProjectileFactory.createPlayerProjectile(
                centerX + 15, getY()));
        }
        
        return projectiles;
    }

    @Override
    protected void renderEffect(GraphicsContext gc) {
        // Aura bleue
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);
        gc.strokeRect(getX() - 3, getY() - 3, getWidth() + 6, getHeight() + 6);
    }

    @Override
    public String getPowerUpName() {
        return "Tir Triple";
    }
}