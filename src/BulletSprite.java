import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class BulletSprite extends DynamicSprite {
    //private Direction direction;
    private int damage;
    private boolean destroyed = false;


    public BulletSprite(Image image, double height, double width, double x, double y, Direction direction, int damage) {
        super(image, height, width, x, y);
        this.direction = direction;
        this.damage = damage;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    protected boolean isMovingPossible(ArrayList<Sprite> environment,ArrayList<DynamicSprite> dynamicSpriteList) {
        Rectangle2D.Double hitbox = new Rectangle2D.Double();

        switch(direction){
            case EAST: hitbox.setRect(super.getHitBox().getX()+speed,super.getHitBox().getY(),
                                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:  hitbox.setRect(super.getHitBox().getX()-speed,super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH:  hitbox.setRect(super.getHitBox().getX(),super.getHitBox().getY()-speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH:  hitbox.setRect(super.getHitBox().getX(),super.getHitBox().getY()+speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
        }

        for (Sprite sprite : dynamicSpriteList) {
            if (sprite instanceof DynamicSprite dynamicSprite) {
                if (dynamicSprite.getHitBox().intersects(hitbox)) {
                    // Collision avec un DynamicSprite
                    if (!dynamicSprite.isInvincible) {
                        dynamicSprite.takeDamage(this.damage);
                        dynamicSprite.setInvincible(true);
                        dynamicSprite.setInvincibleStartTime(System.currentTimeMillis());
                    }
                    this.destroy();
                    return false;
                }
            }
        }

        for (Sprite sprite : environment) {
            if (sprite == this) continue;

            

            if (sprite instanceof SolidSprite solidSprite) {
                if (solidSprite.intersect(hitbox)) {
                    // Collision avec un SolidSprite
                    if (sprite instanceof GunSprite){
                        return true;
                    }
                    this.destroy();
                    return false;
                }
            } 

        }
        return true;
    }

    public void moveIfPossible(ArrayList<Sprite> environment,ArrayList<DynamicSprite> dynamicSpriteList){
        if (isMovingPossible(environment,dynamicSpriteList)){
            move();
        }
    }


    public void destroy() {
        this.destroyed = true;
        System.out.println("BulletSprite détruit");
    }

    @Override
    public void draw(Graphics g) {
        if (!destroyed) {
            g.drawImage(image, (int)x, (int)y, null);
        }
    }
}
