import processing.core.PApplet;
import processing.core.PVector;

public class Checkpoint {
	PApplet parent;
	Vehicle[] vehicles;
	Vehicle dominator;
	
	float x1,y1;
	float x2,y2;
	
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
	
	Checkpoint(PApplet p, Vehicle[] v, float x1_, float y1_, float x2_, float y2_){
		parent = p;
		
		vehicles = new Vehicle[v.length];
		for(int i=0;i<v.length;i++){
			vehicles[i] = v[i];
		}
		
		x1 = x1_;
		y1 = y1_;
		
		x2 = x2_;
		y2 = y2_;
		
		if (Math.random() > 0.5) dancing = true;;
		
		init();
	}
	
	void init(){
		dx = x2-x1;
		dy = y2-y1;
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
		double position = ( (v.x-x1)*dx + (v.y-y1)*dy ) /D;
		
	    double px=x1+position*dx;
	    double py=y1+position*dy;
	    	    
	    PVector a = new PVector((float) px, (float) py);
	    PVector b = new PVector(v.x, v.y);
	    PVector punt1 = new PVector(x1,y1);
	    PVector punt2 = new PVector(x2,y2);
	    double distance1 = PVector.dist(punt1, b);
	    double distance2 = PVector.dist(punt2, b);
	    
	    float dist = PVector.dist(a,b)/20;
	    
	    //parent.line((float)px,(float)py,v.x,v.y);
	    
	    if (distance1 < bolradius || distance2 < bolradius || (dist < 1 && 
	    		(
	    				(v.x > x1 && v.x < x2 && v.y > y1 && v.y < y2)
	    				||
	    				(v.x < x1 && v.x > x2 && v.y > y1 && v.y < y2)
	    				||
	    				(v.x < x1 && v.x > x2 && v.y < y1 && v.y > y2)
	    				||
	    				(v.x > x1 && v.x < x2 && v.y < y1 && v.y > y2)
	    		))
	    	){
			return true;
	    }
		
		return false;
	}
	
	Vehicle setOwner(){
		int[] kleur = new int[3];
		kleur = dominator.getColor();
		setColor(kleur[0], kleur[1], kleur[2]);
		return dominator;
	}
	
	void dance(){
		x1 += parent.random(-5,5);
		y1 += parent.random(-5,5);
		x2 += parent.random(-5,5);
		y2 += parent.random(-5,5);
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
		parent.line(x1,y1, x2,y2);
		
		parent.fill(r,g,b, alpha);
		parent.ellipse(x1,y1,bolradius,bolradius);
		parent.ellipse(x2,y2,bolradius,bolradius);
		
		if (hit && alpha > 40) alpha-=1;
	}
}
