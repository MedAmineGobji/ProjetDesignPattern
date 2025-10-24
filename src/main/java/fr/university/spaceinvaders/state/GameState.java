package fr.university.spaceinvaders.state;

import fr.university.spaceinvaders.SpaceInvadersGame;
import javafx.scene.input.KeyEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Interface pour le State Pattern - définit les actions possibles dans chaque état
 */
public abstract class GameState {
    protected static final Logger logger = LogManager.getLogger(GameState.class);
    protected SpaceInvadersGame context;

    public GameState(SpaceInvadersGame context) {
        this.context = context;
    }

    /**
     * Méthode appelée quand on entre dans cet état
     */
    public abstract void enter();

    /**
     * Méthode appelée quand on sort de cet état
     */
    public abstract void exit();

    /**
     * Met à jour la logique de l'état
     */
    public abstract void update(double deltaTime);

    /**
     * Rendu graphique de l'état
     */
    public abstract void render();

    /**
     * Gestion des entrées clavier
     */
    public abstract void handleKeyPressed(KeyEvent event);

    /**
     * Gestion des relâchements de touches
     */
    public abstract void handleKeyReleased(KeyEvent event);

    /**
     * Nom de l'état pour le logging
     */
    public abstract String getStateName();
}