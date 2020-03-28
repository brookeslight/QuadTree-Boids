package main;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Boss {
	private CopyOnWriteArrayList<Boid> boids = new CopyOnWriteArrayList<Boid>();
	private QuadTree tree = new QuadTree(new Rectangle(0, 0, 960, 540));
	
	public void tick() {
		this.tree = new QuadTree(new Rectangle(0, 0, 960, 540));
		for(Boid b: this.boids) {
			this.tree.insert(b);
		}
		for(Boid b: this.boids) {
			b.tick();
		}
	}
	
	public void render(Graphics2D g) {
//		this.tree.render(g);
		for(Boid b: this.boids) {
			b.render(g);
		}

	}
	
	public void add(Boid b) {
		this.boids.add(b);
	}
	
	public void remove(Boid b) {
		this.boids.remove(b);
	}
	
	public void clear() {
		this.boids.clear();
	}
	
	public ArrayList<Boid> getNearByBoids(Circle c) {
		ArrayList<Boid> near = new ArrayList<Boid>();
		this.tree.query(c, near);
		return near;
	}

}