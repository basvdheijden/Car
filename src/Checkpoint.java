import processing.core.PApplet;
import processing.core.PVector;

public class Checkpoint {
	PApplet parent;
	Vehicle[] vehicles;
	Vehicle dominator;
	
	PVector location1 = new PVector();
	PVector location2 = new PVector();
	
	int r = 30;
	int g = 30;
	int b = 30;
	int alpha = 255;
	
	boolean hit = false;
	boolean target = false;
	boolean dancing = false;
	
	double dx,dy,D;
	float bolradius = 13.0f;
	float strokeWeight = 3.0f;
	
	Checkpoint(PApplet p, Vehicle[] v, PVector location1_, PVector location2_){
		parent = p;
		
		vehicles = new Vehicle[v.length];
		for(int i=0;i<v.length;i++){
			vehicles[i] = v[i];
		}
		
		location1.x = location1_.x;
		location1.y = location1_.y;
		
		location2.x = location2_.x;
		location2.y = location2_.y;
		
		if (Math.random() > 0.5) dancing = true;;
		
		init();
	}
	
	void init(){
		dx = location2.x-location1.x;
		dy = location2.y-location1.y;
		D = (dx*dx) + (dy*dy);
	}
	
	void setTarget(boolean target_){
		target = target_;
	}
	
	void setColor(int r_, int g_, int b_){
		r = r_;
		g = g_;
		b = b_;
	}
	
	boolean checkCollision(Vehicle v){
		PVector middelpunt = new PVector();
		middelpunt = PVector.sub(location2,location1);
		middelpunt.div(2);
		middelpunt.add(location1);
		
		float len = PVector.dist(location1, location2);
		
		float distance = PVector.dist(v.location, middelpunt);
	
		double position = ( (v.location.x-location1.x)*dx + (v.location.y-location1.y)*dy ) /D;
		
	    double px=location1.x+position*dx;
	    double py=location1.y+position*dy;
	    	    
	    PVector a = new PVector((float) px, (float) py);
	    double distance1 = PVector.dist(location1, v.location);
	    double distance2 = PVector.dist(location2, v.location);
	    float dist = PVector.dist(a,v.location);
	    if ((dist < (v.w/2) && distance < len) || distance1 < bolradius/2 || distance2 < bolradius/2) return true;
		
		return false;
	}
	
	Vehicle setOwner(){
		int[] kleur = new int[3];
		kleur = dominator.getColor();
		setColor(kleur[0], kleur[1], kleur[2]);
		return dominator;
	}
	
	void dance(){
		location1.x += parent.random(-5,5);
		location1.y += parent.random(-5,5);
		location2.x += parent.random(-5,5);
		location2.y += parent.random(-5,5);
		init();		
	}
	
	void Draw(){
		if (target && !hit){
			if (dancing) dance();
			for(int i=0;i<vehicles.length;i++){
				boolean test = checkCollision(vehicles[i]);
				if (test){
					hit = true;
					dominator = vehicles[i];
				}
			}
		}
		
		parent.stroke(r,g,b, alpha);
		
		parent.noFill();
		parent.strokeWeight(strokeWeight);
		parent.line(location1.x,location1.y, location2.x,location2.y);
		
		parent.fill(r,g,b, alpha);
		parent.ellipse(location1.x,location1.y,bolradius,bolradius);
		parent.ellipse(location2.x,location2.y,bolradius,bolradius);
		
		if (hit && alpha > 20) alpha-=1;
	}
}
