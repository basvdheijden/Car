import processing.core.*;

public class Car extends PApplet{
	private static final long serialVersionUID = -8699544479211357030L;
	
	public static void main(String[] args){
		PApplet.main(new String[] { "--present", "Car" });
	}
	
	Vehicle[] autos = new Vehicle[2];
	Turret[] turrets;
	
	PFont font;
	
	boolean gamestarted = false;
	
	//checkpoint: parent, vehicle, angle, x1, y1, x2, y2
	int cphit = 0;
	Checkpoint[] checkpoints = new Checkpoint[31];
	
	public void setup(){
		smooth();
		frameRate(40);
		size(screen.width,screen.height, P2D);
		//size(500,500, P2D);
		
		font = loadFont("berlin.vlw");
		
		for(int i=0;i<autos.length;i++){
			autos[i] = new Vehicle(this);
			autos[i].setSpeed(1);
			autos[i].setLocation(new PVector(100, 100+100*i));
		}
		
		autos[0].setColor(255,0,0);
		autos[1].setColor(0,255,0);
		
		//autos[0].setAvatar("eend.png");
		//autos[1].setAvatar("eendgroen.png");
		
		turrets = new Turret[autos.length];
		for(int i=0; i<autos.length;i++){
			int size = 50;
			turrets[i] = new Turret(this, autos[i], autos);
			turrets[i].setLocation(new PVector(random(width-100)+50,random(height-100)+50));
			turrets[i].setSize(size);
			turrets[i].setBulletSpeed((float) 6);
			turrets[i].makeBullets(30);
		}
		
		for(int i=0;i<checkpoints.length;i++){
			PVector rand = new PVector(random(-80,80), random(-80,80));
			PVector cp1 = new PVector(random(150, width-150), random(150, height-150));
			PVector cp2 = PVector.add(cp1,rand);
			
			checkpoints[i] = new Checkpoint(this, autos, cp1, cp2);			
		}
	}
	
	public void draw(){
		background(0);
		
		for(int i=0;i<turrets.length;i++){
			turrets[i].Draw();	
		}
		
		for(int i=0;i<autos.length;i++){
			autos[i].Draw();
		}
		
		if (cphit < checkpoints.length){
			checkpoints[cphit].setTarget(true);
			
			if(checkpoints[cphit].hit)
			{
				checkpoints[cphit].setOwner().score++;
				checkpoints[cphit].setTarget(false);
				cphit++;
			}else{
				checkpoints[cphit].setColor(255, 226, 0);
			}
		}else{
			checkWinner();
		}
		
		for(int i=0;i<checkpoints.length;i++){
			checkpoints[i].Draw();
		}
		
		drawScore();
		
		fill(255,100,0);
		textFont(font, 150);
		if (frameCount < 25){
			text("3", width/2-100, height/2);
		}
		else if (frameCount < 50){
			text("2", width/2-100, height/2);
		}
		else if (frameCount < 75){
			text("1", width/2-100, height/2);
		}
		else if (frameCount < 110){
			fill(0,255,0);
			text("GO!", width/2-120, height/2);
			gamestarted = true;
		}
	}
	
	public void checkWinner(){		
		int win0 = 0;
		int win1 = 0;
		
		for(int i=0;i<checkpoints.length;i++){
			if (autos[0] == checkpoints[i].dominator) win0++; else win1++;
		}
		
		//println("PLAYER1:"+win0+",PLAYER2:"+win1);
		if (win0 > win1){
			fill(autos[0].r,autos[0].g,autos[0].b);
		}else if (win1 > win0){
			fill(autos[1].r,autos[1].g,autos[1].b);
		}else{
			fill(255,255,255);
		}
		ellipse(width/2,height/2,500,500);
	}
	
	public void drawScore(){
		int x = 17;

		boolean[] checklist = new boolean[autos.length];
		for(int i=0;i<checklist.length;i++){
			checklist[i] = false;
		}
		
		int count = 0;
		while(count<autos.length){
			int max = -1;
			int num = 0;
			
			for(int i=0;i<autos.length;i++){
				if (autos[i].score > max && !checklist[i]){
					max = autos[i].score;
					num = i;
				}
			}
			
			if (!checklist[num]){
				int instance = num;
				for(int score=0;score<autos[instance].score;score++){
					noStroke();
					fill(autos[instance].r, autos[instance].g, autos[instance].b, 100);
					ellipse(x,height-75, 8,8);
					ellipse(x,height-50, 8,8);
					noFill();
					stroke(autos[instance].r, autos[instance].g, autos[instance].b, 100);
					strokeWeight(2);
					line(x,height-75,x,height-50);
					x+=17;
				}
				
				count++;
				checklist[num] = true;
			}
		}
	}
	
	public void keyPressed(){
		if (gamestarted){
			if (keyCode == 87){
				autos[1].accelerating = true; autos[1].speeddir = 1;
			}
			if (keyCode == 83){
				autos[1].accelerating = true; autos[1].speeddir = -1;
			}
			if (keyCode == 65){
				autos[1].rotating = true; autos[1].rotatingdir = -1;
			}
			if (keyCode == 68){
				autos[1].rotating = true; autos[1].rotatingdir = 1;
			}
			
			if (key == CODED){
				if (keyCode == UP){
					autos[0].accelerating = true; autos[0].speeddir = 1;
				}
				if (keyCode == DOWN){
					autos[0].accelerating = true; autos[0].speeddir = -1;
				}
				
				if (keyCode == LEFT){
					autos[0].rotating = true; autos[0].rotatingdir = -1;
				}
				if (keyCode == RIGHT){
					autos[0].rotating = true; autos[0].rotatingdir = 1;
				}	
			}
			
			if (keyCode == 79){
				autos[0].accelerating = true; autos[0].speeddir = 1;
			}
			if (keyCode == 76){
				autos[0].accelerating = true; autos[0].speeddir = -1;
			}
			
			if (keyCode == 75){
				autos[0].rotating = true; autos[0].rotatingdir = -1;
			}
			if (keyCode == 59){
				autos[0].rotating = true; autos[0].rotatingdir = 1;
			}
		}
	}

	public void keyReleased(){
		if (gamestarted){
			if (keyCode == 87){
				autos[1].accelerating = false;
			}
			if (keyCode == 83){
				autos[1].accelerating = false;
			}
			if (keyCode == 65){
				autos[1].rotating = false;
			}
			if (keyCode == 68){
				autos[1].rotating = false;
			}		
			
			if (key == CODED){
				if (keyCode == LEFT){
					autos[0].rotating = false;
				}
				if (keyCode == RIGHT){
					autos[0].rotating = false;
				}		
				
				if (keyCode == UP){
					autos[0].accelerating = false;
				}
				if (keyCode == DOWN){
					autos[0].accelerating = false;
				}
			}
			
			if (keyCode == 75){
				autos[0].rotating = false;
			}
			if (keyCode == 59){
				autos[0].rotating = false;
			}		
			
			if (keyCode == 79){
				autos[0].accelerating = false;
			}
			if (keyCode == 76){
				autos[0].accelerating = false;
			}
		}
	}	
	
}