package components;

import java.awt.Color;
import java.awt.Graphics;
import assets.Vector3;

public class SoccerBall implements Animatable {
    private float mass = 0.43f;
    private float radius = 0.11f;
    private boolean inContactWithGround = true;

    // Soccer ball position
    private Vector3 position = new Vector3(0f, 0f, radius + 0.01f);
    private Vector3 velocity = new Vector3();
    private Vector3 acceleration = new Vector3(); // Changes according to forces applied

    // Soccer ball spin
    private Vector3 angularVelocity = new Vector3();;
    private Vector3 angularAcceleration = new Vector3();;

    public SoccerBall() {}

    // Set functions
    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public void zeroVelocity() {
        this.velocity.x = 0;
        this.velocity.y = 0;
        this.velocity.z = 0;
    }

    public void zeroAcceleration() {
        this.acceleration.x = 0;
        this.acceleration.y = 0;
        this.acceleration.z = 0;
    }

    // Natural update functions
    public void updatePosition() {
        Vector3 displacementInTime = this.velocity.multiply((float) Constants.TimeUpdate / 1000f);
        this.position = this.position.add(displacementInTime);
    }

    public void updateVelocity() {
        this.addVelocity(this.acceleration);
    }

    public void updateAngularVelocity() {
        Vector3 angularVelDeltaInTime = this.angularAcceleration.multiply((float) Constants.TimeUpdate / 1000f);
        this.angularVelocity = this.angularVelocity.add(angularVelDeltaInTime);
    }

    // Direct push functions (the idea is adding absolute realtime units)
    private void addVelocity(Vector3 v) {
        this.velocity = this.velocity.add(v.multiply((float) Constants.TimeUpdate / 1000f));
    }

    public void addAngularVelocity(float w) {
        this.angularVelocity = this.angularVelocity.add(new Vector3(0f, 0f, w).multiply((float) Constants.TimeUpdate / 1000f));
    }

    public void applyForce(Vector3 force) {
        Vector3 forceInTime = force.divide(this.mass).multiply((float) Constants.TimeUpdate / 1000f);
        this.acceleration = this.acceleration.add(forceInTime);
    }

    // Real seconds of time, prevents continuous application of force (mimicking impact rather than force)
    public void applyImpulse(Vector3 force, float impactTime) {
        Vector3 velocityChangeInTime = force.divide(this.mass).multiply((float) Constants.TimeUpdate * impactTime / 1000f);
        this.velocity = this.velocity.add(velocityChangeInTime);
    }

    public void applyTorque(Vector3 force, Vector3 position) {
        // Ensure position is on the ball
        // Get cross product of (position vector - ball position vector) x force vector
        // Divide torque by moment of inertia for ball = 2/5*M*R^2
        // Change angular acceleration appropriately
    }

    public void applyDrag() {
        // Drag force is 
        // 0.5 * density * C_D * A (cross-sectional) * v^2
        Vector3 forceDirection = this.velocity.multiply(-1f).normalize();
        float crossArea = (float) (Math.PI * Math.pow(this.radius, 2));
        float forceMagnitude = 0.5f * Constants.AIRDENSITY * Constants.DRAG_CONSTANT * crossArea * velocity.dotProduct(velocity);
        Vector3 dragForce = forceDirection.multiply(forceMagnitude);
        System.out.printf("Drag Force: (%.2f, %.2f, %.2f)\n", dragForce.x, dragForce.y, dragForce.z);
        this.applyForce(dragForce);   
    }

    public void applyFriction() {
        if (inContactWithGround) {
            Vector3 forceDirection = this.velocity.multiply(-1f).normalize();
            // Ideally take the cross-product of the resultant force x the normal of the surface, but for now just ignore other and use gravity
            float forceMagnitude = Constants.GRASS_FRICTION_CONSTANT * mass * Constants.GRAVITY.magnitude();
            Vector3 frictionForce = forceDirection.multiply(forceMagnitude);
            System.out.printf("Friction Force: (%.2f, %.2f, %.2f)\n", frictionForce.x, frictionForce.y, frictionForce.z);
            this.applyForce(frictionForce);
        }
    }

    
    /*
    public void applyWind(float wind) {
    }
    */

    public void applyGravity(float gravity) {
        applyForce(Constants.GRAVITY);
    }

    @Override
    public float getX() {
        return this.position.x;
    }

    @Override
    public float getY() {
        return this.position.y;
    }

    @Override
    public void update() {
        applyDrag();
        applyFriction();
        updateVelocity();
        updateAngularVelocity();
        updatePosition();
        System.out.printf("Position: (%.2f, %.2f, %.2f)\n", position.x, position.y, position.z);
        System.out.printf("Velocity: (%.2f, %.2f, %.2f)\n", velocity.x, velocity.y, velocity.z);
        System.out.printf("Acceleration: (%.2f, %.2f, %.2f)\n", acceleration.x, acceleration.y, acceleration.z);
        zeroAcceleration(); // For new resolution of new forces in next frame
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