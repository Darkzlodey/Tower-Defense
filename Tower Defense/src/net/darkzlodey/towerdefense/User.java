package net.darkzlodey.towerdefense;

public class User {

	private Screen screen;
	Player player;
	
	int startingMoney = 25;
	int startingHealth = 100;
	public User(Screen screen) {
	this.screen = screen;
	this.screen.scene = 0; //Sets scene to main menu
	}
	public void createPlayer(){
		this.player= new Player(this);
		
	}
}
