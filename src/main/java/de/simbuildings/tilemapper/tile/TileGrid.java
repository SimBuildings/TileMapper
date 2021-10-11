package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.image.ImageResolution;
import de.simbuildings.tilemapper.image.SquareImageResolution;

/**
 * Created by SimBuildings on 11.10.21 at 17:18
 */
class TileGrid {
    private final int height;
    private final int width;

    public TileGrid(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public TileGrid(ImageResolution originalResolution, SquareImageResolution targetResoltion) {
        this.height = originalResolution.getHeight() / targetResoltion.getHeight();
        this.width = originalResolution.getHeight() / targetResoltion.getHeight();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getTileAmout() {
        return width * height;
    }
}
