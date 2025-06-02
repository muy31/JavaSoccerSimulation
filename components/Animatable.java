package components;

import java.awt.Graphics;

public interface Animatable {
    float getX();
    float getY();
    void draw(Graphics g);
    void update();
} 