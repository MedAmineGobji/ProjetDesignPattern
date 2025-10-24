package fr.university.spaceinvaders.composite;

import javafx.scene.canvas.GraphicsContext;

/**
 * Interface pour le Composite Pattern - composant de base
 */
public interface EnemyComponent {
    
    /**
     * Met à jour le composant
     */
    void update(double deltaTime);
    
    /**
     * Rendu du composant
     */
    void render(GraphicsContext gc);
    
    /**
     * Déplacement du composant
     */
    void move(double deltaX, double deltaY);
    
    /**
     * Vérifie si le composant est détruit
     */
    boolean isDestroyed();
    
    /**
     * Détruit le composant
     */
    void destroy();
    
    /**
     * Nombre d'ennemis vivants dans ce composant
     */
    int getAliveCount();
    
    /**
     * Position X minimale du composant
     */
    double getMinX();
    
    /**
     * Position X maximale du composant
     */
    double getMaxX();
    
    /**
     * Position Y minimale du composant
     */
    double getMinY();
    
    /**
     * Position Y maximale du composant
     */
    double getMaxY();
    
    /**
     * Vérification de collision avec un point
     */
    boolean checkCollision(double x, double y, double width, double height);
}