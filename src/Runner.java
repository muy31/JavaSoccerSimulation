import components.SoccerField;
import components.SoccerBall;
import assets.SoccerFieldAnimationPanel;
import javax.swing.*;
import assets.Vector3;

public class Runner {
    public static void main(String[] args) {
        SoccerField field = new SoccerField();
        SoccerBall ball = new SoccerBall();
        ball.setPosition(0, 0);
        ball.applyForce(new Vector3(0.1f, 0, 0));
        SoccerFieldAnimationPanel panel = new SoccerFieldAnimationPanel();
        panel.setSoccerField(field);
        panel.addObject(ball);
        JFrame frame = new JFrame("Soccer Field Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}