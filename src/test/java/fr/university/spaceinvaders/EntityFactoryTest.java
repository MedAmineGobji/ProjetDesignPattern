package fr.university.spaceinvaders;

import fr.university.spaceinvaders.entities.*;
import fr.university.spaceinvaders.factory.EntityFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests pour le Factory Pattern
 */
class EntityFactoryTest {

    @Test
    void testEnemyFactoryCreatesCorrectTypes() {
        // Test de création d'ennemis basiques
        Enemy basicEnemy = EntityFactory.EnemyFactory.createEnemy(Enemy.EnemyType.BASIC, 100, 100);
        assertNotNull(basicEnemy);
        assertTrue(basicEnemy instanceof BasicEnemy);
        assertEquals(Enemy.EnemyType.BASIC, basicEnemy.getType());
        assertEquals(10, basicEnemy.getPoints());

        // Test de création d'ennemis rapides
        Enemy fastEnemy = EntityFactory.EnemyFactory.createEnemy(Enemy.EnemyType.FAST, 100, 100);
        assertNotNull(fastEnemy);
        assertTrue(fastEnemy instanceof FastEnemy);
        assertEquals(Enemy.EnemyType.FAST, fastEnemy.getType());
        assertEquals(20, fastEnemy.getPoints());

        // Test de création d'ennemis lourds
        Enemy heavyEnemy = EntityFactory.EnemyFactory.createEnemy(Enemy.EnemyType.HEAVY, 100, 100);
        assertNotNull(heavyEnemy);
        assertTrue(heavyEnemy instanceof HeavyEnemy);
        assertEquals(Enemy.EnemyType.HEAVY, heavyEnemy.getType());
        assertEquals(30, heavyEnemy.getPoints());
    }

    @Test
    void testProjectileFactoryCreatesCorrectTypes() {
        // Test de création de projectile joueur
        Projectile playerProjectile = EntityFactory.ProjectileFactory.createPlayerProjectile(100, 100);
        assertNotNull(playerProjectile);
        assertTrue(playerProjectile.isPlayerProjectile());
        assertTrue(playerProjectile.getVelocityY() < 0); // Va vers le haut

        // Test de création de projectile ennemi
        Projectile enemyProjectile = EntityFactory.ProjectileFactory.createEnemyProjectile(100, 100);
        assertNotNull(enemyProjectile);
        assertFalse(enemyProjectile.isPlayerProjectile());
        assertTrue(enemyProjectile.getVelocityY() > 0); // Va vers le bas
    }

    @Test
    void testPowerUpFactoryCreatesCorrectTypes() {
        // Test de création de power-up tir rapide
        PowerUp rapidFire = EntityFactory.PowerUpFactory.createPowerUp(PowerUp.PowerUpType.RAPID_FIRE, 100, 100);
        assertNotNull(rapidFire);
        assertEquals(PowerUp.PowerUpType.RAPID_FIRE, rapidFire.getType());

        // Test de création de power-up aléatoire
        PowerUp randomPowerUp = EntityFactory.PowerUpFactory.createRandomPowerUp(100, 100);
        assertNotNull(randomPowerUp);
        assertNotNull(randomPowerUp.getType());

        // Test de création avec probabilité
        PowerUp probPowerUp = EntityFactory.PowerUpFactory.createPowerUpWithProbability(100, 100, 1.0);
        assertNotNull(probPowerUp); // Avec probabilité 1.0, doit toujours créer

        PowerUp noPowerUp = EntityFactory.PowerUpFactory.createPowerUpWithProbability(100, 100, 0.0);
        assertNull(noPowerUp); // Avec probabilité 0.0, ne doit jamais créer
    }

    @Test
    void testEnemyFactoryForWave() {
        // Test des premières vagues (seulement ennemis basiques)
        Enemy wave1Enemy = EntityFactory.EnemyFactory.createEnemyForWave(1, 100, 100);
        assertEquals(Enemy.EnemyType.BASIC, wave1Enemy.getType());

        Enemy wave2Enemy = EntityFactory.EnemyFactory.createEnemyForWave(2, 100, 100);
        assertEquals(Enemy.EnemyType.BASIC, wave2Enemy.getType());

        // Test des vagues intermédiaires (mix basic/fast)
        Enemy wave3Enemy = EntityFactory.EnemyFactory.createEnemyForWave(3, 100, 100);
        assertTrue(wave3Enemy.getType() == Enemy.EnemyType.BASIC || 
                  wave3Enemy.getType() == Enemy.EnemyType.FAST);

        // Test des vagues avancées (tous types possibles)
        Enemy wave6Enemy = EntityFactory.EnemyFactory.createEnemyForWave(6, 100, 100);
        assertNotNull(wave6Enemy.getType());
    }
}