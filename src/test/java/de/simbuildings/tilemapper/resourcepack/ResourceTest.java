package de.simbuildings.tilemapper.resourcepack;

import de.simbuildings.tilemapper.variations.model.ModelFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceTest {

    @Test
    @DisplayName("Should get texture directory for block")
    void shouldReturnTextureDirectoryForBlock() {
        // given
        Resource resource = createSandstoneResource();

        // when
        final Path texturePath = resource.textureDirectory();

        // then
        assertThat(texturePath)
                .isDirectory()
                .endsWith(
                        Paths.get("assets", "minecraft", "textures", "block", "sandstone")
                );
    }

    @Test
    @DisplayName("Should get model directory for block")
    void shouldReturnModelDirectory_WhenBlock() {
        // given
        Resource resource = createSandstoneResource();

        // when
        final Path modelPath = resource.modelFile(ModelFile.BLOCK);

        // then
        assertThat(modelPath)
                .isDirectory()
                .endsWith(
                        Paths.get("assets", "minecraft", "models", "block", "sandstone", "sandstone.json")
                );
    }

    @Test
    @DisplayName("Should get model directory for stairs")
    void shouldReturnModelDirectory_WhenStairs() {
        // given
        Resource resource = createSandstoneResource();

        // when
        final Path modelPath = resource.modelFile(ModelFile.STAIRS);

        // then
        assertThat(modelPath)
                .isDirectory()
                .endsWith(
                        Paths.get("assets", "minecraft", "models", "block", "sandstone", "stairs", "sandstone_stairs.json")
                );
    }

    private Resource createSandstoneResource() {
        return new Resource("sandstone");
    }
}