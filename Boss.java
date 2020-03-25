package main;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Boss {
	private CopyOnWriteArrayList<Boid> boids = new CopyOnWriteArrayList<Boid>();
	private QuadTree tree = new QuadTree(new Bounds(0, 0, 1920, 1080));
	
	public void tick() {
		this.tree = new QuadTree(new Bounds(0, 0, 1920, 1080));
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
	
	public ArrayList<Boid> getNearByBoids(Circle b) {
		ArrayList<Boid> near = new ArrayList<Boid>();
		this.tree.query(b, near);
		return near;
	}

}