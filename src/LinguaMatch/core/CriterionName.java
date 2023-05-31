package LinguaMatch.core;

/**
 * Correspond au nom du critère dans lequel on code un type particulier qui lui est associé
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public enum CriterionName {
	/**
	* Critère du visiteur sur une allergie aux animaux
	*/
	GUEST_ANIMAL_ALLERGY('B'),
	/**
	* Indication de l'hôte sur la présence d'animaux allergènes
	*/
	HOST_HAS_ANIMAL('B'),
	/**
	* Critère du visiteur sur les régimes alimentaires requis
	*/
	GUEST_FOOD('T'),
	/**
	* Indication de l'hôte sur les différents régimes alimentaires disponibles
	*/
	HOST_FOOD('T'),
	/**
	* Critère de l'hôte/visiteur sur les passes-temps
	*/
	HOBBIES('T'),
	/**
	* Critère de l'hôte/visiteur décrivant le genre de l'utilisateur
	*/
	GENDER('T'),
	/**
	* Critère de l'hôte/visiteur exprimant les préférences en terme de genre
	*/
	PAIR_GENDER('T'),
	/**
	* Critère sur la possibilité de correspondre avec un même adolescent que les années précédentes
	*/
	HISTORY('T');
	
	private final char TYPE;
	
	/**
     * Constructeur qui prend en paramètre le type associé aux valeurs du critère en particulier
     * @param type Type du critère
    */
	private CriterionName(char type) {
		this.TYPE = type;
	}

	/**
	 * Retourne le type du nom de critère
	 * @return char représentant le type du nom du critère
	*/
	public char getType() {
		return this.TYPE;
	}

	/**
	 * Retourne le nom complet du type du nom de critère suivant son type en char
	 * @param type
	 * @return le nom complet du type du critère
	*/
	public static String getFullNameType(char type) {
		String out;
		switch(type) {
			case 'B':
				out = "booléen";
				break;
			case 'T':
				out = "texte";
				break;
			default:
				out = "type inconnu";
				break;
		}
		return out;
	}
}