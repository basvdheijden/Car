import processing.core.PApplet;
import processing.core.PVector;

public class Turret {
	PApplet parent;
	
	Vehicle vehicle;
	Vehicle[] vehicles;
	
	float x = 0;
	float y = 0;
	float bulletSpeed = 1;
	int size = 5;
	
	//fire-time in secconds
	int time = 180;
	boolean empty = false;
	boolean collision = false;
	
	float startCircle = (float) Math.PI/40;
	float currentCircle = (float) Math.PI/40;
	float stepCircle = (float) Math.PI/90;
	
	int r = 255;
	int g = 255;
	int b = 255;
	
	Bullet[] bullets;
	
	Turret(PApplet p, Vehicle v, Vehicle[] vehicles_){
		parent = p;
		vehicle = v;
		vehicles = vehicles_;
		setColor(vehicle.r, vehicle.g, vehicle.b);
	}
	
	void setLocation(float x_, float y_){
		x = x_;
		y = y_;
	}
	void setSize(int size_){
		size = size_;
	}
	void setBulletSpeed(float bulletSpeed_){
		bulletSpeed = bulletSpeed_;
	}
	
	void setColor(int r_, int g_, int b_){
		r = r_;
		g = g_;
		b = b_;
	}
	
	void makeBullets(int numberofBullets){
		bullets = new Bullet[numberofBullets];
		
		for(int i=0; i<numberofBullets; i++){
			bullets[i] = new Bullet(parent, this, vehicle);
			bullets[i].setSpeed(bulletSpeed);
			bullets[i].setSize(8);
		}
	}
	
	void Draw(){
		fireNewBullets();
		checkCollision();
		
		parent.fill(r,g,b,50);
		parent.noStroke();
		parent.ellipse(x, y, (float) size, (float) size);
		
		for(int i=0; i<bullets.length; i++){
			if (!bullets[i].hit) bullets[i].Draw();
		}
	}
	
	void fireNewBullets(){
		currentCircle += stepCircle;
		
		parent.noFill();
		parent.stroke(r,g,b);
		parent.strokeWeight(5);
		parent.arc(x,y, size,size, 0, (float) currentCircle);		
		
		if (currentCircle >= (float) Math.PI*2 && !empty){
			currentCircle = startCircle;
			for(int i=0; i<bullets.length; i++){
				if (bullets[i].fired){
					
					if (i >= (bullets.length-1) ) empty = true;
					
					continue;
				}else{
					bullets[i].Fire();	
					break;
				}
			}
		}		
	}
	
	public double getDistance(){
		PVector a = new PVector(vehicle.x,vehicle.y);
		PVector b = new PVector(x,y);
		
		return PVector.dist(a,b);
	}
	
	void checkCollision(){
		if (getDistance() < size/2){
			collision = true;
			
			int otherVehicle = 0;
			
			for(int k=0;k<vehicles.length;k++){
				if (vehicles[k] != vehicle){
					otherVehicle = k;
					vehicle = vehicles[k];
					setColor(vehicles[k].r, vehicles[k].g, vehicles[k].b);
					break;
				}
			}
			
			for(int j=0;j<bullets.length;j++){
				bullets[j].vehicle = vehicles[otherVehicle];
				bullets[j].r = vehicles[otherVehicle].r;
				bullets[j].g = vehicles[otherVehicle].g;
				bullets[j].b = vehicles[otherVehicle].b;
			}
			
			collision = false;
		}else{
			collision = false;
		}
	}
}
