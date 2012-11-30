package com.wat.pz.plot;

public class Watek extends Thread{

	private Plot plot;
	public Watek(/*Plot ma*/){
		/*this.plot = ma;*/
	}
	
	public void run(){

		while(true){

			//plot.repaint();


			try {
				sleep(5000);
				//System.out.println(plot.listaPunktow.size());
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
