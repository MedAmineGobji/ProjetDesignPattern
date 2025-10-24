package fr.university.spaceinvaders.decorator;

import fr.university.spaceinvaders.entities.Player;
import fr.university.spaceinvaders.entities.Projectile;
import fr.university.spaceinvaders.factory.EntityFactory;
import javafx.scene.canvas.GraphicsContext;
import java.util.List;
import java.util.ArrayList;

/**
 * Implémentation de base des capacités du joueur - Decorator Pattern
 */
public class BasicPlayerCapabilities implements PlayerCapabilities {
    protected Player player;

    public BasicPlayerCapabilities(Player player) {
        this.player = player;
    }

    @Override
    public void move(int direction, double deltaTime, double screenWidth) {
        if (direction < 0) {
            player.moveLeft();
        } else if (direction > 0) {
            player.moveRight();
        }
        player.update(deltaTime);
        player.constrainToScreen(screenWidth);
    }

    @Override
    public void stop() {
        player.stop();
    }

    @Override
    public List<Projectile> shoot(double currentTime) {
        List<Projectile> projectiles = new ArrayList<>();
        if (canShoot(currentTime)) {
            player.shoot(currentTime);
            projectiles.add(EntityFactory.ProjectileFactory.createPlayerProjectile(
                player.getX() + player.getWidth() / 2, player.getY()));
        }
        return projectiles;
    }

    @Override
    public boolean canShoot(double currentTime) {
        return player.canShoot(currentTime);
    }

    @Override
    public void render(GraphicsContext gc) {
        player.render(gc);
    }

    @Override
    public void update(double deltaTime) {
        player.update(deltaTime);
    }

    @Override
    public double getX() {
        return player.getX();
    }

    @Override
    public double getY() {
        return player.getY();
    }

    @Override
    public double getWidth() {
        return player.getWidth();
    }

    @Override
    public double getHeight() {
        return player.getHeight();
    }

    @Override
    public int getLives() {
        return player.getLives();
    }

    @Override
    public void takeDamage() {
        player.takeDamage();
    }

    @Override
    public boolean isAlive() {
        return player.isAlive();
    }

    /**
     * Accès direct au joueur pour les décorateurs
     */
    public Player getPlayer() {
        return player;
    }
}