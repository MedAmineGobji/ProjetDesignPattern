package fr.university.spaceinvaders;

import fr.university.spaceinvaders.decorator.*;
import fr.university.spaceinvaders.decorator.SpeedBoostDecorator;
import fr.university.spaceinvaders.entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests pour le Decorator Pattern
 */
class PlayerDecoratorTest {

    private Player player;
    private PlayerCapabilities basePlayer;

    @BeforeEach
    void setUp() {
        player = new Player(100, 100);
        basePlayer = new BasicPlayerCapabilities(player);
    }

    @Test
    void testBasicPlayerCapabilities() {
        assertEquals(100, basePlayer.getX());
        assertEquals(100, basePlayer.getY());
        assertEquals(3, basePlayer.getLives());
        assertTrue(basePlayer.isAlive());
    }

    @Test
    void testRapidFireDecorator() {
        PlayerCapabilities rapidFirePlayer = new RapidFireDecorator(basePlayer, 10.0);
        
        // Vérifier que les propriétés de base sont conservées
        assertEquals(100, rapidFirePlayer.getX());
        assertEquals(100, rapidFirePlayer.getY());
        assertEquals(3, rapidFirePlayer.getLives());
        
        // Vérifier que c'est bien un décorateur actif
        assertTrue(rapidFirePlayer instanceof RapidFireDecorator);
        RapidFireDecorator decorator = (RapidFireDecorator) rapidFirePlayer;
        assertTrue(decorator.isActive());
        assertEquals("Tir Rapide", decorator.getPowerUpName());
    }

    @Test
    void testSpeedBoostDecorator() {
        PlayerCapabilities speedPlayer = new SpeedBoostDecorator(basePlayer, 8.0);
        
        assertTrue(speedPlayer instanceof SpeedBoostDecorator);
        SpeedBoostDecorator decorator = (SpeedBoostDecorator) speedPlayer;
        assertTrue(decorator.isActive());
        assertEquals("Vitesse+", decorator.getPowerUpName());
    }

    @Test
    void testShieldDecorator() {
        PlayerCapabilities shieldPlayer = new ShieldDecorator(basePlayer, 15.0);
        
        assertTrue(shieldPlayer instanceof ShieldDecorator);
        ShieldDecorator decorator = (ShieldDecorator) shieldPlayer;
        assertTrue(decorator.isActive());
        assertEquals("Bouclier", decorator.getPowerUpName());
        
        // Test de protection contre les dégâts
        int initialLives = shieldPlayer.getLives();
        shieldPlayer.takeDamage(); // Premier coup absorbé par le bouclier
        assertEquals(initialLives, shieldPlayer.getLives()); // Pas de dégât
        
        shieldPlayer.takeDamage(); // Deuxième coup traverse
        assertEquals(initialLives - 1, shieldPlayer.getLives()); // Dégât pris
    }

    @Test
    void testMultipleDecorators() {
        // Empiler plusieurs décorateurs
        PlayerCapabilities decoratedPlayer = basePlayer;
        decoratedPlayer = new RapidFireDecorator(decoratedPlayer, 10.0);
        decoratedPlayer = new SpeedBoostDecorator(decoratedPlayer, 8.0);
        decoratedPlayer = new ShieldDecorator(decoratedPlayer, 15.0);
        
        // Vérifier que les propriétés de base sont toujours accessibles
        assertEquals(100, decoratedPlayer.getX());
        assertEquals(100, decoratedPlayer.getY());
        assertEquals(3, decoratedPlayer.getLives());
        
        // Vérifier que c'est le décorateur le plus externe
        assertTrue(decoratedPlayer instanceof ShieldDecorator);
    }

    @Test
    void testDecoratorExpiration() {
        RapidFireDecorator decorator = new RapidFireDecorator(basePlayer, 1.0); // 1 seconde
        
        assertTrue(decorator.isActive());
        
        // Simuler le passage du temps
        decorator.update(0.5); // 0.5 seconde écoulée
        assertTrue(decorator.isActive());
        assertEquals(0.5, decorator.getTimeRemaining(), 0.01);
        
        decorator.update(0.6); // 1.1 seconde écoulée au total
        assertFalse(decorator.isActive());
        assertTrue(decorator.getTimeRemaining() <= 0);
    }

    @Test
    void testDecoratorUnwrapping() {
        PlayerCapabilities decoratedPlayer = new RapidFireDecorator(basePlayer, 10.0);
        
        // Vérifier l'accès au joueur décoré
        assertTrue(decoratedPlayer instanceof RapidFireDecorator);
        RapidFireDecorator decorator = (RapidFireDecorator) decoratedPlayer;
        PlayerCapabilities unwrapped = decorator.getDecoratedPlayer();
        
        assertTrue(unwrapped instanceof BasicPlayerCapabilities);
        assertEquals(basePlayer, unwrapped);
    }
}