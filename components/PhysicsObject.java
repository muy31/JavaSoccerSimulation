package components;

import java.util.Queue;
import java.util.ArrayDeque;

import assets.Vector3;

public class PhysicsObject {

    public static float timeStepPerUpdate = 0.05f; // time step per physics resolution
    public static int updatesPerFrame = 2;

    // Object attributes
    private float mass;
    private boolean inContactWithGround = true;

    // Position
    private Vector3 position = new Vector3(0f, 0f, 0f);
    private Vector3 velocity = new Vector3();
    private Vector3 acceleration = new Vector3(); // Changes according to forces applied

    // Spin
    private Vector3 angularVelocity = new Vector3();
    private Vector3 angularAcceleration = new Vector3();

    private Queue<Vector3> forcesThisUpdate;

    // Constructor
    public PhysicsObject() {
        forcesThisUpdate = new ArrayDeque<Vector3>();
        position = new Vector3(0f, 0f, 0f);
        velocity = new Vector3();
        acceleration = new Vector3();
        angularVelocity = new Vector3();
        angularAcceleration = new Vector3();
    }

    public PhysicsObject(float x, float y, float z) {
        forcesThisUpdate = new ArrayDeque<Vector3>();
        position = new Vector3(0f, 0f, 0f);
        velocity = new Vector3();
        acceleration = new Vector3();
        angularVelocity = new Vector3();
        angularAcceleration = new Vector3();
        this.setPosition(x, y);
    }

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
        Vector3 displacementInTime = this.velocity.multiply(timeStepPerUpdate);
        this.position = this.position.add(displacementInTime);
    }

    public void updateVelocity() {
        Vector3 velocityChangeInTime = this.acceleration.multiply(timeStepPerUpdate);
        this.velocity = this.velocity.add(velocityChangeInTime);
    }

    public void updateAngularVelocity() {
        Vector3 angularVelDeltaInTime = this.angularAcceleration.multiply(timeStepPerUpdate);
        this.angularVelocity = this.angularVelocity.add(angularVelDeltaInTime);
    }

    // Direct applications

    // Instead of applying forces, should add to a queue that is iterated per update
    public void applyForce(Vector3 force) {
        forcesThisUpdate.add(force);
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
        float crossArea = 1f;
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


    
}
