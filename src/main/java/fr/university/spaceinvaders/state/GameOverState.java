package fr.university.spaceinvaders.state;

import fr.university.spaceinvaders.SpaceInvadersGame;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * État de fin de partie - implémentation du State Pattern
 */
public class GameOverState extends GameState {

    private int selectedOption = 0;
    private final String[] gameOverOptions = {"Nouvelle Partie", "Retour au Menu", "Quitter"};
    private final boolean victory;
    private final int finalScore;

    public GameOverState(SpaceInvadersGame context) {
        super(context);
        this.victory = context.isVictory();
        this.finalScore = context.getScore();
    }

    @Override
    public void enter() {
        logger.info("Entrée dans l'état GameOver - Victoire: {} - Score: {}", victory, finalScore);
        selectedOption = 0;
    }

    @Override
    public void exit() {
        logger.info("Sortie de l'état GameOver");
    }

    @Override
    public void update(double deltaTime) {
        // Pas de logique à mettre à jour dans l'écran de fin
    }

    @Override
    public void render() {
        var gc = context.getGraphicsContext();
        
        // Fond noir
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, context.getWidth(), context.getHeight());

        // Titre selon victoire ou défaite
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        String title = victory ? "VICTOIRE!" : "GAME OVER";
        Color titleColor = victory ? Color.GREEN : Color.RED;
        
        gc.setFill(titleColor);
        double titleWidth = gc.getFont().getSize() * title.length() * 0.6;
        gc.fillText(title, (context.getWidth() - titleWidth) / 2, 150);

        // Score final
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        String scoreText = "Score Final: " + finalScore;
        double scoreWidth = gc.getFont().getSize() * scoreText.length() * 0.6;
        gc.fillText(scoreText, (context.getWidth() - scoreWidth) / 2, 200);

        // Message selon le résultat
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        String message = victory ? 
            "Félicitations ! Vous avez repoussé l'invasion !" :
            "Les envahisseurs ont gagné... Retentez votre chance !";
        double messageWidth = gc.getFont().getSize() * message.length() * 0.4;
        gc.fillText(message, (context.getWidth() - messageWidth) / 2, 240);

        // Options du menu
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 24));
        for (int i = 0; i < gameOverOptions.length; i++) {
            if (i == selectedOption) {
                gc.setFill(Color.YELLOW);
                gc.fillText("> " + gameOverOptions[i], 300, 330 + i * 50);
            } else {
                gc.setFill(Color.WHITE);
                gc.fillText("  " + gameOverOptions[i], 300, 330 + i * 50);
            }
        }

        // Instructions
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        gc.setFill(Color.GRAY);
        gc.fillText("Flèches HAUT/BAS: Navigation | ENTRÉE: Sélection", 250, 520);
    }

    @Override
    public void handleKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();
        
        switch (code) {
            case UP:
                selectedOption = (selectedOption - 1 + gameOverOptions.length) % gameOverOptions.length;
                logger.debug("GameOver: option sélectionnée = {}", gameOverOptions[selectedOption]);
                break;
                
            case DOWN:
                selectedOption = (selectedOption + 1) % gameOverOptions.length;
                logger.debug("GameOver: option sélectionnée = {}", gameOverOptions[selectedOption]);
                break;
                
            case ENTER:
                executeSelectedOption();
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
        logger.info("GameOver: exécution de l'option = {}", gameOverOptions[selectedOption]);
        
        switch (selectedOption) {
            case 0: // Nouvelle Partie
                context.changeState(new PlayingState(context));
                break;
                
            case 1: // Retour au Menu
                context.changeState(new MenuState(context));
                break;
                
            case 2: // Quitter
                logger.info("Fermeture du jeu demandée depuis GameOver");
                context.shutdown();
                break;
        }
    }

    @Override
    public String getStateName() {
        return "GameOver";
    }
}