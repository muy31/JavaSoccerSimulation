package components;

public class PlayerAgent {
    
    // Player agent dimensions
    private float x;
    private float y;
    private float radius;

    // Player agent attributes
    private float maxSpeed;
    private float turnSpeed;
    private float forwardAcceleration;
    private float agility;
    private float ballControl;

    private Team team;

    public PlayerAgent(Team t) {
        this.team = t;
        this.radius = 0.15f;
    }

    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

}
