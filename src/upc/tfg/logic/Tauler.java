package upc.tfg.logic;

public class Tauler {
	private Districte[] districtes;
	
	public Tauler() {
		districtes = new Districte[10];
		String[] noms = {"Les Corts", "Sarrià Sant Gervasi", "Gràcia", "Horta Guinardo", "Nou Barris", "Sant Andreu", "Sants Montjuic", "Eixample", "Sant Martí", "Ciutat Vella"};
		int valors[] = {7,8,7,9,6,7,7,10,7,8};
		String[] nomsImatges = {};
		
		for (int i = 0; i < 10; ++i){
			//TODO: Afegir imatge districte
			districtes[i] = new Districte(noms[i], valors[i], null);
		}
	}
	
	public void reiniciar(){
		
	}
	
	//Getters & setters

	public Districte[] getDistrictes() {
		return districtes;
	}

	public void setDistrictes(Districte[] districtes) {
		this.districtes = districtes;
	}
}
