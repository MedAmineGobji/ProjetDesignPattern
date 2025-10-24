package fr.university.spaceinvaders.composite;

import fr.university.spaceinvaders.entities.Enemy;
import javafx.scene.canvas.GraphicsContext;

/**
 * Feuille du Composite Pattern - représente un ennemi individuel
 */
public class EnemyLeaf implements EnemyComponent {
    private Enemy enemy;

    public EnemyLeaf(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void update(double deltaTime) {
        if (enemy.isAlive()) {
            enemy.update(deltaTime);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (enemy.isAlive()) {
            enemy.render(gc);
        }
    }

    @Override
    public void move(double deltaX, double deltaY) {
        if (enemy.isAlive()) {
            enemy.setX(enemy.getX() + deltaX);
            enemy.setY(enemy.getY() + deltaY);
        }
    }

    @Override
    public boolean isDestroyed() {
        return !enemy.isAlive();
    }

    @Override
    public void destroy() {
        enemy.destroy();
    }

    @Override
    public int getAliveCount() {
        return enemy.isAlive() ? 1 : 0;
    }

    @Override
    public double getMinX() {
        return enemy.isAlive() ? enemy.getX() : Double.MAX_VALUE;
    }

    @Override
    public double getMaxX() {
        return enemy.isAlive() ? enemy.getX() + enemy.getWidth() : Double.MIN_VALUE;
    }

    @Override
    public double getMinY() {
        return enemy.isAlive() ? enemy.getY() : Double.MAX_VALUE;
    }

    @Override
    public double getMaxY() {
        return enemy.isAlive() ? enemy.getY() + enemy.getHeight() : Double.MIN_VALUE;
    }

    @Override
    public boolean checkCollision(double x, double y, double width, double height) {
        if (!enemy.isAlive()) return false;
        
        return enemy.getX() < x + width &&
               enemy.getX() + enemy.getWidth() > x &&
               enemy.getY() < y + height &&
               enemy.getY() + enemy.getHeight() > y;
    }

    /**
     * Accès direct à l'ennemi pour des opérations spécifiques
     */
    public Enemy getEnemy() {
        return enemy;
    }
}