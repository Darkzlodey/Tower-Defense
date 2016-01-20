package net.darkzlodey.towerdefense;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class Frame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){

		
			public void run() {
				new Frame();
			}
		
		});
		
	}

	public Frame(){
	new JFrame();
	
	//this.setSize(1000,700);
	this.setTitle("Tower Defense");
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setExtendedState(MAXIMIZED_BOTH);
	this.setUndecorated(true);
	this.setResizable(false);
	this.setVisible(true);
	
	Screen screen = new Screen(this);
	this.add(screen);
		
	
	}
}
