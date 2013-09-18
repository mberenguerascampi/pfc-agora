package upc.tfg.utils;

import java.util.HashMap;
import java.util.Map;

public class CartesBD {
	public static final String[] nomsCartesComplets = {
		"Pl. Barceloneta", "Pl. del dubte", "Pl. de les Olles", "Pl. reial", "Pl. Sant Agustí",
		"Pl. Catalunya", "Pl. Gaudí", "Pl. Letamendi", "Pl. Universitat", "Pl. Urquinaona",
		"Pl. del diamant", "Pl. del nord", "Pl. Revolució setembre 1868", "Pl. Rovira i Trias", "Pl. del sol",
		"Pl. allende", "Pl. boyeros", "Pl. Eivissa", "Pl. meguido", "Pl. Nen rutlla", "Pl. Vall Hebron",
		"Pl. Angel Pestaña", "Pl. Llucmajor", "Pl. Sóller", "Pl. Virreiamat", "Pl. Central",
		"Pl. Islandia", "Pl. Masadas", "Pl. Orfila", "Pl. Robert Gerhard", "Pl. Trinitat", "Pl. Viver",
		"Pl. Carles Buigas", "Pl. Cerdà", "Pl. Font i Sagué", "Pl. les Glòries", "Pl. Maresme", "Pl. Marina i Sants", "PL. Països Catalans", "Pl. Palmeras Sant Martí", "PL. General Prim", "Pl. Sants", "Pl. Serrat",
		"Pl. Artós", "Pl. Borràs", "Pl. Molina", "Pl. Sarrià", "PL. Vallvidrera"};
	
	public static final String[] nomsCartes = {
		"cv_pl_barceloneta","cv_pl_dubte","cv_pl_olles","cv_pl_reial","cv_pl_santagusti",
		"ei_pl_catalunya", "ei_pl_gaudi", "ei_pl_letamendi", "ei_pl_universitat", "ei_pl_urquinaona",
		"gr_pl_diamant", "gr_pl_nord", "gr_pl_revoluciosetembre1868", "gr_pl_roviraItrias", "gr_pl_sol",
		"hg_pl_allende", "hg_pl_boyeros", "hg_pl_eivissa", "hg_pl_meguido", "hg_pl_nenrutlla", "hg_pl_vallhebron",
		"nb_pl_angelpestaña", "nb_pl_llucmajor", "nb_pl_soller", "nb_pl_virreiamat", "nb_pl_central",
		"sa_pl_islandia", "sa_pl_masadas", "sa_pl_orfila", "sa_pl_robertgerhard", "sa_pl_trinitat", "sa_pl_viver",
		"sm_pl_carlesbuigas", "sm_pl_cerda", "sm_pl_fontisague", "sm_pl_glories", "sm_pl_maresme", "sm_pl_marinasants", "sm_pl_paisoscatalans", "sm_pl_palmerasantmarti", "sm_pl_prim", "sm_pl_sants", "sm_pl_serrat",
		"ss_pl_artos", "ss_pl_borras", "ss_pl_molina", "ss_pl_sarria", "ss_pl_vallvidrera"};
	
	public static final int[] valorsCartes = {
		3,4,2,3,1,
		3,5,1,2,3,
		3,1,4,2,3,
		3,3,4,1,5,2,
		5,3,3,4,2,
		1,2,3,5,3,4,
		1,4,2,3,4,3,5,3,5,2,1,
		2,4,1,3,5};
	
	public static final HashMap<String,String> mapdDistrictes  = new HashMap<String, String>(){{
        put("cv","Ciutat vella");
        put("ei","Eixample");
        put("gr","Gràcia");
        put("hg","Horta Guinardo");
        put("nb","Nou Barris");
        put("sa","Sant Andreu");
        put("sm","Sant Martí");
        put("ss","Sarrià Sant Gervasi");
//        put("cv","Ciutat vella");
//        put("cv","Ciutat vella");
    }};
}
