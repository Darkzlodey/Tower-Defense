package net.darkzlodey.towerdefense;

public class Player  {

	int health;
	int money;
	
	public Player(User user) {
	this.money = user.startingMoney;
	this.health=user.startingHealth;
		
	}

}
