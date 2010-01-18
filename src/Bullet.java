import processing.core.PApplet;
import processing.core.PVector;

public class Bullet {
	PApplet parent;
	Turret turret;
	Vehicle vehicle;
	
	float x,y;
	
	int size = 10;
	double speed = 1;
	
	boolean fired = false;
	boolean hit = false;
	
	double angle = 0.0;
	double rotateSpeed = Math.PI/30;
	
	int r = 255;
	int g = 0;
	int b = 0;
	
	Bullet(PApplet p, Turret t, Vehicle v){
		parent = p;
		turret = t;
		vehicle = v;
		
		x = t.x + (t.size/2);
		y = t.y + (t.size/2);
		x = t.x;
		y = t.y;
		
		setColor(vehicle.r, vehicle.g, vehicle.b);
	}
	
	void setSize(int _size){
		size = _size;
	}
	
	void setSpeed(double speed_){
		speed = speed_;
	}
	
	void setVehicle(Vehicle v){
		vehicle = v;
	}
	
	void checkBounds(){
		if (x < 0)x = parent.width;
		if (x > parent.width)x = 0;
		if (y > parent.height)y = 0;
		if (y < 0)y = parent.height;		
	}
	
	void Draw(){
		if (!vehicle.hit){
			if (fired){
				checkCollision();
				calcAngle();
				Move();
				checkBounds();
			}
		}else{
			if (fired){
				Move();
				checkBounds();
			}
		}
		
		parent.noStroke();
		parent.fill(r,g,b, 150);
		
		parent.pushMatrix();
		parent.translate(x,y);
		parent.rotate((float) angle);
		parent.ellipse(0,0, size+10, size);

		parent.noFill();
		parent.strokeWeight(2);
		parent.stroke(r,g,b,100);
		
		float wave = (PApplet.sin(parent.frameCount)*5)+15;
		parent.ellipse(0,0, wave+10, wave);
		
		parent.popMatrix();
	}
	
	void Fire(){
		x = turret.x;
		y = turret.y;
		fired = true;
		calcAngle();
	}
	
	void calcAngle(){
		double angle_ = Math.atan2(vehicle.y - y , vehicle.x - x);
		angle = angle_;
	}
	
	void setColor(int r_, int g_, int b_){
		r = r_;
		g = g_;
		b = b_;
	}
	
	void Move(){
	   x += Math.cos(angle) * speed;
	   y += Math.sin(angle) * speed; 
	}
	
	void checkCollision(){
		PVector auto = new PVector(vehicle.x, vehicle.y);
		PVector deze = new PVector(x,y);
		
		if (PVector.dist(auto, deze) < 10){
			vehicle.hit = true;
			vehicle.setLocation(100, parent.random(0,parent.height));
			hit = true;
		}
	}
}
