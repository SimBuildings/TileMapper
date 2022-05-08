package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import de.simbuildings.tilemapper.resourcepack.Resource;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Variant implements Comparable<Variant> {
    private final String model;

    private final int weight;
    private final boolean uvLock;
    private final int x;
    private final int y;

    private Variant(Builder builder) {
        this.model = builder.resource.modelLocation(builder.modelType);

        this.weight = builder.weight;
        this.uvLock = builder.uvLock;
        this.x = builder.x;
        this.y = builder.y;
    }

    @JsonGetter
    public int weight() {
        return weight;
    }

    @JsonGetter("uvlock")
    public boolean uvLock() {
        return uvLock;
    }

    @JsonGetter("x")
    public int rotationX() {
        return x;
    }

    @JsonGetter("y")
    public int rotationY() {
        return y;
    }

    @JsonGetter
    public String model() {
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variant variant = (Variant) o;
        return weight == variant.weight && x == variant.x && y == variant.y && model.equals(variant.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, weight, x, y);
    }

    @Override
    public int compareTo(Variant other) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.model, other.model);
    }

    @Override
    public String toString() {
        return "Variant{" +
               "model='" + model + '\'' +
               ", weight=" + weight +
               ", uvLock=" + uvLock +
               ", x=" + x +
               ", y=" + y +
               '}';
    }

    public static class Builder {
        private final Resource resource;
        private ModelType modelType;
        private int weight;
        private boolean uvLock;
        private int x;

        private int y;

        public Builder(Resource resource) {
            this.modelType = ModelType.BLOCK;
            this.resource = resource;
        }

        public Builder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public Builder modelType(ModelType type) {
            this.modelType = type;
            return this;
        }

        public Builder uvLock(boolean lock) {
            this.uvLock = lock;
            return this;
        }

        public Builder rotationX(int x) {
            if (isValidRotation(x)) {
                throw new IllegalArgumentException("rotation must be in increments of 90");
            }
            this.x = x;
            return this;
        }

        public Builder rotationY(int y) {
            if (isValidRotation(y)) {
                throw new IllegalArgumentException("rotation must be in increments of 90");
            }
            this.y = y;
            return this;
        }

        public Variant build() {
            return new Variant(this);
        }

        private boolean isValidRotation(int rotation) {
            return rotation % 90 != 0;
        }
    }
}