package assets;

import javax.swing.*;
import java.awt.*;
import components.SoccerField;

// Inner class for drawing the soccer field
public class SoccerFieldPanel extends JPanel {

    private SoccerField soccerField;
    private float displayScale = 1.0f;  // Scale factor to convert real-world meters to pixels
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (soccerField == null) return;
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Draw background
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, panelWidth, panelHeight);

        // Calculate scale to fit field in panel (preserve aspect ratio)
        float scale = Math.min(
            (float) panelWidth / soccerField.length,
            (float) panelHeight / soccerField.width
        );

        // Calculate field pixel size
        int fieldPixelWidth = Math.round(soccerField.length * scale);
        int fieldPixelHeight = Math.round(soccerField.width * scale);

        // Center the field
        int x0 = (panelWidth - fieldPixelWidth) / 2;
        int y0 = (panelHeight - fieldPixelHeight) / 2;

        // Draw field background
        g2.setColor(new Color(34, 139, 34));
        g2.fillRect(x0, y0, fieldPixelWidth, fieldPixelHeight);

        // Draw field border
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(x0, y0, fieldPixelWidth, fieldPixelHeight);

        // Center line
        g2.drawLine(x0 + fieldPixelWidth / 2, y0, x0 + fieldPixelWidth / 2, y0 + fieldPixelHeight);

        // Center circle
        int centerX = x0 + fieldPixelWidth / 2;
        int centerY = y0 + fieldPixelHeight / 2;
        int centerCircleRadius = Math.round(soccerField.centerCircleRadius * scale);
        g2.drawOval(centerX - centerCircleRadius, centerY - centerCircleRadius, 2 * centerCircleRadius, 2 * centerCircleRadius);

        // Penalty areas
        int penaltyAreaWidthPx = Math.round(soccerField.penaltyAreaLength * scale);
        int penaltyAreaHeightPx = Math.round(soccerField.penaltyAreaWidth * scale);
        // Left penalty area
        g2.drawRect(x0, centerY - penaltyAreaHeightPx / 2, penaltyAreaWidthPx, penaltyAreaHeightPx);
        // Right penalty area
        g2.drawRect(x0 + fieldPixelWidth - penaltyAreaWidthPx, centerY - penaltyAreaHeightPx / 2, penaltyAreaWidthPx, penaltyAreaHeightPx);

        // Goal area
        int goalAreaWidthPx = Math.round(soccerField.goalAreaLength * scale);
        int goalAreaHeightPx = Math.round(soccerField.goalAreaWidth * scale);
        // Left goal area
        g2.drawRect(x0, centerY - goalAreaHeightPx / 2, goalAreaWidthPx, goalAreaHeightPx);
        // Right goal area
        g2.drawRect(x0 + fieldPixelWidth - goalAreaWidthPx, centerY - goalAreaHeightPx / 2, goalAreaWidthPx, goalAreaHeightPx);

        // Penalty spot
        int penaltySpotDistanceFromGoalLine = Math.round(soccerField.penaltySpotDistanceFromGoalLine * scale);
        // Left penalty spot
        int leftPenaltySpotX = x0 + penaltySpotDistanceFromGoalLine;
        g2.drawLine(leftPenaltySpotX, centerY, leftPenaltySpotX, centerY);
        // Right penalty spot
        int rightPenaltySpotX = x0 + fieldPixelWidth - penaltySpotDistanceFromGoalLine;
        g2.drawLine(rightPenaltySpotX, centerY, rightPenaltySpotX, centerY);

        // Goals
        int goalWidthPx = Math.round(soccerField.goalWidth * scale);
        int goalLengthPx = Math.round(soccerField.goalLength * scale);
        // Left goal
        g2.fillRect(x0 - goalLengthPx, centerY - goalWidthPx / 2, goalLengthPx, goalWidthPx);
        // Right goal
        g2.fillRect(x0 + fieldPixelWidth, centerY - goalWidthPx / 2, goalLengthPx, goalWidthPx);
    }

    public void setSoccerField(SoccerField soccerField2) {
        this.soccerField = soccerField2;
    }
}
