import java.awt.*;
import java.awt.geom.Rectangle2D;
public class SolidSprite extends Sprite{
    public SolidSprite(Image image, double height, double width,double x, double y) {
        super(image, height,  width,x, y);
    }
    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x,y,(double) width,(double) height);
    }

    public boolean intersect(Rectangle2D.Double hitBox){
        return this.getHitBox().intersects(hitBox);
    }
}
