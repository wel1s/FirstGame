import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    JFrame displayZoneFrame;
    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;

    private boolean isGameRunning = false;

    public Main() throws Exception {
        // Création de la fenêtre principale
        displayZoneFrame = new JFrame("Projet Java");
        displayZoneFrame.setSize(1920, 1080);
        displayZoneFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Initialisation du héros
        DynamicSprite hero = new DynamicSprite(ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 50, 48, 200, 300);

        renderEngine = new RenderEngine();
        gameEngine = new GameEngine(hero);
        physicEngine = new PhysicEngine();

        // Timers pour les moteurs
        Timer renderEngineTimer = new Timer(50, (time) -> {
            if (isGameRunning) renderEngine.update();
        });
        Timer gameEngineTimer = new Timer(50, (time) -> {
            if (isGameRunning) {
                gameEngine.update();
                checkHeroHealth(hero); // Vérifie la santé du héros
            }
        });
        Timer physicEngineTimer = new Timer(50, (time) -> {
            if (isGameRunning) physicEngine.update();
        });

        // Affichage des composants
        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);

        // Initialisation du niveau
        Playground1 niveau = new Playground1("./data/level1.txt");
        renderEngine.setRenderList(niveau.getSpriteList());
        renderEngine.addToRenderList(hero);

        ArrayList<GunSprite> gunSprites = niveau.getGunSprites();
        for (GunSprite gun : gunSprites) {
            gun.setEngine(physicEngine, renderEngine);
        }

        // Timer pour tirer des balles
        Timer bulletTimer = new Timer(1000, (time) -> {
            if (isGameRunning) {
                for (GunSprite gun : gunSprites) {
                    gun.newBullet(Direction.SOUTH);
                }
            }
        });

        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(niveau.getSolidSpriteList());

        displayZoneFrame.addKeyListener(gameEngine);

        // Affichage de la page de démarrage
        showStartMenu(() -> {
            // Démarrer le jeu
            isGameRunning = true;
            renderEngineTimer.start();
            gameEngineTimer.start();
            physicEngineTimer.start();
            bulletTimer.start();
        });
    }

    /**
     * Affiche la page de démarrage pour demander si l'utilisateur veut commencer le jeu.
     */
    private void showStartMenu(Runnable onStart) {
        int choice = JOptionPane.showOptionDialog(
                displayZoneFrame,
                "Voulez-vous commencer le jeu ?",
                "Menu Principal",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Oui", "Non"},
                "Oui"
        );
        if (choice == JOptionPane.YES_OPTION) {
            onStart.run();
        } else {
            System.exit(0);
        }
    }

    /**
     * Vérifie si la santé du héros atteint 0 et affiche l'écran de Game Over.
     */
    private void checkHeroHealth(DynamicSprite hero) {
        if (hero.getHealth() <= 0) {
            isGameRunning = false; // Arrête le jeu
            int choice = JOptionPane.showOptionDialog(
                    displayZoneFrame,
                    "Game Over ! Voulez-vous recommencer ?",
                    "Game Over",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    new String[]{"Recommencer", "Quitter"},
                    "Recommencer"
            );
            if (choice == JOptionPane.YES_OPTION) {
                restartGame();
            } else {
                System.exit(0);
            }
        }
    }

    /**
     * Redémarre le jeu en réinitialisant les composants nécessaires.
     */
    private void restartGame() {
        try {
            displayZoneFrame.getContentPane().removeAll(); // Supprime les composants
            new Main(); // Relance une nouvelle instance
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new Main();
    }
}
