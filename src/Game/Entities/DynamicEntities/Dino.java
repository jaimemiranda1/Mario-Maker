package Game.Entities.DynamicEntities;

import Main.Handler;
import Resources.Animation;
import Resources.Images;

import java.awt.*;

public class Dino extends BaseDynamicEntity {

    public Animation anim;

    public Dino(int x, int y, int width, int height, Handler handler) {
        super(x, y, width, height, handler, Images.dino[0]);
        anim = new Animation(160,Images.dino);
    }

    @Override
    public void tick(){
        if(!ded && dedCounter==0) {
            super.tick();
            anim.tick();
            if (falling) {
                y = (int) (y + velY);
                velY = velY + gravityAcc;
                checkFalling();
            }
            checkHorizontal();
            move();
        }else if(ded&&dedCounter==0){
            y++;
            height--;
            setDimension(new Dimension(width,height));
            if (height==0){
                dedCounter=1;
                y=-10000;
            }
        }
    }

    @Override
    public void kill() {
        sprite = Images.dinoDies;
        ded=true;
    }
}
