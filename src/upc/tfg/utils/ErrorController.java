package upc.tfg.utils;

import upc.tfg.logic.Districte;
import upc.tfg.logic.Partida;

public class ErrorController {
	public static final int DISTRICTE_AMB_MATEIX_NOMBRE_PASSEJANTS = 1;
	public static final int DISTRICTE_NO_ADJACENT = 2;
	public static final int NO_POT_TREURE_PASSEJANT = 3;
	public static final int NO_POT_AFEGIR_PASSEJANT = 4;
	
	public static void showError(int idJugador, int idError, Object arg1, Object arg2){
		String errorText = "";
		switch(idError){
			case DISTRICTE_AMB_MATEIX_NOMBRE_PASSEJANTS:
				errorText = "DISTRICTE AMB EL MATEIX NOMBRE DE PASSEJANTS ->" + ((Districte)arg1).getNom();
				break;
			case DISTRICTE_NO_ADJACENT:
				errorText = "ELS DISTRICTES DE "+ ((Districte)arg1).getNom() + " I " + ((Districte)arg2).getNom() + " NO SÓN ADJACENTS." +" HAS DE MOURE ELS PASSEJANTS ENTRE DOS DISTRICTES QUE ESTIGUIN DE COSTAT.";
				break;
			case NO_POT_TREURE_PASSEJANT:
				errorText = "NO ES POT TREURE CAP PASSEJANT DE COLOR " + Partida.getInstance().getNomColor((int)arg2) +" DEL DISTRICTE DE "+ ((Districte)arg1).getNom();
				break;
			case NO_POT_AFEGIR_PASSEJANT:
				errorText = "NO ES POT AFEGIR CAP PASSEJANT DE COLOR " + Partida.getInstance().getNomColor((int)arg2) +" EN EL DISTRICTE DE "+ ((Districte)arg1).getNom();
				break;
			default:
				break;
		}
		if(idJugador == 1)System.out.println(errorText);
	}
}
