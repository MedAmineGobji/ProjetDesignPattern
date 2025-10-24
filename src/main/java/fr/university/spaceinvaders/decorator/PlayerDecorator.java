package fr.university.spaceinvaders.decorator;

import fr.university.spaceinvaders.entities.Projectile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;

/**
 * Décorateur de base pour les améliorations du joueur - Decorator Pattern
 */
public abstract class PlayerDecorator implements PlayerCapabilities {
    protected PlayerCapabilities player;
    protected double duration;
    protected double timeRemaining;

    public PlayerDecorator(PlayerCapabilities player, double duration) {
        this.player = player;
        this.duration = duration;
        this.timeRemaining = duration;
    }

    @Override
    public void move(int direction, double deltaTime, double screenWidth) {
        player.move(direction, deltaTime, screenWidth);
    }

    @Override
    public void stop() {
        player.stop();
    }

    @Override
    public List<Projectile> shoot(double currentTime) {
        return player.shoot(currentTime);
    }

    @Override
    public boolean canShoot(double currentTime) {
        return player.canShoot(currentTime);
    }

    @Override
    public void render(GraphicsContext gc) {
        player.render(gc);
        renderEffect(gc);
    }

    @Override
    public void update(double deltaTime) {
        player.update(deltaTime);
        timeRemaining -= deltaTime;
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
     * Rendu des effets visuels du power-up
     */
    protected abstract void renderEffect(GraphicsContext gc);

    /**
     * Vérifie si le power-up est toujours actif
     */
    public boolean isActive() {
        return timeRemaining > 0;
    }

    /**
     * Temps restant du power-up
     */
    public double getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * Pourcentage de temps restant
     */
    public double getPercentageRemaining() {
        return timeRemaining / duration;
    }

    /**
     * Nom du power-up pour l'affichage
     */
    public abstract String getPowerUpName();

    /**
     * Accès au joueur décoré (pour unwrapping)
     */
    public PlayerCapabilities getDecoratedPlayer() {
        return player;
    }
}