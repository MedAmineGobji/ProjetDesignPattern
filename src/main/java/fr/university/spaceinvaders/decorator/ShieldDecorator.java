package fr.university.spaceinvaders.decorator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Décorateur Bouclier - protection temporaire contre les dégâts
 */
public class ShieldDecorator extends PlayerDecorator {
    private boolean shieldActive = true;

    public ShieldDecorator(PlayerCapabilities player, double duration) {
        super(player, duration);
    }

    @Override
    public void takeDamage() {
        if (shieldActive && isActive()) {
            // Absorber le premier coup
            shieldActive = false;
            timeRemaining = Math.min(timeRemaining, 2.0); // Réduit la durée après absorption
        } else {
            player.takeDamage();
        }
    }

    @Override
    protected void renderEffect(GraphicsContext gc) {
        if (shieldActive && isActive()) {
            // Bouclier cyan
            gc.setStroke(Color.CYAN);
            gc.setLineWidth(3);
            double alpha = 0.5 + 0.5 * Math.sin(System.currentTimeMillis() * 0.01);
            gc.setGlobalAlpha(alpha);
            gc.strokeOval(getX() - 8, getY() - 8, getWidth() + 16, getHeight() + 16);
            gc.setGlobalAlpha(1.0);
        }
    }

    @Override
    public String getPowerUpName() {
        return "Bouclier";
    }
}