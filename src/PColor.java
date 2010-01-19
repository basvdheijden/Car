import processing.core.*;

public class PColor {
	PApplet parent;
	
	boolean hasStroke;
	boolean hasFill;
	
	int strokeR;
	int strokeG;
	int strokeB;
	
	int strokeWeight;
	
	int fillR;
	int fillG;
	int fillB;
	
	PColor(PApplet p){
		parent = p;
	}
	
	public void applyStyles(){
		if (hasStroke){
			parent.stroke(strokeR, strokeG, strokeB);
			parent.strokeWeight(strokeWeight);
		}
		
		if (hasFill){
			parent.fill(fillR, fillG, fillB);
		}
	}
	
	public void switchStrokeFill(){
		int tempStrokeR = strokeR;
		int tempStrokeG = strokeG;
		int tempStrokeB = strokeB;
		
		strokeR = fillR;
		strokeG = fillG;
		strokeB = fillB;
		
		fillR = tempStrokeR;
		fillG = tempStrokeG;
		fillB = tempStrokeB;
	}
}
