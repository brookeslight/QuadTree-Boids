package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Boid {
	//variable
	private float x;
	private float y;
	private Vector dir;
	private Boss boss;
	//constants
	private static final Color COLOR = new Color(0, 0, 128); //navy blue
	private static final float MAXVELOCITY = 2.25f;
	private static final float RANGE = 175;
	private static final float PERSONALSPACE = 125;
	private static final float SEPARATIONWEIGHT = 0.125f;
	private static final float ALIGHTMENTWEIGHT = 0.125f;
	private static final float COHESIONWIEGHT = 0.125f;

	public Boid(float x, float y, Boss boss) {
		this.x = x;
		this.y = y;
		this.dir = new Vector((float)(Math.random()-0.5f), (float)(Math.random()-0.5f));
		this.dir.normalize();
		this.dir.scale(MAXVELOCITY);
		this.boss = boss;
	}
	
	public void tick() {
		//get near
		ArrayList<Boid> nearBoids = this.boss.getNearByBoids(new Circle((this.x), (this.y-12.5f), RANGE));
		nearBoids.remove(this); //remove self from list
		
		if(nearBoids.size() != 0) {
			Vector separationForce = new Vector(0, 0);
			Vector alignmentForce = new Vector(0, 0);
			Vector cohesionForce = new Vector(0, 0);
			int separationCount = 0;
			for(Boid b: nearBoids) {
				//Separation:
				if(Math.hypot(b.getX() - this.getX(), b.getY() - this.getY()) <= PERSONALSPACE) {
					separationForce.add(new Vector(this.x, b.getX(), this.y, b.getY()));
					separationCount++;
				}
				
				//Alignment:
				alignmentForce.add(b.getDir());
				
				//Cohesion:
				cohesionForce.add(new Vector(b.getX(), this.x, b.getY(), this.y));
			}
			
			//average
			if(separationCount > 0) {
				separationForce.normalize();
				separationForce.scale(SEPARATIONWEIGHT);
				this.dir.add(separationForce);
			}
			alignmentForce.normalize();
			alignmentForce.scale(ALIGHTMENTWEIGHT);
			this.dir.add(alignmentForce);
			cohesionForce.normalize();
			cohesionForce.scale(COHESIONWIEGHT);
			this.dir.add(cohesionForce);
			this.dir.normalize(); //normalize and scale direction
			this.dir.scale(MAXVELOCITY);
		}

		//move
		this.x += this.dir.x;
		this.y += this.dir.y;
		
		//stay in bounds
		if(this.x <= 0) {
			this.x = 960;
		} else if(this.x >= 960) {
			this.x = 0;
		}
		if(this.y <= 0) {
			this.y = 540;
		} else if(this.y >= 540) {
			this.y = 0;
		}
	}
	
	public void render(Graphics2D g2d) {
		AffineTransform old = g2d.getTransform();
		g2d.rotate(Math.atan2(this.dir.y, this.dir.x) + (Math.PI / 2), this.x, this.y - 12.5f); //center point of triangle is (x, y-12.5)
		g2d.setColor(COLOR);
		g2d.fillPolygon(new int[] {(int)(this.x), (int)(this.x+7), (int)(this.x-7)}, new int[] {(int)(this.y-25), (int)(this.y), (int)(this.y)}, 3); //use pythag tripple for triangle (in this case 7,24,25)
		g2d.setTransform(old);
//		g2d.drawOval((int)(this.x - RANGE/2), (int)(this.y - RANGE/2 - 12.5), (int)RANGE, (int)RANGE);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Vector getDir() {
		return dir;
	}

}