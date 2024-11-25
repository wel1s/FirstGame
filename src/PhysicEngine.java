import java.util.ArrayList;

public class PhysicEngine implements Engine{

    private ArrayList<DynamicSprite> movingSpriteList;
    private ArrayList <Sprite> environment;
    private ArrayList<BulletSprite> bulletList;

    public PhysicEngine() {
        movingSpriteList = new ArrayList<>();
        environment = new ArrayList<>();
        bulletList = new ArrayList<>();
    }

    public void addToEnvironmentList(Sprite sprite){
        if (!environment.contains(sprite)){
            environment.add(sprite);
        }
    }

    public void setEnvironment(ArrayList<Sprite> environment){
        this.environment=environment;
    }

    public void addToMovingSpriteList(DynamicSprite sprite){
        if (!movingSpriteList.contains(sprite)){
            movingSpriteList.add(sprite);
        }
    }
    public void addTobulletList(BulletSprite sprite){
        if (!bulletList.contains(sprite)){
            bulletList.add(sprite);
        }
    }

    @Override
    public void update() {
        for(DynamicSprite dynamicSprite : movingSpriteList){
            dynamicSprite.moveIfPossible(environment);
        }
        bulletList.removeIf(BulletSprite::isDestroyed);
        for(BulletSprite bulletSprite : bulletList){
            bulletSprite.moveIfPossible(environment,movingSpriteList);
        }
    }

    
}
