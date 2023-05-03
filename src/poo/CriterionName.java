package poo;

public enum CriterionName {
	GUEST_ANIMAL_ALLERGY,
	HOST_HAS_ANIMAL,
	GUEST_FOOD,
	HOST_FOOD,
	HOBBIES,
	GENDER,
	PAIR_GENDER,
	HISTORY,
	AGE_GAP;
	
	private final char TYPE;
	
	private CriterionName() {
		switch(this.name()) {
			case "GUEST_ANIMAL_ALLERGY":
			case "HOST_HAS_ANIMAL":
				this.TYPE = 'B';
				break;
			case "AGE_GAP":
				this.TYPE = 'N';
				break;
			default:
				this.TYPE = 'T';
				break;
		}
	}
	
	public char getType() {
		return this.TYPE;
	}
}