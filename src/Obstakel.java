import processing.core.PApplet;
import processing.core.PVector;

public class Obstakel {
	PApplet parent;
	Vehicle[] vehicles;
	
	int baseradius = 20;
	
	int x = 0;
	int y = 0;
	
	float r = 255;
	float g = 255;
	float b = 0;
	float alpha = 100;
	
	float r_stroke = 255;
	float g_stroke = 255;
	float b_stroke = 255;
	
	boolean stroke = false;
	boolean fill = true;
	
	Obstakel(PApplet p, Vehicle[] v){
		parent = p;
		vehicles = v;
	}
	
	void setRadius(int r_){
		baseradius = r_;
	}
	
	void setLocation(int x_, int y_){
		x = x_;
		y = y_;
	}
	
	void Draw(){
		//if (stroke) parent.stroke(r_stroke,g_stroke,b_stroke); else parent.noStroke();
		//if (fill) parent.fill(r,g,b); else parent.noFill();
		//parent.ellipse((float) x, (float) y, (float) baseradius, (float) baseradius);
		//parent.rect(0,0,10,10);
	}
	
	void calcDistance(PVector point){
		float totaldistance = 0;
		
		for(int i=0;i<vehicles.length;i++){
			PVector a = new PVector(vehicles[i].x, vehicles[i].y);
			totaldistance += PVector.dist(point, a); 
		}
		
		//parent.println(totaldistance);
	}
}
