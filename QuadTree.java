package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class QuadTree {
	
	private Rectangle rect;
	private QuadTree[] nodes;
	private Boid data;
	
	public QuadTree(Rectangle rect) {
		this.rect = rect;
		this.data = null;
		this.nodes = null;
	}
	
	public boolean insert(Boid e) {
		if(this.rect.contains(e.getX(), e.getY()) == false) { //if the bounds doesn't have the point then return
			return false;
		}
		
		if(this.data == null && this.nodes == null) { //case 1: empty node
			this.data = e;
			return true;
		}else {
			if(this.nodes == null && this.data != null) { //case 2: full node
				this.split();
				if(this.nodes[0].insert(this.data)) { //case 3: parent node
					this.data = null;
				}else if(this.nodes[1].insert(this.data)) {
					this.data = null;
				}else if(this.nodes[2].insert(this.data)) {
					this.data = null;
				}else if(this.nodes[3].insert(this.data)) {
					this.data = null;
				}else {
					System.out.println("somehow my kids don't contain my point?!?!?!");
				}
			}
			
			if(this.nodes[0].insert(e)) { //case 3: parent node
				return true;
			}
			if(this.nodes[1].insert(e)) {
				return true;
			}
			if(this.nodes[2].insert(e)) {
				return true;
			}
			if(this.nodes[3].insert(e)) {
				return true;
			}
			return false;
		}
	}
	
	public void query(Rectangle r, ArrayList<Boid> result) {
		if(this.rect.intersects(r) == false) { //i am not in the search area
			return;
		}else {
			if(this.nodes != null && this.data == null) { //has nodes
				this.nodes[0].query(r, result);
				this.nodes[1].query(r, result);
				this.nodes[2].query(r, result);
				this.nodes[3].query(r, result);
			}else if(this.data != null && this.nodes == null && r.contains(this.data.getX(), this.data.getY()) == true) { //has data
				result.add(this.data);
			}
		}
	}
	
	public void query(Circle c, ArrayList<Boid> result) {
		if(c.intersects(this.rect) == false) { //i am not in the search area
			return;
		}else {
			if(this.nodes != null && this.data == null) { //has nodes
				this.nodes[0].query(c, result);
				this.nodes[1].query(c, result);
				this.nodes[2].query(c, result);
				this.nodes[3].query(c, result);
			}else if(this.data != null && this.nodes == null && c.contains(this.data.getX(), this.data.getY()) == true) { //has data
				result.add(this.data);
			}
		}
	}
	
	private void split() {
	    this.nodes = new QuadTree[4];
	    this.nodes[0] = new QuadTree(new Rectangle(this.rect.x, this.rect.y, this.rect.w/2, this.rect.h/2)); //nw
	    this.nodes[1] = new QuadTree(new Rectangle(this.rect.x + this.rect.w/2, this.rect.y, this.rect.w/2, this.rect.h/2)); //ne
	    this.nodes[2] = new QuadTree(new Rectangle(this.rect.x + this.rect.w/2, this.rect.y+this.rect.h/2, this.rect.w/2, this.rect.h/2)); //se
	    this.nodes[3] = new QuadTree(new Rectangle(this.rect.x, this.rect.y+this.rect.h/2, this.rect.w/2, this.rect.h/2)); //sw
	}
	
	public void render(Graphics g) {
		if(this.nodes != null) { //has nodes
			this.nodes[0].render(g);
			this.nodes[1].render(g);
			this.nodes[2].render(g);
			this.nodes[3].render(g);
		}else {
			g.setColor(Color.white);
			g.drawRect((int)this.rect.x, (int)this.rect.y, (int)this.rect.w, (int)this.rect.h);
		}
	}

}