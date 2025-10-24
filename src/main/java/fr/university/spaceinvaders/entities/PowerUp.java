package fr.university.spaceinvaders.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe de base pour les power-ups
 */
public abstract class PowerUp extends GameEntity {
    protected PowerUpType type;
    protected double lifeTime = 10.0; // Durée de vie en secondes
    protected double age = 0;

    public enum PowerUpType {
        RAPID_FIRE("Tir Rapide", Color.YELLOW),
        TRIPLE_SHOT("Tir Triple", Color.BLUE),
        SPEED_BOOST("Vitesse+", Color.GREEN),
        SHIELD("Bouclier", Color.CYAN),
        EXTRA_LIFE("Vie+", Color.MAGENTA);

        private final String name;
        private final Color color;

        PowerUpType(String name, Color color) {
            this.name = name;
            this.color = color;
        }

        public String getName() { return name; }
        public Color getColor() { return color; }
    }

    public PowerUp(double x, double y, PowerUpType type) {
        super(x, y, 20, 20);
        this.type = type;
        this.color = type.getColor();
        this.velocityY = 100; // Tombe vers le bas
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        age += deltaTime;
        
        // Effet de clignotement quand le power-up vieillit
        if (age > lifeTime * 0.7) {
            // Clignotement
            if ((int)(age * 10) % 2 == 0) {
                color = Color.WHITE;
            } else {
                color = type.getColor();
            }
        }
        
        // Destruction automatique après la durée de vie
        if (age > lifeTime || y > 600) {
            destroy();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x, y, width, height);
        
        // Bordure
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeOval(x, y, width, height);
        
        // Icône du power-up
        renderIcon(gc);
    }

    /**
     * Rendu de l'icône spécifique au power-up
     */
    protected abstract void renderIcon(GraphicsContext gc);

    public PowerUpType getType() {
        return type;
    }
}