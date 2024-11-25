import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
public class DynamicSprite extends SolidSprite {
    public boolean isWalking = true;
    public double speed = 5;
    public final int spriteSheetNumberOfColumn =10;
    public int timeBetweenFrame = 200;
    //public Direction direction;
    protected Direction direction = Direction.SOUTH;  // ceci est egalement a revoir
    private int attitude = direction.getFrameLineNumber();
    
    private int health = 100;
    public boolean isInvincible = false;
    private long invincibleStartTime = -1; 
    public void setInvincibleStartTime(long invincibleStartTime) {
        this.invincibleStartTime = invincibleStartTime;
    }
    private static final long INVINCIBLE_DURATION = 2000;

    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }
    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        System.out.println("j'ai recu un truc");
        this.health-=damage;    
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public DynamicSprite(Image image,  double height, double width,double x, double y){
        super(image, height, width, x, y);
    }

    @Override
    public void draw(Graphics g) {
        update();
        int index =  (int)((System.currentTimeMillis()/timeBetweenFrame)%spriteSheetNumberOfColumn);
        attitude = direction.getFrameLineNumber();

        System.out.println(index);
        g.drawImage(image,(int)x,(int)y,(int)(x+width), (int)(y+height),
                (int)(index*width),(int)(attitude*height),
                (int)((index+1)*width),(int)((attitude+1)*height),null);

        if (isInvincible) {
            drawHealthBar(g);
        }
    }

    protected void move(){
        switch (direction){
            case NORTH -> {
                this.y-=speed;
            }
            case SOUTH -> {
                this.y+=speed;
            }
            case EAST -> {
                this.x+=speed;
            }
            case WEST -> {
                this.x-=speed;
            }
        }
    }
    protected boolean isMovingPossible(ArrayList<Sprite> environment){
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

        for (Sprite element : environment) {
            if ((element instanceof SolidSprite) && (element!=this)) {
                if (((SolidSprite) element).intersect(hitbox)) {
                    if ((element instanceof DangerSolidSprite)&&(!isInvincible)) {
                        takeDamage(10);
                        setInvincible(true);
                        setInvincibleStartTime(System.currentTimeMillis());
                        System.out.println(health);
                    }
                    return false;   
                }
            }
        }
        return true;
    }
    public void moveIfPossible(ArrayList<Sprite> environment){
        if (isMovingPossible(environment)){
            move();
        }
    }
    public void update() {
        if (invincibleStartTime != -1 && System.currentTimeMillis() - invincibleStartTime >= INVINCIBLE_DURATION) {
            setInvincible(false);  
            invincibleStartTime = -1;  
        }
    }
    private void drawHealthBar(Graphics g) {
        int barWidth = 40; // Largeur de la barre
        int barHeight = 5; // Hauteur de la barre
        int barX = (int) (x + width / 2 - barWidth / 2); // Centrer la barre au-dessus du héros
        int barY = (int) (y - barHeight - 5); // Placer la barre au-dessus de la tête avec un petit espace
    
        // Dessiner la bordure de la barre
        g.setColor(Color.BLACK);
        g.drawRect(barX, barY, barWidth, barHeight);
    
        // Dessiner la barre de santé (remplie en fonction de la santé restante)
        g.setColor(Color.GREEN);
        int filledWidth = (int) ((health / 100.0) * barWidth); // Proportion remplie
        g.fillRect(barX, barY, filledWidth, barHeight);
    
        // Dessiner la partie non remplie (rouge)
        g.setColor(Color.RED);
        g.fillRect(barX + filledWidth, barY, barWidth - filledWidth, barHeight);
    }
}
