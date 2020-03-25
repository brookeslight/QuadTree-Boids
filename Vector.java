package main;

public class Vector {
	public float x;
	public float y;
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector(float x2, float x1, float y2, float y1) {
		this.x = x2-x1;
		this.y = y2-y1;
	}
	
	public float getMagnitude() {
		return (float) Math.hypot(this.x, this.y);
	}
	
	public void normalize() {
		float mag = this.getMagnitude();
		this.x /= mag;
		this.y /= mag;
	}
	
	public void scale(float s) {
		this.x *= s;
		this.y *= s;
	}
	
	public void divide(float s) {
		this.x /= s;
		this.y /= s;
	}
	
	public void add(Vector v) {
		this.x += v.x;
		this.y += v.y;
	}

}