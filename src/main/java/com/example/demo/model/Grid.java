package com.example.demo.model;
import java.util.List;

public class Grid {
    private final int width;
    private final int height;
    private final List<Position> blocked;

    public Grid(int width, int height, List<Position> blocked) {
        this.width = width;
        this.height = height;
        this.blocked = blocked;
    }

    public boolean isValid(Position position) {
        return position.getX() >= 0 && position.getX() < width &&
               position.getY() >= 0 && position.getY() < height &&
               !blocked.contains(position);
    }
}
