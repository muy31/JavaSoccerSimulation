package assets;

import components.Animatable;
import components.Constants;
import components.SoccerField;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class SoccerFieldAnimationPanel extends SoccerFieldPanel {
    private final List<Animatable> objects = new ArrayList<>();
    private final Timer timer;
    private float scale = 1.0f;
    private int x0 = 0, y0 = 0;

    public SoccerFieldAnimationPanel() {
        timer = new Timer(Constants.TimeUpdate, e -> {
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
        SoccerField soccerField = getSoccerField();
        if (soccerField == null) return;
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        scale = Math.min(
            (float) panelWidth / soccerField.length,
            (float) panelHeight / soccerField.width
        );
        int fieldPixelWidth = Math.round(soccerField.length * scale);
        int fieldPixelHeight = Math.round(soccerField.width * scale);
        x0 = (panelWidth - fieldPixelWidth) / 2;
        y0 = (panelHeight - fieldPixelHeight) / 2;
        // Draw animated objects
        for (Animatable obj : objects) {
            drawAnimatable(g, obj);
        }
    }

    private void drawAnimatable(Graphics g, Animatable obj) {
        // Convert meters to pixels and center
        int px = x0 + Math.round(obj.getX() * scale);
        int py = y0 + Math.round(obj.getY() * scale);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(px, py);
        obj.draw(g2);
        g2.dispose();
    }

    public SoccerField getSoccerField() {
        // Access the protected soccerField field from SoccerFieldPanel
        try {
            java.lang.reflect.Field f = SoccerFieldPanel.class.getDeclaredField("soccerField");
            f.setAccessible(true);
            return (SoccerField) f.get(this);
        } catch (Exception e) {
            return null;
        }
    }
} 