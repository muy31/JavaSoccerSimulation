package components;

import assets.SoccerFieldPanel;
import javax.swing.*;

public class SoccerField {

    // Field dimensions (in meters)
    public float length;
    public float width;

    // Goal dimensions (in meters)
    public float goalLength;
    public float goalWidth;
    public float goalHeight;
    public float goalPostRadius;

    // Penalty area dimensions (in meters)
    public float penaltyAreaLength;
    public float penaltyAreaWidth;
    public float centerCircleRadius;
    public float cornerRadius;

    // Goal area dimensions (in meters)
    public float goalAreaLength;
    public float goalAreaWidth;

    // Penalty spot dimensions (in meters)
    public float penaltySpotDistanceFromGoalLine;
    public float penaltyArcRadiusFromPenaltySpot;

    public SoccerField() {
        this.length = 110;
        this.width = 75;
        this.goalWidth = 7;
        this.goalLength = 2;
        this.goalPostRadius = 0.1f;
        this.penaltyAreaLength = 18f;
        this.penaltyAreaWidth = 47f;
        this.centerCircleRadius = 9.15f;
        this.cornerRadius = 0.2f;
        this.penaltySpotDistanceFromGoalLine = 11f;
        this.penaltyArcRadiusFromPenaltySpot = 9.15f;
        this.goalAreaLength = 5.5f;
        this.goalAreaWidth = 18f;
    }

    public void DisplaySoccerField() {
        SoccerFieldPanel panel = new SoccerFieldPanel();
        panel.setSoccerField(this);

        JFrame frame = new JFrame("Soccer Field");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
