package poo;

public class Criterion {
    private String value;
    private CriterionName label;

    public Criterion(String value, CriterionName label) {
        this.value = value;
        this.label = label;
    }
    
    public CriterionName getLabel(){
        return this.label;
    }

    public boolean isNumeric() {
        try {
            Integer.parseInt(this.value);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    public char getTrueType() {
        if(this.value.equals("yes") || this.value.equals("no")) {
            return 'B';
        } else if(this.isNumeric()) {
            return 'N';
        } else {
            return 'T';
        }
    }

    public boolean isValid(){
        return this.getTrueType() == this.label.getType();
    }
}
