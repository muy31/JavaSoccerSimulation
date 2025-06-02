package assets;

public class Vector3 {
    public float x;
    public float y;
    public float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    
    public Vector3 add(Vector3 v) {
        return new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    public Vector3 subtract(Vector3 v) {
        return new Vector3(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    public Vector3 multiply(float scalar) {
        return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public Vector3 divide(float scalar) {
        return new Vector3(this.x / scalar, this.y / scalar, this.z / scalar);
    }

    public float magnitude() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public Vector3 normalize() {
        float magnitude = magnitude();
        return new Vector3(this.x / magnitude, this.y / magnitude, this.z / magnitude);
    }

    public float dotProduct(Vector3 v) {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    public Vector3 crossProduct(Vector3 v) {
        return new Vector3(this.y * v.z - this.z * v.y, this.z * v.x - this.x * v.z, this.x * v.y - this.y * v.x);
    }

    public float angleBetween(Vector3 v) {
        return (float) Math.acos(this.dotProduct(v) / (this.magnitude() * v.magnitude()));
    }

    public Vector3 project(Vector3 v) {
        return v.multiply(this.dotProduct(v) / v.dotProduct(v));
    }

    public Vector3 reject(Vector3 v) {
        return this.subtract(this.project(v));
    }
}
