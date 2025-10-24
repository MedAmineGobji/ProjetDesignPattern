package fr.university.spaceinvaders.state;

import fr.university.spaceinvaders.SpaceInvadersGame;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * État de jeu en cours - implémentation du State Pattern
 */
public class PlayingState extends GameState {

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean spacePressed = false;

    public PlayingState(SpaceInvadersGame context) {
        super(context);
    }

    @Override
    public void enter() {
        logger.info("Entrée dans l'état Playing - Début de partie");
        context.initializeGame();
    }

    @Override
    public void exit() {
        logger.info("Sortie de l'état Playing");
    }

    @Override
    public void update(double deltaTime) {
        // Mise à jour des entités du jeu
        context.updateGame(deltaTime);
        
        // Vérification des conditions de fin de partie
        if (context.isGameOver()) {
            logger.info("Game Over détecté - transition vers GameOverState");
            context.changeState(new GameOverState(context));
        }
    }

    @Override
    public void render() {
        var gc = context.getGraphicsContext();
        
        // Fond étoilé
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, context.getWidth(), context.getHeight());
        
        // Rendu des entités du jeu
        context.renderGame();
        
        // Interface utilisateur
        renderUI();
    }

    private void renderUI() {
        var gc = context.getGraphicsContext();
        
        // Score
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        gc.fillText("Score: " + context.getScore(), 10, 25);
        
        // Vies
        gc.fillText("Vies: " + context.getLives(), 10, 50);
        
        // Niveau/Vague
        gc.fillText("Vague: " + context.getWaveNumber(), 10, 75);
        
        // Instructions
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        gc.setFill(Color.GRAY);
        gc.fillText("ECHAP: Pause | Flèches: Déplacement | ESPACE: Tir", 10, context.getHeight() - 10);
    }

    @Override
    public void handleKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();
        
        switch (code) {
            case LEFT:
                leftPressed = true;
                context.movePlayer(-1);
                break;
                
            case RIGHT:
                rightPressed = true;
                context.movePlayer(1);
                break;
                
            case SPACE:
                if (!spacePressed) {
                    spacePressed = true;
                    context.playerShoot();
                    logger.debug("Player shoot!");
                }
                break;
                
            case ESCAPE:
                logger.info("Pause demandée");
                context.changeState(new PausedState(context));
                break;
                
            default:
                break;
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent event) {
        KeyCode code = event.getCode();
        
        switch (code) {
            case LEFT:
                leftPressed = false;
                if (!rightPressed) context.stopPlayer();
                break;
                
            case RIGHT:
                rightPressed = false;
                if (!leftPressed) context.stopPlayer();
                break;
                
            case SPACE:
                spacePressed = false;
                break;
                
            default:
                break;
        }
    }

    @Override
    public String getStateName() {
        return "Playing";
    }
}