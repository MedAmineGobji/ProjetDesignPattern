package fr.university.spaceinvaders.state;

import fr.university.spaceinvaders.SpaceInvadersGame;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * État de pause - implémentation du State Pattern
 */
public class PausedState extends GameState {

    private int selectedOption = 0;
    private final String[] pauseOptions = {"Reprendre", "Retour au Menu", "Quitter"};

    public PausedState(SpaceInvadersGame context) {
        super(context);
    }

    @Override
    public void enter() {
        logger.info("Entrée dans l'état Paused");
        selectedOption = 0;
    }

    @Override
    public void exit() {
        logger.info("Sortie de l'état Paused");
    }

    @Override
    public void update(double deltaTime) {
        // Le jeu est en pause, pas de mise à jour
    }

    @Override
    public void render() {
        var gc = context.getGraphicsContext();
        
        // Garder l'arrière-plan du jeu mais assombri
        context.renderGame();
        
        // Overlay semi-transparent
        gc.setFill(Color.color(0, 0, 0, 0.7));
        gc.fillRect(0, 0, context.getWidth(), context.getHeight());

        // Titre PAUSE
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        String title = "PAUSE";
        double titleWidth = gc.getFont().getSize() * title.length() * 0.6;
        gc.fillText(title, (context.getWidth() - titleWidth) / 2, 200);

        // Options du menu pause
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 24));
        for (int i = 0; i < pauseOptions.length; i++) {
            if (i == selectedOption) {
                gc.setFill(Color.YELLOW);
                gc.fillText("> " + pauseOptions[i], 300, 300 + i * 50);
            } else {
                gc.setFill(Color.WHITE);
                gc.fillText("  " + pauseOptions[i], 300, 300 + i * 50);
            }
        }

        // Instructions
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        gc.setFill(Color.GRAY);
        gc.fillText("ECHAP: Reprendre | Flèches: Navigation | ENTRÉE: Sélection", 200, 500);
    }

    @Override
    public void handleKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();
        
        switch (code) {
            case UP:
                selectedOption = (selectedOption - 1 + pauseOptions.length) % pauseOptions.length;
                logger.debug("Pause: option sélectionnée = {}", pauseOptions[selectedOption]);
                break;
                
            case DOWN:
                selectedOption = (selectedOption + 1) % pauseOptions.length;
                logger.debug("Pause: option sélectionnée = {}", pauseOptions[selectedOption]);
                break;
                
            case ENTER:
                executeSelectedOption();
                break;
                
            case ESCAPE:
                // Reprendre directement avec ECHAP
                logger.info("Reprise du jeu via ECHAP");
                context.changeState(new PlayingState(context));
                break;
                
            default:
                break;
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent event) {
        // Pas d'action particulière
    }

    private void executeSelectedOption() {
        logger.info("Pause: exécution de l'option = {}", pauseOptions[selectedOption]);
        
        switch (selectedOption) {
            case 0: // Reprendre
                context.changeState(new PlayingState(context));
                break;
                
            case 1: // Retour au Menu
                context.changeState(new MenuState(context));
                break;
                
            case 2: // Quitter
                logger.info("Fermeture du jeu demandée depuis la pause");
                context.shutdown();
                break;
        }
    }

    @Override
    public String getStateName() {
        return "Paused";
    }
}