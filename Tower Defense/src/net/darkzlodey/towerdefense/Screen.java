package net.darkzlodey.towerdefense;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Thread thread = new Thread(this);
	
	Frame frame;
	Level level;
	LevelFile levelFile;
public static	EnemyAI enemyAI;
	Wave wave;
	
	//Account
public 	User user;
	
	private int fps = 0;
	
	public int scene;
	
	public int hand=0;
	public int handXPos=0;
	public int handYPos=0;
	
	public static int speed;
	
	public boolean running = false;
	
	public static double towerSize;
	
	public int frameWidth;
	public int frameHeight;
	
	public static int frameHeightBorder;
	
	public Tower selectedTower;
	
	public int [][] map = new int[22][14];
	public Tower[][] towerMap = new Tower[22][14];
	public Image[] terrain = new Image[100];
	
	private Image buttonStartGame = new ImageIcon("res/startButton.png").getImage();
	private Image speedUpGame = new ImageIcon("res/speedButton.png").getImage();
	private Image speedUpGame2x = new ImageIcon("res/speedButton2x.png").getImage();
	private Image GameOver = new ImageIcon("res/GameOver.png").getImage();
	
	
	public EnemyMove[] enemyMap = new EnemyMove[50];
	
	public static Missile[] missiles = new Missile[10];
	
	//private String packagename = "net/darkzlodey/towerdefense";
	
	public Screen(Frame frame){
		
		this.frame = frame;
		
		this.frame.addKeyListener(new KeyHandler(this));
		this.frame.addMouseListener(new MouseHandler(this));	
		this.frame.addMouseMotionListener(new MouseHandler(this));
		
		
		frameWidth = this.frame.getWidth();
		frameHeight = this.frame.getWidth() / 16 * 9;
		
		
		frameHeightBorder= (this.frame.getHeight() - frameHeight)/2;
		towerSize = this.frameHeight / 18;

		thread.start(); //print in console
	}
	
	public void paintComponent(Graphics g){
		g.clearRect(0, 0, this.frame.getWidth(), this.frame.getHeight());
				
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.frame.getWidth(), this.frame.getHeight());
		
		if(scene == 0){
			g.setColor(Color.BLUE);
			g.fillRect(0, 0+ frameHeightBorder, this.frameWidth, this.frameHeight);
			
		}else if (scene == 1){
			//Background
			g.setColor(Color.GREEN);
			g.fillRect(0, 0+ frameHeightBorder, this.frameWidth, this.frameHeight);
			
			//Grid
			g.setColor(Color.GRAY);
			for(int x=0; x<22 ; x++){
			for(int y=0; y<14 ; y++){
				g.drawImage(terrain[map[x][y]], (int) towerSize + (x * (int) towerSize),(int) towerSize + (y * (int) towerSize)+ frameHeightBorder,(int) towerSize,(int) towerSize ,null);
				g.drawRect((int)towerSize + (x*(int) towerSize), (int)towerSize + (y*(int) towerSize) + frameHeightBorder, (int) towerSize, (int) towerSize);
			}
			}
			
			//UpgradeMenu
			g.setColor(Color.GRAY);
			g.drawRect((int)towerSize * 24, (int)towerSize + frameHeightBorder, (int)towerSize * 7,(int)towerSize * 14);
			g.drawRect((int)towerSize * 24 + (int)towerSize/2 -1, (int)towerSize + frameHeightBorder + (int)towerSize/2 -1,(int)towerSize * 3 +2, (int)towerSize * 3+2 );
			if(selectedTower != null)g.drawImage(selectedTower.texture,(int)towerSize * 24 + (int)towerSize/2 , (int)towerSize + frameHeightBorder + (int)towerSize/2 ,(int)towerSize * 3 , (int)towerSize * 3 , null);
			
			//Tower Description
			g.drawRect((int)towerSize*28 -5, (int)towerSize + (((int)towerSize*5/2 +2)/3)*0 + frameHeightBorder + (int)towerSize*2/4 -1, frame.getWidth() -(int)towerSize*29 - (int)towerSize/2 , ((int)towerSize*5/2 +2)/3 );
			g.drawRect((int)towerSize*28 -5, (int)towerSize + (((int)towerSize*5/2 +2)/3)*1 +frameHeightBorder + (int)towerSize*3/4 -1, frame.getWidth() -(int)towerSize*29 - (int)towerSize/2 , ((int)towerSize*5/2 +2)/3 );
			g.drawRect((int)towerSize*28 -5, (int)towerSize + (((int)towerSize*5/2 +2)/3)*2 + frameHeightBorder + (int)towerSize*4/4 -1, frame.getWidth() -(int)towerSize*29 - (int)towerSize/2 , ((int)towerSize*5/2 +2)/3 );
			
			//Tower Strategy
			g.drawRect((int)towerSize*24 + (int)towerSize/2 -1 + 0*(((int)towerSize*9/2)/4 +(int)towerSize/2) , (int)towerSize*5 + frameHeightBorder, ((int)towerSize*9/2)/4, ((int)towerSize*5/2 +2)/3);
			g.drawRect((int)towerSize*24 + (int)towerSize/2 -1 + 1*(((int)towerSize*9/2)/4 +(int)towerSize/2) , (int)towerSize*5 + frameHeightBorder, ((int)towerSize*9/2)/4, ((int)towerSize*5/2 +2)/3);
			g.drawRect((int)towerSize*24 + (int)towerSize/2 -1 + 2*(((int)towerSize*9/2)/4 +(int)towerSize/2) , (int)towerSize*5 + frameHeightBorder, ((int)towerSize*9/2)/4, ((int)towerSize*5/2 +2)/3);
			g.drawRect((int)towerSize*24 + (int)towerSize/2 -1 + 3*(((int)towerSize*9/2)/4 +(int)towerSize/2) , (int)towerSize*5 + frameHeightBorder, ((int)towerSize*9/2)/4, ((int)towerSize*5/2 +2)/3);
			
			//Option Menu
				//g.drawRect((int)towerSize * 24, (int)towerSize*31/2,(int)towerSize * 7,(int)towerSize *2);
				//Three Buttons
				g.drawRect((int)towerSize * 24, (int)towerSize*31/2 + (int)towerSize*2/3*0 + frameHeightBorder,(int)towerSize *13/4,(int)towerSize *2/3);
				g.drawRect((int)towerSize * 24, (int)towerSize*31/2 + (int)towerSize*2/3*1 + frameHeightBorder,(int)towerSize *13/4,(int)towerSize *2/3);
				g.drawRect((int)towerSize * 24, (int)towerSize*31/2 + (int)towerSize*2/3*2 + frameHeightBorder,(int)towerSize *13/4,(int)towerSize *2/3);
				
				//Start Round / Speed up
				boolean flag = false;
				for(int i=0; i<enemyMap.length;i++ ){
					if(enemyMap[i]!=null){
						flag = true;
					}
				}
				
				if(!flag){
					g.drawImage(buttonStartGame,(int)towerSize * 111/4, (int)towerSize*31/2+ frameHeightBorder,(int)towerSize *13/4,(int)towerSize *2,null);
				}else
				{
					if(speed == 1){
						g.drawImage(speedUpGame,(int)towerSize * 111/4, (int)towerSize*31/2+ frameHeightBorder,(int)towerSize *13/4,(int)towerSize *2,null);
					}else{
						g.drawImage(speedUpGame2x,(int)towerSize * 111/4, (int)towerSize*31/2 + frameHeightBorder,(int)towerSize *13/4,(int)towerSize *2,null);
						
					}
						
				}
			//Enemies
			for(int i = 0 ; i < enemyMap.length; i++){
				if(enemyMap[i] != null){
					g.drawImage(enemyMap[i].enemy.texture,(int) enemyMap[i].xPos + (int)towerSize, (int)enemyMap[i].yPos + (int)towerSize + frameHeightBorder, (int)towerSize,(int)towerSize, null );
				}
			}
			
			//Health + Money + LevelNumber
			g.drawRect((int)(12*towerSize/25), (15*(int)towerSize)+(int)(12*towerSize/25) + frameHeightBorder , (int)(frameWidth/11.52), (this.frameHeight-(15*(int)towerSize) -(int)(12*towerSize/25) - (int)(12*towerSize/25))/3);
			g.drawString("Health:" + user.player.health,(int)(12*towerSize/25)+25, (15*(int)towerSize)+(int)(12*towerSize/25) +25 + frameHeightBorder);
			
			//GameOver
			if(user.player.health <=0){
				g.drawImage(GameOver,0, 0+ frameHeightBorder, this.frameWidth, this.frameHeight,null);
			}
			g.drawRect((int)(12*towerSize/25), (15*(int)towerSize)+(int)(12*towerSize/25)+((this.frameHeight-(15*(int)towerSize) -(int)(12*towerSize/25) - (int)(12*towerSize/25))/3)+ frameHeightBorder , (int)(frameWidth/11.52), (this.frameHeight-(15*(int)towerSize) -(int)(12*towerSize/25) - (int)(12*towerSize/25))/3);
			g.drawString("Money:" + user.player.money,(int)(12*towerSize/25)+25, (15*(int)towerSize)+(int)(12*towerSize/25) +33+(int)towerSize + frameHeightBorder);
			
			g.drawRect((int)(12*towerSize/25), (15*(int)towerSize)+(int)(12*towerSize/25)+(((this.frameHeight-(15*(int)towerSize) -(int)(12*towerSize/25) - (int)(12*towerSize/25))/3))*2  + frameHeightBorder, (int)(frameWidth/11.52), (this.frameHeight-(15*(int)towerSize) -(int)(12*towerSize/25) - (int)(12*towerSize/25))/3);
			g.drawString("Level number:" +this.wave.waveNumber,(int)(12*towerSize/25)+25, (15*(int)towerSize)+(int)(12*towerSize/25) +13+(int)towerSize + frameHeightBorder);
			
			//Tower Scroll List Buttons
			g.drawRect((int)(12*towerSize/25)+(int)(12*towerSize/25)+(int)(frameWidth/11.52), (15*(int)towerSize)+(int)(12*towerSize/25) + frameHeightBorder, this.frameWidth/40, (this.frameHeight-(15*(int)towerSize) -(int)(12*towerSize/25) - (int)(12*towerSize/25)));
			//Other button on the other side
			
			
			//Tower List
			for(int x=0;x<18;x++){
				for(int y=0;y<2;y++){
					if (Tower.towerList[x*2+y]!=null) {
						g.drawImage(Tower.towerList[x*2 + y].texture,(int)(12*towerSize/25)+(int)(12*towerSize/25)+(int)(frameWidth/11.52)+(int)(12*towerSize/25)+this.frameWidth/40+ (x*(int)towerSize), (15*(int)towerSize)+(int)(12*towerSize/25)+(y*(int)towerSize) + frameHeightBorder,(int) towerSize,(int) towerSize,null);
					
					if(Tower.towerList[x*2 + y].cost >this.user.player.money){
					g.setColor(new Color(255,0,0,100));
						g.fillRect((int)(12*towerSize/25)+(int)(12*towerSize/25)+(int)(frameWidth/11.52)+(int)(12*towerSize/25)+this.frameWidth/40+ (x*(int)towerSize), (15*(int)towerSize)+(int)(12*towerSize/25)+(y*(int)towerSize) + frameHeightBorder,(int) towerSize,(int) towerSize);	
					}
					}
					g.setColor(Color.GRAY);
					g.drawRect((int)(12*towerSize/25)+(int)(12*towerSize/25)+(int)(frameWidth/11.52)+(int)(12*towerSize/25)+this.frameWidth/40+ (x*(int)towerSize), (15*(int)towerSize)+(int)(12*towerSize/25)+(y*(int)towerSize) + frameHeightBorder,(int) towerSize, (int) towerSize);
				}
			}
			
			//Towers on Grid
			for(int x=0; x<22; x++){
				for(int y=0;y<14;y++){
					if(towerMap[x][y] !=null){
						g.drawImage(Tower.towerList[towerMap[x][y].id].texture, (int) towerSize + (x * (int)towerSize ), (int) towerSize + (y * (int)towerSize) + frameHeightBorder,(int) towerSize , (int) towerSize , null  );
						
						}
					}
				}
			
			
			//Attacking Towers on Grid
			for(int x=0; x<22; x++){
				for(int y=0;y<14;y++){
					if(towerMap[x][y] !=null){
						//Attack Enemy
						if(towerMap[x][y].target != null){
							if(towerMap[x][y] instanceof TowerLightning){
							g.setColor(Color.RED);
							g.drawLine((int) towerSize + (x * (int)towerSize ) +(int)towerSize /2, (int) towerSize + (y * (int)towerSize) + frameHeightBorder + (int)towerSize /2 , (int)towerSize + (int)towerMap[x][y].target.xPos + (int)towerSize /2,(int) towerSize + (int)towerMap[x][y].target.yPos + frameHeightBorder + (int)towerSize /2);
							}
						}
						
						if(towerMap[x][y] !=null){
							if(selectedTower == towerMap[x][y]){
							g.setColor(Color.GRAY);
							g.drawOval((int)towerSize + (x* (int) towerSize) - (towerMap[x][y].range *2 *(int) towerSize + (int) towerSize) / 2 + (int) towerSize / 2, (int) towerSize + (y * (int) towerSize) - (towerMap[x][y].range * 2 * (int) towerSize + (int) towerSize) / 2 + (int) towerSize / 2 + frameHeightBorder, towerMap[x][y].range * 2 * (int) towerSize + (int) towerSize, towerMap[x][y].range * 2 * (int) towerSize + (int) towerSize);
							g.setColor(new Color(64, 64, 64, 64));
							g.fillOval((int)towerSize + (x* (int) towerSize) - (towerMap[x][y].range *2 *(int) towerSize + (int) towerSize) / 2 + (int) towerSize / 2, (int) towerSize + (y * (int) towerSize) - (towerMap[x][y].range * 2 * (int) towerSize + (int) towerSize) / 2 + (int) towerSize / 2 + frameHeightBorder, towerMap[x][y].range * 2 * (int) towerSize + (int) towerSize, towerMap[x][y].range * 2 * (int) towerSize + (int) towerSize);
						}
					}
				}
			}
		}
			
			//Missiles
			Graphics2D g2d = (Graphics2D) g;
			for(int i=0; i< missiles.length; i++){
				if(missiles[i] != null){
				g2d.rotate(missiles[i].direction + Math.toRadians(90) , (int)missiles[i].x + (int) towerSize +(int)towerSize/2, (int)missiles[i].y + (int)towerSize +(int)towerSize/2 + frameHeightBorder);
				g.drawImage(missiles[i].texture,(int) missiles[i].x + (int)towerSize +(int)towerSize/2 ,(int) missiles[i].y + (int)towerSize +(int)towerSize/2 + frameHeightBorder , (int)(14*towerSize/50), (int)(30*towerSize/50), null);	
				g2d.rotate(-missiles[i].direction + Math.toRadians(-90) , (int)missiles[i].x + (int)towerSize +(int)towerSize/2, (int)missiles[i].y + (int)towerSize +(int)towerSize/2 + frameHeightBorder);

				}
			}
			
			
			//Hand
			if(hand !=0 && Tower.towerList[hand-1] != null){
				g.drawImage(Tower.towerList[hand-1].texture, this.handXPos-(int)Screen.towerSize/2,this.handYPos-(int)Screen.towerSize/2 , (int) Screen.towerSize,(int) Screen.towerSize,null);
			}
			}else {
			
			g.setColor(Color.WHITE);
			g.fillRect(0, 0 + frameHeightBorder, this.frameWidth, this.frameHeight);
		}
	
		
		//FPS at the bottom
		g.setColor(Color.BLACK);
		g.drawString(fps + "", 10, 10+ frameHeightBorder);
		
		
		
	}
	//only first time
	public void loadGame(){
		user = new User (this);
		levelFile = new LevelFile();
		wave = new Wave(this);
		 
		
		for(int y = 0; y < 10; y++){
			for(int x = 0; x < 10; x++){
				terrain[x + (y * 10)] = new ImageIcon("res/" + "tower/" + "terrain.png").getImage();
				terrain[x + (y * 10)] = createImage(new FilteredImageSource(terrain[x + (y * 10)].getSource(), new CropImageFilter(x * 25, y * 25, 25, 25)));
			}
		}
		
		running = true;
		
	}
	/**
	 * Each time you start a level
	 * @param user
	 * @param level
	 */
	
	public void startGame(User user, String level){
		user.createPlayer();
		
		Screen.speed =1;
		
		this.level = levelFile.getLevel(level);
		this.level.findSpawnPoint();
		this.map=this.level.map;
		
		
		
		Screen.enemyAI = new EnemyAI(this.level);
		
		
		this.scene = 1; //level1
		this.wave.waveNumber = 10;
	}
	
	
	
	
	public void run() {
		
		long lastFrame = System.currentTimeMillis();
		int frames = 0;
		
		int synchronized_fps= 0;
		
		
		loadGame();
		
		
	while(running){
		repaint();
		
		frames++;
		
		if(System.currentTimeMillis() - 1000 >= lastFrame ){ //when we reach 1 sec -repaint
			fps=frames;
			frames=0;
			lastFrame = System.currentTimeMillis();
		}
		
		double time = (double) System.currentTimeMillis() / 1000;
		int timeMilliSec = (int) Math.round((time - (int) time)*1000);
				
		if(timeMilliSec > synchronized_fps * 1000 / 25 ){
			synchronized_fps++;
		update();
		
		if(synchronized_fps == 40 ){
			synchronized_fps = 0;
		}
		}
		if(timeMilliSec + 1000/25 < synchronized_fps * 1000 / 25){
			synchronized_fps = 0;
		}
		
		
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	
	System.exit(0);
}
	
	public void enemyUpdate(){
		for(int i=0; i< enemyMap.length; i++){
			if(enemyMap[i]!= null){
				if(!enemyMap[i].attack){
					EnemyAI.moveAI.move(enemyMap[i]);
					
					if(enemyMap[i].health <=0){
						user.player.money +=enemyMap[i].enemy.price;
						}
					if(((int)enemyMap[i].routePosX == (int)EnemyAI.basePosX )&&((int)enemyMap[i].routePosY == (int)EnemyAI.basePosY)){
						user.player.health -=enemyMap[i].enemy.damage;
						enemyMap[i]=null;
						break;
					}
				
					
				}
	
				enemyMap[i] =enemyMap[i].update(); // currently only cheking dead or not
			}
			
		}
	}
	
	public void towerUpdate(){
		for(int x=0; x< 22; x++){
			for(int y=0; y< 14 ; y++){
				if(towerMap[x][y] != null){
					towerAttack(x,y);
				}
			}
		}
	}
	
	public void towerAttack(int x , int y){
		if(this.towerMap[x][y].target == null){
			//Find target for tower
			if(this.towerMap[x][y].attackDelay > this.towerMap[x][y].maxAttackDelay){
				EnemyMove currentEnemy = this.towerMap[x][y].calculateEnemy(enemyMap, x, y);
				
				if(currentEnemy != null){
					this.towerMap[x][y].towerAttack(x,y, currentEnemy);
					
					this.towerMap[x][y].target = currentEnemy;
					this.towerMap[x][y].attackTime = 0;
					this.towerMap[x][y].attackDelay = 0;
					
					System.out.println("[Tower] Enemy attacked! health: " + currentEnemy.health + "x:" + x+ "y:" + y);
		
				}
			}else{
				this.towerMap[x][y].attackDelay += speed;
				
			}
		}else{
			if(this.towerMap[x][y].attackTime < this.towerMap[x][y].maxAttackTime){
				this.towerMap[x][y].attackTime +=speed;
			}else{
				this.towerMap[x][y].target = null;
				
			}
		}
		
	}
	
	
	public void missileUpdate(){
		for(int i=0; i < missiles.length; i++){
			if(missiles[i] != null){
				missiles[i].update();
				if((missiles[i].target == null)||(missiles[i].target.health <=0)){
					missiles[i] = null;
				}
				
			}
		}
		
	}
	
	
	public void update(){
		enemyUpdate();
		towerUpdate();
		missileUpdate();
		
		if(wave.waveSpawning){
			wave.spawnEnemies();
		}
	}
	
	public void spawnEnemy(int enemyID){
		for(int i = 0; i< enemyMap.length; i++){
			if(enemyMap[i] == null){
				enemyMap[i] = new EnemyMove( Enemy.enemyList[enemyID], level.spawnPoint);
				break;	
			}
		}
	}
	
	public void placeTower(int x, int y){
		int xPos= x / (int) towerSize;
		int yPos= y / (int) towerSize;
		
		
		if(xPos > 0 && xPos <= 22 && yPos <= 14 && yPos > 0 ){
			
			xPos -= 1;
			yPos -= 1;
						
			if(towerMap[xPos][yPos]==null && map[xPos][yPos] == 0){
				user.player.money -= Tower.towerList[hand-1].cost;
				
				towerMap[xPos][yPos] = (Tower) Tower.towerList[hand-1].clone();
				selectedTower = towerMap[xPos][yPos];
			}
		 }
	}
	
	public void selectTower(int x, int y){
		int xPos= x / (int) towerSize;
		int yPos= y / (int) towerSize;
		
		
		if(xPos > 0 && xPos <= 22 && yPos <= 14 && yPos > 0 ){
			
			xPos -= 1;
			yPos -= 1;
						
			selectedTower = towerMap[xPos][yPos];
			
		 }else{
			 if(!(xPos >= 24 && xPos <=30 && yPos >=1 && yPos <=14)){
			 selectedTower = null;
			 }
		 }
	}
	
	public void hitStartRoundButton(){
	boolean flag = false;
				for(int i=0;i<enemyMap.length;i++){
				if(enemyMap[i]!=null){
					flag = true;
				}
			}
				if(!flag){
				wave.nextWave();
				}else{
					if(speed ==1){
						speed =4;
					}else{
					speed = 1;
					}
					}
			}
		
	
	
	
	public class MouseHeld{
		boolean mouseDown = false;
		public void mouseMoved(MouseEvent e) {
			handXPos = e.getXOnScreen();
			handYPos = e.getYOnScreen();
		}

		public void updateMouse(MouseEvent e){
			if(scene==1){
				if(mouseDown && hand ==0){
					if(e.getXOnScreen() >= ((int)((int)(12*towerSize/25)+(int)(12*towerSize/25)+(int)(frameWidth/11.52)+frameWidth/40 + (int)(12*towerSize/25))) && e.getXOnScreen()<=((int) ((int)(12*towerSize/25)+(int)(12*towerSize/25)+(int) frameWidth/11.52)+ frameWidth/40 + (int)(12*towerSize/25) + (18* towerSize))){
						if(e.getYOnScreen()>= (15*(int)towerSize)+(int)(12*towerSize/25) + frameHeightBorder &&e.getYOnScreen() <=(15*(int)towerSize)+(int)(12*towerSize/25)+(int) towerSize *2 + frameHeightBorder){
						for(int i=0; i< Tower.towerList.length;i++){
							if(e.getXOnScreen() >= ((int)((int)(12*towerSize/25)+(int)(12*towerSize/25)+(int)(frameWidth/11.52)+frameWidth/40 + (int)(12*towerSize/25)))+ (int)(i/2)*(int)towerSize && e.getXOnScreen()<=((int) ((int)(12*towerSize/25)+(int)(12*towerSize/25)+(int) frameWidth/11.52)+ frameWidth/40 + (int)(12*towerSize/25) +  towerSize)+ (int)(i/2)*(int)towerSize&& e.getYOnScreen()>= (15*(int)towerSize)+(int)(12*towerSize/25) + frameHeightBorder + (int)(i % 2)*(int)towerSize &&e.getYOnScreen() <=(15*(int)towerSize)+(int)(12*towerSize/25)+(int) towerSize + frameHeightBorder + (int)(i%2)*(int)towerSize ){
								if(user.player.money >= Tower.towerList[i].cost){
									System.out.println("[Shop] You bought a tower for " + Tower.towerList[i].cost + "!");
									hand = i+1;
									
									return;
									}
								}
							}
						}
					}
				}
			}
		}
		public void mouseDown(MouseEvent e) {
			mouseDown=true;
			
			if(hand!=0){
				placeTower(e.getX(), e.getY()- frameHeightBorder);
				
				hand=0;
			}else{
				selectTower(e.getX(), e.getY() - frameHeightBorder);
				
				if(e.getX()>(int)towerSize*111/4 && e.getX()< (int)towerSize * 124/4){
					if(e.getY()>(int)towerSize*31/2 && e.getY()< (int)towerSize * 35/2){
					hitStartRoundButton();
					}
				}
			}
			
			updateMouse(e);
		}
		
	}
	
	public class KeyTyped{
		public void keyESC(){
		running = false;
		}

		public void keyENTER(){
			hitStartRoundButton();
		}
		
		public void keySPASE() {
			startGame(user, "Level1");
			
		}
	}
	
	
}
