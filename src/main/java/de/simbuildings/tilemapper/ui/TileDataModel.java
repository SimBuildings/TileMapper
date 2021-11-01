package de.simbuildings.tilemapper.ui;

import de.simbuildings.tilemapper.image.ImageResolution;
import de.simbuildings.tilemapper.image.SquareImageResolution;
import de.simbuildings.tilemapper.tile.ImageSpliter;
import de.simbuildings.tilemapper.tile.TilePropertiesWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by SimBuildings on 01.11.21 at 12:14
 */
class TileDataModel {
    private final ImageSpliter spliter = new ImageSpliter();
    private TilePropertiesWriter propertiesWriter;
    private File imageFile;
    private String block;

    // TODO: implement validation logic and throw exceptions if image is wrong. Handle exceptions in Controller

    // TODO: isValid method to enable/disable Form
    public void setOriginalImage(File imageFile) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        if (!new ImageResolution(image).isPowerOfTwo())
            throw new IllegalArgumentException("wrong image size");
        this.imageFile = imageFile;
        spliter.setOriginalImage(image);
    }

    public void setTargetResolution(SquareImageResolution resolution) {
        spliter.setTargetResolution(resolution);
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public void split() {
        spliter.split();
    }

    public void export() {
        propertiesWriter = new TilePropertiesWriter(spliter.getTileGrid(), block);
        spliter.save(getDirectory());
        propertiesWriter.write(getDirectory());
    }

    public int getGridHeight() {
        return spliter.getTileGrid().getHeight();
    }

    public int getGridWidth() {
        return spliter.getTileGrid().getWidth();
    }

    public String getDirectory() {
        return imageFile.getParentFile().getAbsolutePath() + "/";
    }

    public String getFileName() {
        return imageFile.getName();
    }

    public ImageResolution getOriginalResolution() {
        return spliter.getOriginalResolution();
    }

}
