import java.awt.Graphics;
import java.awt.Image;

public class HealthSprite extends Sprite {
    public HealthSprite(Image image, double height, double width,double x, double y) {
        super(image, height,  width,x, y);
    }
    // ajouter la logique po
    @Override
    public void draw(Graphics g) {
        g.drawImage(image,(int)x,(int)y,null);
    }
}
