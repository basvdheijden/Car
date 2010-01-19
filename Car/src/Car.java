import processing.core.*;

public class Car{
	public static void main(String[] args){
		PApplet.main(new String[] { "--present", "CarGame" });
	}
	
	CarGame newGame;
	
	Car(){
		newGame = new CarGame();
	}
}