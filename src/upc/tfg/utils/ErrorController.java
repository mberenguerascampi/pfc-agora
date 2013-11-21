package upc.tfg.utils;

import upc.tfg.gui.VistaAlertes;
import upc.tfg.gui.VistaPopUpBotons;
import upc.tfg.logic.Districte;
import upc.tfg.logic.Partida;

public class ErrorController {
	public static final int DISTRICTE_AMB_MATEIX_NOMBRE_PASSEJANTS = 1;
	public static final int DISTRICTE_NO_ADJACENT = 2;
	public static final int NO_POT_TREURE_PASSEJANT = 3;
	public static final int NO_POT_AFEGIR_PASSEJANT = 4;
	public static final int NO_ES_POT_GUARDAR = 5;
	public static final int DISTRICTE_CARTA_AMB_MATEIX_NOMBRE_PASSEJANTS = 6;
	public static final int NO_ES_POT_GUARDAR_NOM_EXISTENT = 7;
	public static final int NO_ES_POT_TIRAR_ENDERRERE = 8;
	
	public static void showError(int idJugador, int idError, Object arg1, Object arg2){
		String errorText = "";
		switch(idError){
			case DISTRICTE_AMB_MATEIX_NOMBRE_PASSEJANTS:
				errorText = "El distrcite de " + ((Districte)arg1).getNom() + " té un empat de màxims passejants";
				break;
			case DISTRICTE_NO_ADJACENT:
				errorText = ((Districte)arg1).getNom() + " i " + ((Districte)arg2).getNom() + " no són adjacents." +" Has de moure el passejant entre dos districtes que estiguin de costat.";
				break;
			case NO_POT_TREURE_PASSEJANT:
				errorText = "No es pot treure cap passejant de color " + Partida.getInstance().getNomColor((int)arg2) +" del districte "+ ((Districte)arg1).getNom();
				break;
			case NO_POT_AFEGIR_PASSEJANT:
				errorText = "No es pot afegir cap passejant de color " + Partida.getInstance().getNomColor((int)arg2) +" en el districte "+ ((Districte)arg1).getNom();
				break;
			case NO_ES_POT_GUARDAR:
				if(idJugador != 1)errorText = "Només pots guardar en el teu torn";
				else errorText = "No pots guardar mentre quedin passejants a afegir";
				VistaPopUpBotons.getInstance().showError(errorText);
				return;
			case NO_ES_POT_GUARDAR_NOM_EXISTENT:
				errorText = "Ja existeix una partida amb aquest nom. Sisplau, tria un atre nom.";
				VistaPopUpBotons.getInstance().showError(errorText);
				return;
			case DISTRICTE_CARTA_AMB_MATEIX_NOMBRE_PASSEJANTS:
				errorText = "No pots afegir " + (int)arg2 + " passejant/s al districte de " + 
						((Districte)arg1).getNom() + " perquè es produiria un empat";
				break;
			case NO_ES_POT_TIRAR_ENDERRERE:
				errorText = "Només pots tirar enderrere si ja has afegit algun passejant a un districte en el pas 2 i 3 durant el teu torn";
				break;
			default:
				break;
		}
		if(idJugador == 1){
			VistaAlertes.getInstance().setWarningText(errorText);
			System.out.println(errorText);
		}
	}
}
