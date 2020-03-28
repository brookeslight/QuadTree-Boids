package main;

public class Circle {
	
	public float x;
	public float y;
	public float r;
	
	public Circle(float x, float y, float r) {
		this.x = x;
		this.y = y;
		this.r = r;
		if(r <= 0) {
			throw new Error("Undefined Circle");
		}
	}
	
	public boolean contains(float x1, float y1) {
		return (this.r > 0 && Math.hypot(x1 - this.x, y1 - this.y) <= this.r);
	}
	
	public boolean intersects(Rectangle r) {
		return (this.r > 0 && r.w > 0 && r.h > 0 && Math.hypot(this.x - Math.max(r.x, Math.min(r.x + r.w, this.x)), this.y - Math.max(r.y, Math.min(r.y + r.h, this.y))) <= this.r);
	}

}