package fr.university.spaceinvaders.decorator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Décorateur Vitesse - augmente la vitesse de déplacement
 */
public class SpeedBoostDecorator extends PlayerDecorator {
    private static final double SPEED_MULTIPLIER = 1.5;

    public SpeedBoostDecorator(PlayerCapabilities player, double duration) {
        super(player, duration);
    }

    @Override
    public void move(int direction, double deltaTime, double screenWidth) {
        // Appliquer le multiplicateur de vitesse
        player.move(direction, deltaTime * SPEED_MULTIPLIER, screenWidth);
    }

    @Override
    protected void renderEffect(GraphicsContext gc) {
        // Traînée verte
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        for (int i = 0; i < 3; i++) {
            gc.strokeOval(getX() - i*2, getY() + getHeight() + i*3, 
                         getWidth() + i*4, 2);
        }
    }

    @Override
    public String getPowerUpName() {
        return "Vitesse+";
    }
}