package components;

import java.awt.Color;
import java.awt.Graphics;
import assets.Vector3;

public class SoccerBall implements Animatable {
    private float mass = 0.43f;
    private float radius = 0.11f;

    // Soccer ball position
    private Vector3 position = new Vector3(0f, 0f, radius + 0.01f);
    private Vector3 velocity = new Vector3();
    private Vector3 acceleration = new Vector3(); // Changes according to forces applied

    // Soccer ball spin
    private float spin;
    private float angularVelocity;
    private float angularAcceleration;

    // Update time
    private boolean animate = true; 
    private float updateTime;

    public SoccerBall() {
        this.spin = 0; // degrees per second
        this.angularVelocity = 0;
        this.angularAcceleration = 0;
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public void addVelocity(Vector3 v) {
        this.velocity = this.velocity.add(v);
    }

    public void addAngularVelocity(float w) {
        this.angularVelocity += w;
    }

    public void updatePosition() {
        this.position = this.position.add(this.velocity);
    }

    public void updateSpin() {
        this.spin += this.angularVelocity;
    }

    public void updateAngularVelocity() {
        this.angularVelocity += this.angularAcceleration;
    }

    public void applyForce(Vector3 force) {
        this.angularAcceleration += force / this.mass;
    }

    public void applyTorque(float torque) {
        this.angularAcceleration += torque / this.mass;
    }

    public void applyFriction(float friction) {
        this.angularAcceleration -= friction / this.mass;
    }

    public void applyDrag(float drag) {
        this.angularAcceleration -= drag / this.mass;
    }

    public void applyGravity(float gravity) {
        this.angularAcceleration -= gravity / this.mass;
    }

    public void applyWind(float wind) {
        this.angularAcceleration -= wind / this.mass;
    }

    @Override
    public float getX() {
        return xPos;
    }

    @Override
    public float getY() {
        return yPos;
    }

    @Override
    public void update() {
        updatePosition();
        updateSpin();
        updateAngularVelocity();
    }

    @Override
    public void draw(Graphics g) {
        int diameter = (int) (radius * 2 * 100); // Default scale, panel will handle actual scaling
        g.setColor(Color.WHITE);
        g.fillOval(-diameter/2, -diameter/2, diameter, diameter);
        g.setColor(Color.BLACK);
        g.drawOval(-diameter/2, -diameter/2, diameter, diameter);
    }
}