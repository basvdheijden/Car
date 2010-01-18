import processing.core.PApplet;
import processing.core.PVector;

public class Bullet {
	PApplet parent;
	Turret turret;
	Vehicle vehicle;
	
	PVector location = new PVector(0,0);
	
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

		location.x = t.location.x;
		location.y = t.location.y;
		
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
		if (location.x < 0)location.x = parent.width;
		if (location.x > parent.width)location.x = 0;
		if (location.y > parent.height)location.y = 0;
		if (location.y < 0)location.y = parent.height;		
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
		parent.translate(location.x,location.y);
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
		location.x = turret.location.x;
		location.y = turret.location.y;
		fired = true;
		angle = Math.random()*Math.PI;
	}
	
	void calcAngle(){
		double angle_ = Math.atan2(vehicle.location.y - location.y , vehicle.location.x - location.x);
		angle = angle_;
	}
	
	void setColor(int r_, int g_, int b_){
		r = r_;
		g = g_;
		b = b_;
	}
	
	void Move(){
		location.x += Math.cos(angle) * speed;
		location.y += Math.sin(angle) * speed; 
	}
	
	void checkCollision(){
		if (PVector.dist(vehicle.location, location) < 10){
			vehicle.hit = true;
			vehicle.setLocation(new PVector(100, parent.random(0,parent.height)));
			hit = true;
		}
	}
}
