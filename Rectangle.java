package main;

public class Rectangle {
	
	public float x;
	public float y;
	public float w;
	public float h;
	
	public Rectangle(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		if(this.w <= 0 || this.h <= 0) {
			throw new Error("Undefined Rectangle");
		}
	}
	
	public boolean contains(float x1, float y1) {
	    return (this.w > 0 && this.h > 0 &&
	            x1 >= this.x &&
	            x1 <= this.x + this.w &&
	            y1 >= this.y &&
	            y1 <= this.y + this.h);
	}

	public boolean intersects(Rectangle r) {
	    return (this.w > 0 && this.h > 0 &&
	            r.w > 0 && r.h > 0 &&
	            r.x <= this.x + this.w &&
	            r.x + r.w >= this.x &&
	            r.y <= this.y + this.h &&
	            r.y + r.h >= this.y);
	}
	
	public boolean intersects(Circle c) {
		return (c.r > 0 && this.w > 0 && this.h > 0 && Math.hypot(c.x - Math.max(this.x, Math.min(this.x + this.w, c.x)), c.y - Math.max(this.y, Math.min(this.y + this.h, c.y))) <= c.r);
	}
}