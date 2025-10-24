package fr.university.spaceinvaders;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Application principale JavaFX - Point d'entrée du jeu
 */
public class SpaceInvadersApplication extends Application {
    private static final Logger logger = LogManager.getLogger(SpaceInvadersApplication.class);
    
    private static final double SCREEN_WIDTH = 800;
    private static final double SCREEN_HEIGHT = 600;
    
    private SpaceInvadersGame game;
    private AnimationTimer gameLoop;

    @Override
    public void start(Stage primaryStage) {
        logger.info("Démarrage de l'application Space Invaders");
        
        // Créer le canvas
        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        // Initialiser le jeu
        game = new SpaceInvadersGame(gc);
        
        // Créer la scène
        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        
        // Configuration de la fenêtre
        primaryStage.setTitle("Space Invaders - Design Patterns");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        
        // Gestion des événements clavier
        scene.setOnKeyPressed(event -> game.handleKeyPressed(event));
        scene.setOnKeyReleased(event -> game.handleKeyReleased(event));
        
        // S'assurer que le canvas peut recevoir le focus
        canvas.setFocusTraversable(true);
        canvas.requestFocus();
        
        // Boucle de jeu
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!game.isRunning()) {
                    stop();
                    primaryStage.close();
                    return;
                }
                
                game.update(now);
                game.render();
            }
        };
        
        // Afficher la fenêtre et démarrer le jeu
        primaryStage.show();
        gameLoop.start();
        
        // Gérer la fermeture de la fenêtre
        primaryStage.setOnCloseRequest(event -> {
            logger.info("Fermeture de l'application");
            gameLoop.stop();
            game.shutdown();
        });
        
        logger.info("Application démarrée avec succès");
    }

    public static void main(String[] args) {
        logger.info("Lancement du jeu Space Invaders avec Design Patterns");
        launch(args);
    }
}