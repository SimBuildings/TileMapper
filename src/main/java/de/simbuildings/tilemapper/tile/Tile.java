package de.simbuildings.tilemapper.tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by SimBuildings on 10.10.21 at 17:20
 */
public class Tile {
    private final BufferedImage subImage;
    private final int tileID;

    public Tile(BufferedImage subImage, int tileID) {
        this.subImage = subImage;
        this.tileID = tileID;
    }

    public int getTileID() {
        return tileID;
    }

    public void export(String destDir) {
        File outputFile = new File(destDir + tileID + ".png");
        try {
            ImageIO.write(subImage, "png", outputFile);
            System.out.println("exported " + tileID + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getSubImage() {
        return subImage;
    }
}
