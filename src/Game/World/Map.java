package Game.World;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import Display.UI.UIListener;
import Display.UI.UIPointer;
import Game.Entities.DynamicEntities.BaseDynamicEntity;
import Game.Entities.DynamicEntities.Goomba;
import Game.Entities.DynamicEntities.Item;
import Game.Entities.DynamicEntities.Luigi;
import Game.Entities.DynamicEntities.Mario;
import Game.Entities.StaticEntities.BaseStaticEntity;
import Game.Entities.StaticEntities.Wall;
import Game.GameStates.MenuState;
import Main.Handler;
import Resources.Images;

public class Map {

    ArrayList<BaseStaticEntity> blocksOnMap;
    ArrayList<BaseDynamicEntity> enemiesOnMap;
    Handler handler;
    private double bottomBorder;
    private UIListener listener;
    private Background hand;
    private Random rand;
    private Wall walls;
    private int mapBackground;
	private ArrayList<BaseStaticEntity> boundaryOnMap;
	private ArrayList<BaseStaticEntity> StarOnMap;

    public Map(Handler handler) {
        this.handler=handler;
        this.rand = new Random();
        this.hand = new Background(this.handler);
        this.listener = new UIListener( this.handler);
        this.walls = new Wall(this.handler);
        this.blocksOnMap = new ArrayList<>();
        this.enemiesOnMap = new ArrayList<>();
        this.boundaryOnMap = new ArrayList<>();
        this.StarOnMap = new ArrayList<>();
        bottomBorder=handler.getHeight();
        this.mapBackground = this.rand.nextInt(6);
    }

    public void addBlock(BaseStaticEntity block){
    	if(block.sprite==Images.boundBlock ) {
    		boundaryOnMap.add(block);
    	}
    	if(block.sprite==Images.winStar ) {
    		StarOnMap.add(block);
    	}
    	 blocksOnMap.add(block);
    }
    public void addEnemy(BaseDynamicEntity entity){
        if(entity instanceof Mario){
            handler.setMario((Mario) entity);
            handler.getCamera().setX(handler.getMario().x- (MapBuilder.pixelMultiplier*6));
            handler.getCamera().setY(handler.getMario().y - (MapBuilder.pixelMultiplier*10));
            bottomBorder=handler.getHeight()+handler.getMario().y;
        }else if(entity instanceof Luigi) {
        	handler.setLuigi((Luigi) entity);
        	handler.getCameraL().setX(handler.getLuigi().x-(MapBuilder.pixelMultiplier*6));
        	handler.getCameraL().setY(handler.getLuigi().y - (MapBuilder.pixelMultiplier*10));
            bottomBorder=handler.getHeight()+handler.getLuigi().y;
        }
        else {
            enemiesOnMap.add(entity);
        }
    }

    public void drawMap(Graphics2D g2) {
    	handler.setIsInMap(true);
        Point camLocation = new Point((int)handler.getCamera().getX(), (int)handler.getCamera().getY());
        g2.translate(-camLocation.x, -camLocation.y);
        g2.drawImage(Images.backgrounds2[this.mapBackground], camLocation.x, camLocation.y, this.handler.getWidth(), this.handler.getHeight(),null);
        for (BaseStaticEntity block:blocksOnMap) {
            g2.drawImage(block.sprite,block.x,block.y,block.width,block.height,null);
        }
        for (BaseDynamicEntity entity:enemiesOnMap) {
            if(entity instanceof Item){
                if(!((Item)entity).used){
                    g2.drawImage(entity.sprite, entity.x, entity.y, entity.width, entity.height, null);
                }
            }else if(entity instanceof Goomba && !entity.ded){
                g2.drawImage(((Goomba)entity).anim.getCurrentFrame(), entity.x, entity.y, entity.width, entity.height, null);
            }
            else if(entity instanceof UIPointer ){
                ((UIPointer) entity).render(g2);
            }else {
                g2.drawImage(entity.sprite, entity.x, entity.y, entity.width, entity.height, null);
            }
        }
        handler.getMario().drawMario(g2);
        if(MenuState.multiplayer) {
        	handler.getLuigi().drawLuigi(g2);
        }
        if(this.listener != null && MapBuilder.mapDone) {
            this.listener.render(g2);
            this.hand.render(g2);
            this.walls.render(g2);
        }
        g2.translate(camLocation.x, camLocation.y);
    }
    
    public void drawMapL(Graphics2D g2) {
    	handler.setIsInMapL(true);
    	   
         Point camLocationL = new Point((int)handler.getCameraL().getX(), (int)handler.getCameraL().getY());
         g2.translate(-camLocationL.x, -camLocationL.y);
         g2.drawImage(Images.backgrounds2[this.mapBackground], camLocationL.x, camLocationL.y, this.handler.getWidth(), this.handler.getHeight(),null);
         for (BaseStaticEntity block:blocksOnMap) {
             g2.drawImage(block.sprite,block.x,block.y,block.width,block.height,null);
         }
         for (BaseDynamicEntity entity:enemiesOnMap) {
             if(entity instanceof Item){
                 if(!((Item)entity).used){
                     g2.drawImage(entity.sprite, entity.x, entity.y, entity.width, entity.height, null);
                 }
             }else if(entity instanceof Goomba && !entity.ded){
                 g2.drawImage(((Goomba)entity).anim.getCurrentFrame(), entity.x, entity.y, entity.width, entity.height, null);
             }
             else if(entity instanceof UIPointer ){
                 ((UIPointer) entity).render(g2);
             }else {
                 g2.drawImage(entity.sprite, entity.x, entity.y, entity.width, entity.height, null);
             }
         }
         handler.getMario().drawMario(g2);
         handler.getLuigi().drawLuigi(g2);
         
         if(this.listener != null && MapBuilder.mapDone) {
        	 this.listener.render(g2);
        	 this.hand.render(g2);
        	 this.walls.render(g2);
         }
         g2.translate(camLocationL.x, camLocationL.y);
    }

    public ArrayList<BaseStaticEntity> getBlocksOnMap() {
        return blocksOnMap;
    }
    public ArrayList<BaseStaticEntity> getStarOnMap() {
        return StarOnMap;
    }
    public ArrayList<BaseStaticEntity> getBoundaryOnMap() {
        return boundaryOnMap;
    }

    public ArrayList<BaseDynamicEntity> getEnemiesOnMap() {
        return enemiesOnMap;
    }

    public double getBottomBorder() {
        return bottomBorder;
    }

    public UIListener getListener() {
        return this.listener;
    }
    public Background getHand() {
        return this.hand;
    }
    public Wall getWalls() {
        return this.walls;
    }

    public void reset() {
    }
}
