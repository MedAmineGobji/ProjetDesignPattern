package fr.university.spaceinvaders.state;

import fr.university.spaceinvaders.SpaceInvadersGame;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * État du menu principal - implémentation du State Pattern
 */
public class MenuState extends GameState {

    private int selectedOption = 0;
    private final String[] menuOptions = {"Nouvelle Partie", "Options", "Quitter"};

    public MenuState(SpaceInvadersGame context) {
        super(context);
    }

    @Override
    public void enter() {
        logger.info("Entrée dans l'état Menu");
        selectedOption = 0;
    }

    @Override
    public void exit() {
        logger.info("Sortie de l'état Menu");
    }

    @Override
    public void update(double deltaTime) {
        // Pas de logique particulière à mettre à jour dans le menu
    }

    @Override
    public void render() {
        var gc = context.getGraphicsContext();
        
        // Fond noir
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, context.getWidth(), context.getHeight());

        // Titre du jeu
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        String title = "SPACE INVADERS";
        double titleWidth = gc.getFont().getSize() * title.length() * 0.6;
        gc.fillText(title, (context.getWidth() - titleWidth) / 2, 150);

        // Sous-titre avec patterns
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        String subtitle = "Projet Design Patterns";
        double subtitleWidth = gc.getFont().getSize() * subtitle.length() * 0.6;
        gc.fillText(subtitle, (context.getWidth() - subtitleWidth) / 2, 180);

        // Options du menu
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 24));
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedOption) {
                gc.setFill(Color.YELLOW);
                gc.fillText("> " + menuOptions[i], 300, 300 + i * 50);
            } else {
                gc.setFill(Color.WHITE);
                gc.fillText("  " + menuOptions[i], 300, 300 + i * 50);
            }
        }

        // Instructions
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        gc.setFill(Color.GRAY);
        gc.fillText("Utilisez les flèches HAUT/BAS pour naviguer, ENTRÉE pour sélectionner", 150, 500);
    }

    @Override
    public void handleKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();
        
        switch (code) {
            case UP:
                selectedOption = (selectedOption - 1 + menuOptions.length) % menuOptions.length;
                logger.debug("Menu: option sélectionnée = {}", menuOptions[selectedOption]);
                break;
                
            case DOWN:
                selectedOption = (selectedOption + 1) % menuOptions.length;
                logger.debug("Menu: option sélectionnée = {}", menuOptions[selectedOption]);
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
        // Pas d'action particulière sur le relâchement des touches dans le menu
    }

    private void executeSelectedOption() {
        logger.info("Menu: exécution de l'option = {}", menuOptions[selectedOption]);
        
        switch (selectedOption) {
            case 0: // Nouvelle Partie
                context.changeState(new PlayingState(context));
                break;
                
            case 1: // Options (pas implémenté pour ce projet)
                logger.info("Options pas encore implémentées");
                break;
                
            case 2: // Quitter
                logger.info("Fermeture du jeu demandée depuis le menu");
                context.shutdown();
                break;
        }
    }

    @Override
    public String getStateName() {
        return "Menu";
    }
}