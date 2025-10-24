package fr.university.spaceinvaders.composite;

import fr.university.spaceinvaders.entities.Enemy;
import fr.university.spaceinvaders.factory.EntityFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Factory pour créer différents types de formations d'ennemis
 */
public class FormationFactory {
    private static final Logger logger = LogManager.getLogger(FormationFactory.class);

    /**
     * Crée une formation rectangulaire d'ennemis
     */
    public static EnemyFormation createRectangularFormation(String name, int rows, int cols, 
                                                          double startX, double startY, 
                                                          double spacingX, double spacingY,
                                                          int waveNumber) {
        EnemyFormation formation = new EnemyFormation(name);
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = startX + col * spacingX;
                double y = startY + row * spacingY;
                
                Enemy enemy = EntityFactory.EnemyFactory.createEnemyForWave(waveNumber, x, y);
                formation.addComponent(new EnemyLeaf(enemy));
            }
        }
        
        logger.info("Formation rectangulaire créée: {} ({}x{} ennemis)", name, rows, cols);
        return formation;
    }

    /**
     * Crée une formation en V d'ennemis
     */
    public static EnemyFormation createVFormation(String name, int size, 
                                                double centerX, double startY, 
                                                double spacing, int waveNumber) {
        EnemyFormation formation = new EnemyFormation(name);
        
        for (int i = 0; i < size; i++) {
            // Côté gauche du V
            double leftX = centerX - spacing * (i + 1);
            double leftY = startY + spacing * i;
            Enemy leftEnemy = EntityFactory.EnemyFactory.createEnemyForWave(waveNumber, leftX, leftY);
            formation.addComponent(new EnemyLeaf(leftEnemy));
            
            // Côté droit du V
            double rightX = centerX + spacing * (i + 1);
            double rightY = startY + spacing * i;
            Enemy rightEnemy = EntityFactory.EnemyFactory.createEnemyForWave(waveNumber, rightX, rightY);
            formation.addComponent(new EnemyLeaf(rightEnemy));
        }
        
        // Ennemi au centre du V
        Enemy centerEnemy = EntityFactory.EnemyFactory.createEnemyForWave(waveNumber, centerX, startY);
        formation.addComponent(new EnemyLeaf(centerEnemy));
        
        logger.info("Formation en V créée: {} ({} ennemis)", name, size * 2 + 1);
        return formation;
    }

    /**
     * Crée une formation en diamant
     */
    public static EnemyFormation createDiamondFormation(String name, int levels, 
                                                      double centerX, double centerY, 
                                                      double spacing, int waveNumber) {
        EnemyFormation formation = new EnemyFormation(name);
        
        for (int level = 0; level < levels; level++) {
            int enemiesInLevel = (level + 1) * 2 - 1;
            double startX = centerX - (enemiesInLevel - 1) * spacing / 2;
            double y = centerY + level * spacing;
            
            for (int i = 0; i < enemiesInLevel; i++) {
                double x = startX + i * spacing;
                Enemy enemy = EntityFactory.EnemyFactory.createEnemyForWave(waveNumber, x, y);
                formation.addComponent(new EnemyLeaf(enemy));
            }
        }
        
        // Partie inférieure du diamant (symétrique)
        for (int level = levels - 2; level >= 0; level--) {
            int enemiesInLevel = (level + 1) * 2 - 1;
            double startX = centerX - (enemiesInLevel - 1) * spacing / 2;
            double y = centerY + (2 * levels - level - 2) * spacing;
            
            for (int i = 0; i < enemiesInLevel; i++) {
                double x = startX + i * spacing;
                Enemy enemy = EntityFactory.EnemyFactory.createEnemyForWave(waveNumber, x, y);
                formation.addComponent(new EnemyLeaf(enemy));
            }
        }
        
        logger.info("Formation en diamant créée: {} ({} niveaux)", name, levels);
        return formation;
    }

    /**
     * Crée une vague complète avec plusieurs formations
     */
    public static EnemyFormation createWave(int waveNumber, double screenWidth) {
        EnemyFormation wave = new EnemyFormation("Vague " + waveNumber);
        wave.setScreenWidth(screenWidth);
        
        switch (waveNumber % 4) {
            case 1:
                // Formation rectangulaire simple
                EnemyFormation rect = createRectangularFormation(
                    "Escadron Principal", 3, 8, 100, 50, 60, 40, waveNumber);
                wave.addComponent(rect);
                break;
                
            case 2:
                // Deux formations en V
                EnemyFormation v1 = createVFormation(
                    "Escadron Alpha", 3, screenWidth / 3, 50, 50, waveNumber);
                EnemyFormation v2 = createVFormation(
                    "Escadron Beta", 3, 2 * screenWidth / 3, 50, 50, waveNumber);
                wave.addComponent(v1);
                wave.addComponent(v2);
                break;
                
            case 3:
                // Formation en diamant + rectangulaire
                EnemyFormation diamond = createDiamondFormation(
                    "Avant-garde", 3, screenWidth / 2, 50, 40, waveNumber);
                EnemyFormation support = createRectangularFormation(
                    "Support", 2, 6, 150, 200, 70, 50, waveNumber);
                wave.addComponent(diamond);
                wave.addComponent(support);
                break;
                
            case 0:
                // Formation complexe (boss wave)
                EnemyFormation center = createDiamondFormation(
                    "Elite", 2, screenWidth / 2, 50, 60, waveNumber);
                EnemyFormation left = createVFormation(
                    "Flanc Gauche", 2, screenWidth / 4, 150, 45, waveNumber);
                EnemyFormation right = createVFormation(
                    "Flanc Droit", 2, 3 * screenWidth / 4, 150, 45, waveNumber);
                wave.addComponent(center);
                wave.addComponent(left);
                wave.addComponent(right);
                break;
        }
        
        // Ajuster la vitesse selon le niveau
        wave.setFormationSpeed(30 + waveNumber * 5);
        
        logger.info("Vague {} créée avec {} ennemis", waveNumber, wave.getAliveCount());
        return wave;
    }
}