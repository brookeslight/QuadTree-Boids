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
	
	public boolean intersects(Circle c) {
	    return (this.r > 0 && c.r > 0 && Math.hypot(c.x - this.x, c.y - this.y) <= (this.r + c.r));
	}
	
	public boolean intersects(Bounds b) {
		return (this.r > 0 && b.w > 0 && b.h > 0 && Math.hypot(this.x - Math.max(b.x, Math.min(b.x + b.w, this.x)), this.y - Math.max(b.y, Math.min(b.y + b.h, this.y))) <= this.r);
	}

}