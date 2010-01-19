import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Vehicle {
	//variables
	PApplet parent;
	
	PImage avatar;
	boolean usingavatar = false;
	
	float speed;
	float speeddir = 1;
	boolean accelerating = false;
	
	float rot = 0;
	float rotatingdir = 1;
	boolean rotating = false;
	
	boolean lulmode = false;
	
	double accelerationStep = 0.11;
	
	float startCircle = (float) Math.PI/40;
	float currentCircle = (float) Math.PI/40;
	float stepCircle = (float) Math.PI/50;
	
	float w = 15;
	float h = 25;
	  
	float x = 0;
	float y = 0;
	
	PVector location = new PVector();
	
	int score = 0;
	
	float terrain = (float) 0.99;
	
	boolean hit = false;
	
	//color
	int r = 255;
	int g = 255;
	int b = 255;
	
	//constructor
	Vehicle(PApplet P){
	  parent = P;
	}
	
	//setters	
	void setSpeed(float speed_){
		speed = speed_;
	}
	void setLocation(PVector location_){
		location.x = location_.x;
		location.y = location_.y;
	}
	void setRotation(float _angle){
		rot = _angle;
	}
	void setColor(int r_, int g_, int b_){
		r = r_;
		g = g_;
		b = b_;
	}
	void setAvatar(String path){
		avatar = parent.loadImage(path);
		
		usingavatar = true;
	}
	
	int[] getColor(){
		int[] result = new int[3];
		result[0] = r;
		result[1] = g;
		result[2] = b;
		return result;
	}
	
	//other global methods
	void Draw(){
	  if (!hit){
		  updateLocation();
		  checkMovement();
		  decay();
			
		  parent.noFill();
		  parent.noStroke();
		  parent.fill(r,g,b);
		  parent.pushMatrix();
		  parent.translate(location.x,location.y);
		  parent.rotate(rot);
		  
		  if (usingavatar){
			  parent.image(avatar,-(avatar.width/2),-(avatar.height/2));
		  }else{
			  if (lulmode){
				  parent.ellipse(-10,-2, 20, 20);
				  parent.rect(-30 - speed*3, -12, 20 + speed*3, 20);
				  parent.ellipse(-30 - speed*3, -12, 17+speed, 17+speed);
				  parent.ellipse(-30 - speed*3, 5, 17+speed, 17+speed);
				  parent.stroke(0);
				  parent.noFill();
				  parent.strokeWeight(2);
				  parent.line(-10,-2, 0, -2);
			  }
			  else{
				  parent.ellipse(0, 0, h, w);
				  parent.triangle(-2,  5, 15,  5, -20 - speed*2,  20 - speed/2);
				  parent.triangle(-2, -5, 15, -5, -20 - speed*2, -20 + speed/2);
			  }
		  }
		  parent.popMatrix();
	  }else{
		  drawCircle();
	  }
	}
	  
	void updateLocation(){
		location.x += Math.cos(rot) * speed;
		location.y += Math.sin(rot) * speed;
		
		checkBounds();
	}
	
	void decay(){
		speed *= terrain;
	}
	
	void checkBounds(){
		if (location.x < 0)location.x = parent.width;
		if (location.x > parent.width)location.x = 0;
		if (location.y > parent.height)location.y = 0;
		if (location.y < 0)location.y = parent.height;
	}
	
	//movement:
	void checkMovement(){
		if (accelerating){
			accelerate();
		}
		
		if (rotating){
			rotate();
		}
	}
	
	void accelerate(){
		float speed_ = (float) ( (speed + accelerationStep) * speeddir);
		setSpeed( speed_ );
	}
	
	void rotate(){
		float rot_ = (float) (rot + ((rotatingdir * (speed/8) * (Math.PI/25))));
		setRotation( rot_ );
	}
	
	void drawCircle(){
		currentCircle += stepCircle;
		
		if (currentCircle >= (float) Math.PI*2){
			currentCircle = startCircle;
			hit = false;
			setSpeed(10);
			setRotation(0);
		}
		
		parent.pushMatrix();
		parent.noFill();
		parent.noStroke();
		parent.fill(r,g,b,255);
		parent.arc(location.x,location.y, h*2,h*2, 0, (float) currentCircle);
		
		parent.noFill();
		parent.stroke(r,g,b,255);
		parent.ellipse(location.x,location.y, h*2, h*2);
		parent.popMatrix();
	}
}
