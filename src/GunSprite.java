import java.awt.*;
import java.util.ArrayList;

public class GunSprite extends SolidSprite {
    private ArrayList<BulletSprite> bullets = new ArrayList<>();
    private Image bulletImage;
    private  PhysicEngine physicEngine;
    private  RenderEngine renderEngine;

    public void setEngine(PhysicEngine physEngine, RenderEngine renderEngine){
        this.physicEngine = physEngine;
        this.renderEngine = renderEngine;
    }

    public GunSprite(Image image, double height, double width, double x, double y, Image bulletImage) {
        super(image, height, width, x, y);
        this.bulletImage = bulletImage; // Image des balles
    }

    public void newBullet( Direction direction) {
        double bulletX = this.x + this.width / 2;
        double bulletY = this.y + this.height / 2;
        BulletSprite bullet = new BulletSprite(bulletImage, 16, 16, bulletX, bulletY, direction, 8);
        renderEngine.addToRenderList(bullet);  // Ajoute le bullet à la liste de rendu
        physicEngine.addTobulletList(bullet);
        //bullets.add(bullet);
    }

    


    public void update(ArrayList<Sprite> environment) {
        // Met à jour les balles tirées
        //bullets.forEach(bullet -> bullet.update(environment));
        // Supprime les balles détruites
        bullets.removeIf(BulletSprite::isDestroyed);
    }

}
