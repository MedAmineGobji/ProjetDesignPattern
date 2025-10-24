package fr.university.spaceinvaders.composite;

import javafx.scene.canvas.GraphicsContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * Composite du Composite Pattern - représente un groupe d'ennemis
 */
public class EnemyFormation implements EnemyComponent {
    private static final Logger logger = LogManager.getLogger(EnemyFormation.class);
    
    private List<EnemyComponent> components;
    private String formationName;
    private double formationSpeedX = 50.0;
    private double formationSpeedY = 0.0;
    private boolean movingRight = true;
    private double screenWidth = 800.0;

    public EnemyFormation(String formationName) {
        this.formationName = formationName;
        this.components = new ArrayList<>();
    }

    /**
     * Ajouter un composant à la formation
     */
    public void addComponent(EnemyComponent component) {
        components.add(component);
        logger.debug("Ajout d'un composant à la formation {}: {} composants total", 
                    formationName, components.size());
    }

    /**
     * Retirer un composant de la formation
     */
    public void removeComponent(EnemyComponent component) {
        components.remove(component);
        logger.debug("Suppression d'un composant de la formation {}: {} composants restants", 
                    formationName, components.size());
    }

    /**
     * Obtenir tous les composants
     */
    public List<EnemyComponent> getComponents() {
        return new ArrayList<>(components);
    }

    @Override
    public void update(double deltaTime) {
        // Nettoyer les composants détruits
        Iterator<EnemyComponent> iterator = components.iterator();
        while (iterator.hasNext()) {
            EnemyComponent component = iterator.next();
            if (component.isDestroyed()) {
                iterator.remove();
            } else {
                component.update(deltaTime);
            }
        }

        // Logique de mouvement de formation
        updateFormationMovement(deltaTime);
    }

    private void updateFormationMovement(double deltaTime) {
        if (components.isEmpty()) return;

        boolean shouldChangeDirection = false;
        boolean shouldMoveDown = false;

        // Vérifier les limites de l'écran
        if (movingRight && getMaxX() >= screenWidth - 20) {
            shouldChangeDirection = true;
            shouldMoveDown = true;
        } else if (!movingRight && getMinX() <= 20) {
            shouldChangeDirection = true;
            shouldMoveDown = true;
        }

        if (shouldChangeDirection) {
            movingRight = !movingRight;
            formationSpeedX = -formationSpeedX;
            logger.debug("Formation {} change de direction", formationName);
        }

        if (shouldMoveDown) {
            // Mouvement vers le bas pour toute la formation
            move(0, 30);
            logger.debug("Formation {} descend", formationName);
        } else {
            // Mouvement latéral normal
            move(formationSpeedX * deltaTime, 0);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        for (EnemyComponent component : components) {
            component.render(gc);
        }
    }

    @Override
    public void move(double deltaX, double deltaY) {
        for (EnemyComponent component : components) {
            component.move(deltaX, deltaY);
        }
    }

    @Override
    public boolean isDestroyed() {
        return components.isEmpty() || components.stream().allMatch(EnemyComponent::isDestroyed);
    }

    @Override
    public void destroy() {
        for (EnemyComponent component : components) {
            component.destroy();
        }
        components.clear();
        logger.info("Formation {} détruite", formationName);
    }

    @Override
    public int getAliveCount() {
        return components.stream().mapToInt(EnemyComponent::getAliveCount).sum();
    }

    @Override
    public double getMinX() {
        return components.stream()
                .mapToDouble(EnemyComponent::getMinX)
                .filter(x -> x != Double.MAX_VALUE)
                .min()
                .orElse(Double.MAX_VALUE);
    }

    @Override
    public double getMaxX() {
        return components.stream()
                .mapToDouble(EnemyComponent::getMaxX)
                .filter(x -> x != Double.MIN_VALUE)
                .max()
                .orElse(Double.MIN_VALUE);
    }

    @Override
    public double getMinY() {
        return components.stream()
                .mapToDouble(EnemyComponent::getMinY)
                .filter(y -> y != Double.MAX_VALUE)
                .min()
                .orElse(Double.MAX_VALUE);
    }

    @Override
    public double getMaxY() {
        return components.stream()
                .mapToDouble(EnemyComponent::getMaxY)
                .filter(y -> y != Double.MIN_VALUE)
                .max()
                .orElse(Double.MIN_VALUE);
    }

    @Override
    public boolean checkCollision(double x, double y, double width, double height) {
        return components.stream().anyMatch(component -> 
            component.checkCollision(x, y, width, height));
    }

    /**
     * Définir la largeur de l'écran pour les calculs de mouvement
     */
    public void setScreenWidth(double screenWidth) {
        this.screenWidth = screenWidth;
    }

    /**
     * Nom de la formation
     */
    public String getFormationName() {
        return formationName;
    }

    /**
     * Vitesse de la formation
     */
    public void setFormationSpeed(double speedX) {
        this.formationSpeedX = Math.abs(speedX) * (movingRight ? 1 : -1);
    }
}