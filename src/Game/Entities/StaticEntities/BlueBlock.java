package Game.Entities.StaticEntities;

import Main.Handler;
import Resources.Images;

public class BlueBlock extends BaseStaticEntity {

    public BlueBlock(int x, int y, int width, int height, Handler handler) {
        super(x, y, width, height,handler, Images.blueBlock);
    }

}
