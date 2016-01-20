package net.darkzlodey.towerdefense;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public abstract class Tower implements Cloneable {
	public String textureFile = "";
	public Image texture;
	
	public static final Tower[] towerList = new Tower[36];
	
	public static final Tower LightningTowerYellow = new TowerLightning(0, 10 , 2, 1, 6, 9).getTextureFile("LightningTower");
	public static final Tower LightningTowerGreen = new TowerLightning(1, 25 , 3, 2, 6, 15).getTextureFile("LightningTower2");
	public static final Tower LightningTowerBlue = new TowerLightning(2, 50 , 3, 3, 6, 12).getTextureFile("LightningTower3");
	public static final Tower LightningTowerPink = new TowerLightning(3, 75 , 4, 4, 9, 21).getTextureFile("LightningTower4");
	public static final Tower missileTower = new TowerMissile(4, 10, 2, 1, 0, 17).getTextureFile("MissileTower");
	
	public int id ;
	public int cost;
	public int range;
	
	public int damage;
		
	public int attackTime;//(timer)How long do we want our tower to attacking current enemy
	public int attackDelay; //(timer) Pause between each attack
	
	public int maxAttackTime;
	public int maxAttackDelay;
	
	public int RANDOM = 1; //Attack enemy nearest best
	public int FIRST = 2; //Attack random enemy
	
	//Default Attack Strategy
	public int attackStrategy = FIRST;
	
	public EnemyMove target;
	
	public static Screen screen;
	
	public Tower(int id, int cost , int range, int damage, int maxAttackTime,  int maxAttackDelay){
		if (towerList[id] != null)
		{
			System.out.println("TowerInitialization  Two towers with the same id" + id);
		
		}else{
			towerList[id] = this;
			this.id =id;
			this.cost = cost;
			this.range= range;
			this.damage = damage;
			this.maxAttackTime = maxAttackTime;
			this.maxAttackDelay = maxAttackDelay;
			

			this.attackTime = 0;
			this.attackDelay = 0;
		}
	
	}
	
	public EnemyMove calculateEnemy(EnemyMove[] enemies, int x , int y){
		//Which of the enemies given are in our range?
		EnemyMove[] enemiesInRange = new EnemyMove[enemies.length];
		
		int towerX = x;
		int towerY = y;
		
		int towerRadius = this.range;
		int enemyRadius = 1;
		
		int enemyX;
		int enemyY;
		
		for(int i =0; i < enemies.length; i++){
			if(enemies[i] != null){
				enemyX = (int) (enemies[i].xPos / Screen.towerSize);
				enemyY = (int) (enemies[i].yPos / Screen.towerSize);
				
				int dx = enemyX - towerX;
				int dy = enemyY - towerY;
				
				int dradius = towerRadius + enemyRadius;
				
				if((dx * dx) + (dy * dy) <( dradius * dradius)){
					enemiesInRange[i] = enemies[i];
				}
				
			}
		}
		
		int totalEnemies = 0 ;
		
		for(int i = 0 ; i <enemiesInRange.length; i++){
			if(enemiesInRange[i] !=null){
				totalEnemies ++;
			}
		}
				
		if(this.attackStrategy == RANDOM){
			
			if(totalEnemies > 0){
				int enemy = new Random().nextInt(totalEnemies);
				int enemiesTaken = 0;
				int i=0;
				
				while(true){
					if(enemiesTaken == enemy && enemiesInRange[i] != null){
					return enemiesInRange[i];
					}
					if(enemiesInRange[i] != null){
						enemiesTaken ++;
					}
					i++;
				}
			}
		}
		
		if(this.attackStrategy ==FIRST){
			EnemyMove bestTarget =null;
			for(int i = 0 ; i <enemiesInRange.length; i++){
				if(enemiesInRange[i] !=null){
					if(bestTarget == null){
						bestTarget = enemiesInRange[i];
					}else{
						int b_x = bestTarget.routePosX;
						int b_y = bestTarget.routePosY;
						
						int b_points_worth =EnemyAI.route.getPointsWorth(b_x, b_y); 
						
						if(EnemyAI.route.getPointsWorth(enemiesInRange[i].routePosX, enemiesInRange[i].routePosY)> b_points_worth){
							bestTarget = enemiesInRange[i];
						}else if(EnemyAI.route.getPointsWorth(enemiesInRange[i].routePosX, enemiesInRange[i].routePosY)== b_points_worth){
							
						}
						
					}
				}
			}
			return bestTarget;
			
		}
		
		return null;
	}
	
	public abstract void towerAttack(int x,int y , EnemyMove enemy);
		
	
	
	protected Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
		e.printStackTrace();
		}
		return null;
	}
	
	
	public Tower getTextureFile(String str){
		this.textureFile = str;
		
		this.texture = new ImageIcon("res/tower/"+ this.textureFile+ ".png").getImage(); 
		
		return null;
	}
	
	public static void startup(Screen screen2){
		screen = screen2;
	}
}
