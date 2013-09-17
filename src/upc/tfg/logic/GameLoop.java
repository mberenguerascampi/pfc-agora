package upc.tfg.logic;

import java.io.IOException;

import upc.tfg.agora.Agora;

public class GameLoop implements Runnable {
	static Agora agora;
	
	public GameLoop(Agora agora) throws IOException {
		this.agora = agora;
	}

	@Override
	public void run() {
		while(true){
			if(agora.finish){
				System.out.println("Finish");
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
