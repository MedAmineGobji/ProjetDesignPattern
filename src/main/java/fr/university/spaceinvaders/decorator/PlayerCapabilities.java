package fr.university.spaceinvaders.decorator;

import fr.university.spaceinvaders.entities.Projectile;
import javafx.scene.canvas.GraphicsContext;
import java.util.List;

/**
 * Interface pour les capacités du joueur - Decorator Pattern
 */
public interface PlayerCapabilities {
    
    /**
     * Mouvement du joueur
     */
    void move(int direction, double deltaTime, double screenWidth);
    
    /**
     * Arrêt du mouvement
     */
    void stop();
    
    /**
     * Tir du joueur
     */
    List<Projectile> shoot(double currentTime);
    
    /**
     * Vérification si le joueur peut tirer
     */
    boolean canShoot(double currentTime);
    
    /**
     * Rendu du joueur
     */
    void render(GraphicsContext gc);
    
    /**
     * Mise à jour du joueur
     */
    void update(double deltaTime);
    
    /**
     * Position X du joueur
     */
    double getX();
    
    /**
     * Position Y du joueur
     */
    double getY();
    
    /**
     * Largeur du joueur
     */
    double getWidth();
    
    /**
     * Hauteur du joueur
     */
    double getHeight();
    
    /**
     * Vies du joueur
     */
    int getLives();
    
    /**
     * Prendre des dégâts
     */
    void takeDamage();
    
    /**
     * Vérifier si le joueur est vivant
     */
    boolean isAlive();
}