package Game.Entities.StaticEntities;

import Main.Handler;
import Resources.Images;

public class Star extends BaseStaticEntity {

    public Star(int x, int y, int width, int height, Handler handler) {
        super(x, y, width, height,handler, Images.winStar);
    }

}
