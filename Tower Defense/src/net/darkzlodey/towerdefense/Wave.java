package net.darkzlodey.towerdefense;

import java.util.Random;

public class Wave {

	Screen screen;
	
	int waveNumber = 0;
	int pointsThisRound ;

	int currentPoints;
	
	int currentDelay = 0;
	int spawnRate = 25;
	
	
	
	boolean waveSpawning;
	
	public Wave(Screen screen){
		this.screen = screen;
	}
	
	public void nextWave(){
		this.waveNumber++;
		this.pointsThisRound = this.waveNumber * 25;
		this.currentPoints = 0;
		this.waveSpawning = true;
		
		System.out.println("[Wave] Wave " + this.waveNumber + " incoming!");
		
		for(int i = 0; i < this.screen.enemyMap.length; i++){
			this.screen.enemyMap[i] = null;
		}
		
	}
	
	
	public void spawnEnemies(){
		if(this.currentPoints < this.pointsThisRound){
			if(currentDelay < spawnRate){
				currentDelay+=Screen.speed;
			}else{
				currentDelay = 0 ;
				
				System.out.println("[Wave] Enemy Spawned");
				
				int[] enemiesSpawnableID = new int [Enemy.enemyList.length];
				int enemiesSpawnableSoFar = 0;
				
				for(int i=0; i<Enemy.enemyList.length; i++){
					if(Enemy.enemyList[i] != null){
					
					if(Enemy.enemyList[i].points + currentPoints <= this.pointsThisRound && Enemy.enemyList[i].points <= this.waveNumber){
					enemiesSpawnableID[enemiesSpawnableSoFar] = Enemy.enemyList[i].id;
					enemiesSpawnableSoFar++;
					}
				}
			}
				
				int enemyID = new Random().nextInt(enemiesSpawnableSoFar);
			
				this.currentPoints += Enemy.enemyList[enemyID].points;
			this.screen.spawnEnemy(enemiesSpawnableID[enemyID]);
			
			}
		}else{
			this.waveSpawning = false;
		}
	}
}
