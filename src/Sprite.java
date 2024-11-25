import java.awt.*;

public class Sprite implements Displayable {
    protected Image image;
    protected double height;
    protected double width;
    protected double x;
    protected double y;

    public Sprite(Image image, double height, double width, double x, double y) {
        this.image = image;
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image,(int)x,(int)y,null);
    }
    
}
