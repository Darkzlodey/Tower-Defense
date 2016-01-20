package net.darkzlodey.towerdefense;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class KeyHandler implements KeyListener{

	private Screen screen;
	private Screen.KeyTyped keyTyped;
	
	public KeyHandler(Screen screen){
		this.screen = screen;
		this.keyTyped = this.screen.new KeyTyped();
		
	}

	
	public void keyPressed(KeyEvent e) {
		int keyCode= e.getKeyCode();
		
		if(keyCode == 10){
			this.keyTyped.keyENTER();
		}
		
		
		if(keyCode == 27){
			this.keyTyped.keyESC();
		}
		if(keyCode == 32){
			this.keyTyped.keySPASE();
		}
		
	}

	
	public void keyReleased(KeyEvent e) {
		
	}

	
	
	
	public void keyTyped(KeyEvent e) {
		
	}

}
