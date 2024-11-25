import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Playground1 {
    private ArrayList<Sprite> environment = new ArrayList<>();
    public ArrayList<Sprite> getEnvironment() {
        return environment;
    }

    private final ArrayList<GunSprite> gunSprites = new ArrayList<>(); // Liste des GunSprites

    public Playground1(String pathName) {
        try {
            final Image imageTree = ImageIO.read(new File("./img/tree.png"));
            final Image imageGrass = ImageIO.read(new File("./img/grass.png"));
            final Image imageRock = ImageIO.read(new File("./img/rock.png"));
            final Image imageTrap = ImageIO.read(new File("./img/trap.png"));
            final Image imageGun = ImageIO.read(new File("./img/gun.png")); // Image pour GunSprite
            final Image imageBullet = ImageIO.read(new File("./img/bullet.png")); // Image pour BulletSprite

            final int imageTreeWidth = imageTree.getWidth(null);
            final int imageTreeHeight = imageTree.getHeight(null);

            final int imageGrassWidth = imageGrass.getWidth(null);
            final int imageGrassHeight = imageGrass.getHeight(null);

            final int imageRockWidth = imageRock.getWidth(null);
            final int imageRockHeight = imageRock.getHeight(null);

            final int imageTrapWidth = imageTrap.getWidth(null);
            final int imageTrapHeight = imageTrap.getHeight(null);

            final int imageGunWidth = imageGun.getWidth(null);
            final int imageGunHeight = imageGun.getHeight(null);

            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            String line = bufferedReader.readLine();
            int lineNumber = 0;
            int columnNumber = 0;
            while (line != null) {
                for (byte element : line.getBytes(StandardCharsets.UTF_8)) {
                    switch (element) {
                        case 'T':
                            environment.add(new SolidSprite(imageTree, imageTreeHeight, imageTreeWidth,
                                    columnNumber * imageTreeWidth, lineNumber * imageTreeHeight));
                            break;
                        case ' ':
                            environment.add(new Sprite(imageGrass, imageGrassHeight, imageGrassWidth,
                                    columnNumber * imageGrassWidth, lineNumber * imageGrassHeight));
                            break;
                        case 'R':
                            environment.add(new SolidSprite(imageRock, imageRockHeight, imageRockWidth,
                                    columnNumber * imageRockWidth, lineNumber * imageRockHeight));
                            break;
                        case 'K':
                            environment.add(new DangerSolidSprite(imageTrap, imageTrapHeight, imageTrapWidth,
                                    columnNumber * imageTrapWidth, lineNumber * imageTrapHeight));
                            break;
                        case 'G': // Ajouter un GunSprite
                            GunSprite gun = new GunSprite(imageGun, imageGunHeight, imageGunWidth,
                                    columnNumber * imageGunWidth, lineNumber * imageGunHeight, imageBullet);
                            gunSprites.add(gun); // Ajouter dans la liste des GunSprites
                            environment.add(gun); // Ajouter dans l'environnement général
                            break;
                    }
                    columnNumber++;
                }
                columnNumber = 0;
                lineNumber++;
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Sprite> getSolidSpriteList() {
        ArrayList<Sprite> solidSpriteArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite) solidSpriteArrayList.add(sprite);
        }
        return solidSpriteArrayList;
    }

    public ArrayList<Displayable> getSpriteList() {
        ArrayList<Displayable> displayableArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            displayableArrayList.add((Displayable) sprite);
        }
        return displayableArrayList;
    }

    public ArrayList<GunSprite> getGunSprites() {
        return gunSprites; // Retourne uniquement les GunSprites
    }
}
