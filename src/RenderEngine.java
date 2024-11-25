import java.util.ArrayList;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RenderEngine extends JPanel implements Engine {

    private ArrayList<Displayable> renderList;

    public RenderEngine() {
        renderList = new ArrayList<>();
    }
    public void setRenderList(ArrayList<Displayable> renderOject) {
        if (!renderList.contains(renderOject)){
            renderList.addAll(renderOject);
        }
    }

    public void addToRenderList(Displayable displayable){
        if (!renderList.contains(displayable)){
            renderList.add(displayable);
        }   
    }




    @Override
    public void update(){
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        renderList.removeIf(obj -> obj instanceof BulletSprite && ((BulletSprite) obj).isDestroyed());
        for (Displayable displayableObject:renderList) {
            displayableObject.draw(g);
        }
    }

}
