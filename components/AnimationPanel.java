package components;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class AnimationPanel extends JPanel {
    private final List<Animatable> objects = new ArrayList<>();
    private final Timer timer;

    public AnimationPanel() {
        timer = new Timer(16, e -> {
            for (Animatable obj : objects) {
                obj.update();
            }
            repaint();
        });
        timer.start();
    }

    public void addObject(Animatable obj) {
        objects.add(obj);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Animatable obj : objects) {
            obj.draw(g);
        }
    }
} 