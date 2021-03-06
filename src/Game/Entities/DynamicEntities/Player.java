package Game.Entities.DynamicEntities;

import Game.Entities.EntityBase;
import Game.Entities.StaticEntities.BaseStaticEntity;
import Game.GameStates.State;
import Main.GameSetUp;
import Main.Handler;
import Resources.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends BaseDynamicEntity {

	protected double velX,velY;

	public String facing = "Left";
	public boolean moving = false;
	public Animation playerSmallLeftAnimation,playerSmallRightAnimation,playerBigLeftWalkAnimation,playerBigRightWalkAnimation,playerBigLeftRunAnimation,playerBigRightRunAnimation;
	public boolean falling = true, jumping = false,isBig=false,running = false,changeDirrection=false;
	public double gravityAcc = 0.38;
	int changeDirectionCounter=0;

	public Player(int x, int y, int width, int height, Handler handler, BufferedImage sprite,Animation PSLA,Animation PSRA,Animation PBLWA,Animation PBRWA,Animation PBLRA,Animation PBRRA) {
		super(x, y, width, height, handler, sprite);
		playerBigLeftRunAnimation=PBLRA;
		playerBigLeftWalkAnimation=PBLWA;
		playerBigRightRunAnimation=PBRRA;
		playerBigRightWalkAnimation=PBRWA;
		playerSmallLeftAnimation=PSLA;
		playerSmallRightAnimation=PSRA;
	}

	@Override
	public void tick(){

		if (changeDirrection) {
			changeDirectionCounter++;
		}
		if(changeDirectionCounter>=10){
			changeDirrection=false;
			changeDirectionCounter=0;
		}

		checkBottomCollisions();
		checkMarioHorizontalCollision();
		checkTopCollisions();
		checkItemCollision();
		if(!isBig) {
			if (facing.equals("Left") && moving) {
				playerSmallLeftAnimation.tick();
			} else if (facing.equals("Right") && moving) {
				playerSmallRightAnimation.tick();
			}
		}else{
			if (facing.equals("Left") && moving && !running) {
				playerBigLeftWalkAnimation.tick();
			} else if (facing.equals("Left") && moving && running) {
				playerBigLeftRunAnimation.tick();
			} else if (facing.equals("Right") && moving && !running) {
				playerBigRightWalkAnimation.tick();
			} else if (facing.equals("Right") && moving && running) {
				playerBigRightRunAnimation.tick();
			}
		}
	}

	private void checkItemCollision() {

		for (BaseDynamicEntity entity : handler.getMap().getEnemiesOnMap()) {
			if (entity != null && getBounds().intersects(entity.getBounds()) && entity instanceof Item && !isBig) {
				isBig = true;
				this.y -= 8;
				this.height += 8;
				setDimension(new Dimension(width, this.height));
				((Item) entity).used = true;
				entity.y = -100000;
			}
		}
	}


	public void checkBottomCollisions() {
		Player mario = this;
		Player luigi=this;
		ArrayList<BaseStaticEntity> bricks = handler.getMap().getBlocksOnMap();
		ArrayList<BaseDynamicEntity> enemies =  handler.getMap().getEnemiesOnMap();
		ArrayList<BaseStaticEntity> boundary =  handler.getMap().getBoundaryOnMap();
		ArrayList<BaseStaticEntity> star =  handler.getMap().getStarOnMap();
		

		Rectangle marioBottomBounds =getBottomBounds();
		Rectangle marioTopBounds =getTopBounds();
		Rectangle luigiBottomBounds =luigi.getBottomBounds();
		Rectangle luigiTopBounds =luigi.getTopBounds();

		if (!mario.jumping) {
			falling = true;
		}
		boolean marioDies=false;
		boolean luigiDies=false;
		for(BaseStaticEntity bound : boundary){
			Rectangle boundTopBounds = bound.getTopBounds();
			Rectangle boundBottomBounds = bound.getBottomBounds();
		
			
			if (marioBottomBounds.intersects(boundTopBounds)&&GameSetUp.created2&&this instanceof Mario
				|| marioTopBounds.intersects(boundBottomBounds)&&GameSetUp.created2&&this instanceof Mario) {
				marioDies = true;
				State.setState(handler.getGame().luigiWinState);
				break;
			}
			if (marioBottomBounds.intersects(boundTopBounds)&&this instanceof Luigi || 
					marioTopBounds.intersects(boundBottomBounds)&&this instanceof Luigi) {
				marioDies = true;
				State.setState(handler.getGame().marioWinState);
				break;
			}
			if (marioBottomBounds.intersects(boundTopBounds) || marioTopBounds.intersects(boundBottomBounds)) {
				marioDies = true;
				State.setState(handler.getGame().gameOverState);
				break;
			}
			
		}
		for(BaseStaticEntity winStar : star){
			Rectangle StarTopBounds = winStar.getTopBounds();
			if (luigiBottomBounds.intersects(StarTopBounds)&&this instanceof Luigi) {
				 luigiDies = true;
				State.setState(handler.getGame().luigiWinState);
				break;
			}
			
		
			
			if (marioBottomBounds.intersects(StarTopBounds)) {
				marioDies = true;
				State.setState(handler.getGame().marioWinState);
				break;
			}
			
			
		}
		

		for (BaseStaticEntity brick : bricks) {
			Rectangle brickTopBounds = brick.getTopBounds();
			if (marioBottomBounds.intersects(brickTopBounds)) {
				mario.setY(brick.getY() - mario.getDimension().height + 1);
				falling = false;
				velY=0;
				
			}
		}

		for (BaseDynamicEntity enemy : enemies) {
			Rectangle enemyTopBounds = enemy.getTopBounds();
			if (marioBottomBounds.intersects(enemyTopBounds) && !(enemy instanceof Item)) {
				if(!enemy.ded) {
					handler.getGame().getMusicHandler().playStomp();
				}
				enemy.kill();
				falling=false;
				velY=0;

			}
		
			if(marioDies) {
				handler.getMap().reset();
			}
			if(luigiDies) {
				handler.getMap().reset();
			}}
	}

	public void checkTopCollisions() {
		Player mario = this;
		ArrayList<BaseStaticEntity> bricks = handler.getMap().getBlocksOnMap();
		ArrayList<BaseStaticEntity> star =  handler.getMap().getStarOnMap();

		Rectangle marioTopBounds = mario.getTopBounds();
		for (BaseStaticEntity brick : bricks) {
			Rectangle brickBottomBounds = brick.getBottomBounds();
			if (marioTopBounds.intersects(brickBottomBounds)) {
				velY=0;
				mario.setY(brick.getY() + brick.height);
			}
			
			
		}
		for (BaseStaticEntity winstar : star) {
			Rectangle starBottomBounds = winstar.getBottomBounds();
			if (marioTopBounds.intersects(starBottomBounds)&&this instanceof Luigi) {
				velY=0;
				mario.setY(winstar.getY() + winstar.height);
				State.setState(handler.getGame().luigiWinState);
				break;
			}
			if (marioTopBounds.intersects(starBottomBounds)) {
				velY=0;
				mario.setY(winstar.getY() + winstar.height);
				State.setState(handler.getGame().marioWinState);
				break;
			}
			
			
		}
	}

	public void checkMarioHorizontalCollision(){
		Player mario = this;
		
		ArrayList<BaseStaticEntity> bricks = handler.getMap().getBlocksOnMap();
		ArrayList<BaseDynamicEntity> enemies = handler.getMap().getEnemiesOnMap();
		ArrayList<BaseStaticEntity> star =  handler.getMap().getStarOnMap();

		boolean marioDies = false;
		boolean toRight = moving && facing.equals("Right");
		boolean toLeft = moving && facing.equals("Left");

		Rectangle marioBounds = toRight ? mario.getRightBounds() : mario.getLeftBounds();
		Rectangle marioBounds1 = toLeft ? mario.getLeftBounds() : mario.getRightBounds();
		

		for (BaseStaticEntity brick : bricks) {
			Rectangle brickBounds = !toRight ? brick.getRightBounds() : brick.getLeftBounds();
			if (marioBounds.intersects(brickBounds)) {
				velX=0;
				if(toRight)
					mario.setX(brick.getX() - mario.getDimension().width);
				else
					mario.setX(brick.getX() + brick.getDimension().width);
			}
		}
		for (BaseStaticEntity winstar : star) {
			Rectangle winstarBounds = !toRight ? winstar.getRightBounds() : winstar.getLeftBounds();
			if ((marioBounds.intersects(winstarBounds) || marioBounds1.intersects(winstarBounds)) && (this instanceof Luigi)) {
				marioDies = true;
				State.setState(handler.getGame().luigiWinState);
				break;
			}
			if ((marioBounds.intersects(winstarBounds) || marioBounds1.intersects(winstarBounds)) ) {
				marioDies = true;
				State.setState(handler.getGame().marioWinState);
				break;
			}
		}

		for(BaseDynamicEntity enemy : enemies){
			Rectangle enemyBounds = !toRight ? enemy.getRightBounds() : enemy.getLeftBounds();
			Rectangle enemyBounds1 = !toLeft ? enemy.getLeftBounds() : enemy.getRightBounds();
			
			
			if ((marioBounds.intersects(enemyBounds) || marioBounds1.intersects(enemyBounds1)) && !(enemy instanceof Mushroom)&&this instanceof Luigi) {
				marioDies = true;
				State.setState(handler.getGame().marioWinState);
				break;
			}
			if ((marioBounds.intersects(enemyBounds) || marioBounds1.intersects(enemyBounds1)) && !(enemy instanceof Mushroom)&&GameSetUp.created2) {
				marioDies = true;
				State.setState(handler.getGame().luigiWinState);
				break;
			}
			if ((marioBounds.intersects(enemyBounds) || marioBounds1.intersects(enemyBounds1)) && !(enemy instanceof Mushroom)) {
				marioDies = true;
				State.setState(handler.getGame().gameOverState);
				break;
			}
			
		}
		
		

		if(marioDies) {
			handler.getMap().reset();
		}
	}

	public void jump() {

		if(!jumping && !falling){
			jumping=true;
			velY=10;
			handler.getGame().getMusicHandler().playJump();
		}
	}

	public double getVelX() {
		return velX;
	}
	public double getVelY() {
		return velY;
	}


}
