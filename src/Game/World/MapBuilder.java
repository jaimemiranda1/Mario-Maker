package Game.World;

import java.awt.Color;
import java.awt.image.BufferedImage;

import Game.Entities.DynamicEntities.BaseDynamicEntity;
import Game.Entities.DynamicEntities.Dino;
import Game.Entities.DynamicEntities.Goomba;
import Game.Entities.DynamicEntities.Luigi;
import Game.Entities.DynamicEntities.Mario;
import Game.Entities.DynamicEntities.Mushroom;
import Game.Entities.StaticEntities.BaseStaticEntity;
import Game.Entities.StaticEntities.BlueBlock;
import Game.Entities.StaticEntities.BoundBlock;
import Game.Entities.StaticEntities.BreakBlock;
import Game.Entities.StaticEntities.GoldenBlock;
import Game.Entities.StaticEntities.MisteryBlock;
import Game.Entities.StaticEntities.Star;
import Game.Entities.StaticEntities.SurfaceBlock;
import Main.Handler;
import Resources.Images;

public class MapBuilder {

	public static int pixelMultiplier = 48;
	public static int boundBlock = new Color(0,0,0).getRGB();
	public static int mario = new Color(255,0,0).getRGB();
	public static int luigi = new Color(0,102,0).getRGB();
	public static int surfaceBlock = new Color(255,106,0).getRGB();
	public static int breakBlock = new Color(0,38,255).getRGB();
	public static int misteryBlock = new Color(255,216,0).getRGB();
	public static int goldenBlock = new Color(0,255,120).getRGB();
	public static int blueBlock = new Color(255,0,167).getRGB();
	public static int mushroom = new Color(178,0,255).getRGB();
	public static int goomba = new Color(167,15,1).getRGB();
	public static int dino = new Color(200,180,50).getRGB();
	public static int star = new Color(255,255,51).getRGB();
	public static boolean mapDone = false;

	public static Map createMap(BufferedImage mapImage, Handler handler){
		Images.currentImage = mapImage;
		Map mapInCreation = new Map(handler);
		for (int i = 0; i < mapImage.getWidth(); i++) {
			for (int j = 0; j < mapImage.getHeight(); j++) {
				int currentPixel = mapImage.getRGB(i, j);
				int xPos = i*pixelMultiplier;
				int yPos = j*pixelMultiplier;
				if(currentPixel == boundBlock){
					BaseStaticEntity BoundBlock = new BoundBlock(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(BoundBlock);
				}else if(currentPixel == mario){
					BaseDynamicEntity Mario = new Mario(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addEnemy(Mario);
				}else if(currentPixel == surfaceBlock){
					BaseStaticEntity SurfaceBlock = new SurfaceBlock(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(SurfaceBlock);
				}else if(currentPixel == breakBlock){
					BaseStaticEntity BreakBlock = new BreakBlock(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(BreakBlock);
				}else if(currentPixel == misteryBlock){
					BaseStaticEntity MisteryBlock = new MisteryBlock(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(MisteryBlock);
				}else if(currentPixel == goldenBlock){
					BaseStaticEntity GoldenBlock = new GoldenBlock(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(GoldenBlock);
				}else if(currentPixel == blueBlock){
					BaseStaticEntity BlueBlock = new BlueBlock(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(BlueBlock);
				}else if(currentPixel == mushroom){
					BaseDynamicEntity Mushroom = new Mushroom(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addEnemy(Mushroom);
				}else if(currentPixel == goomba){
					BaseDynamicEntity Goomba = new Goomba(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addEnemy(Goomba);
				}else if(currentPixel == dino){
					BaseDynamicEntity Dino = new Dino(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addEnemy(Dino);
				}else if(currentPixel == luigi){
					BaseDynamicEntity Luigi = new Luigi(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addEnemy(Luigi);
				}else if(currentPixel == star){
					BaseStaticEntity Star = new Star(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(Star);
				}

			}
		}
		if(mapDone) {
			Images.makeMap(50, pixelMultiplier, mapImage.getWidth(), 100, mapInCreation, handler);
			for(int i = 96; i < 101; i++) {
				mapInCreation.addBlock(new BreakBlock(49*pixelMultiplier, i*pixelMultiplier,48,48,handler));
				mapInCreation.addBlock(new BreakBlock(54*pixelMultiplier, i*pixelMultiplier,48,48,handler));
			}
		}
		return mapInCreation;
	}

}
