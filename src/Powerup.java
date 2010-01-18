import processing.core.PApplet;
import processing.core.PVector;

public class Powerup {
	public class Properties{
		Properties(PApplet p, Vehicle v){
			parent = p;
			vehicle = v;
		}
		
		int radius = 20;
		
		Vehicle vehicle;
		PApplet parent;
		
		int x;
		int y;
		
		boolean fired = false;
		
		void Fire(){
			fired = true;
			x = (int) vehicle.x;
			y = (int) vehicle.y;
		}
		
		void Draw(){
			if (fired){
				parent.stroke(255);
				parent.noFill();
				parent.ellipse(x, y, radius, radius);
			}
		}
	}
	
	Properties[] eigenschappen;
	
	int x = 0;
	int y = 0;
	
	int r = 255;
	int g = 255;
	int b = 255;
	
	int radius = 20;
	
	boolean pickedup = false;
	
	Vehicle[] vehicles;
	PApplet parent;
	
	Powerup(PApplet p, Vehicle[] v){
		parent = p;
		vehicles = v;
	}
	
	void setLocation(int x_, int y_){
		x = x_;
		y = y_;
	}
	
	void setColor(int r_, int g_, int b_){
		r = r_;
		g = g_;
		b = b_;
	}
	
	void setRadius(int radius_){
		radius = radius_;
	}
	
	void Draw(){
		if (!pickedup){
			checkCollision();
			
			parent.noStroke();
			parent.fill(r,g,b);
			parent.ellipse(x,y, radius,radius);
		}
	}
	
	void checkCollision(){
		for(int i=0;i<vehicles.length;i++){
			PVector thisitem = new PVector(x,y);
			PVector auto = new PVector(vehicles[i].x, vehicles[i].y);
			float distance = PVector.dist(auto, thisitem);
			if (distance < radius){
				eigenschappen = new Properties[5];
				for(int j=0;j<eigenschappen.length;j++){
					eigenschappen[i] = new Properties(parent, vehicles[i]);
				}
				
				pickedup = true;
				r = 0;
				g = 255;
				b = 0;
				
				vehicles[i].addPowerup(eigenschappen, 5);
			}
		}
	}
}
