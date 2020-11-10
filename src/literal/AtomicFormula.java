package literal;

import dpll.Sat;

public class AtomicFormula {
	
	private final char name;
	private boolean value;
	private boolean isUnit; //When the Formula is a lone positive literal in a clause
	
	public boolean isUnit() {
		return isUnit;
	}

	public void setUnit(boolean isUnit) {
		this.isUnit = isUnit;
	}

	public AtomicFormula(char name) {
		
		this.name = name;
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
		for (AtomicFormula currentFormula : Sat.allAtomicFormulas) {
			String nameString = this.getName() + "";
			if (Character.isUpperCase(currentFormula.getName()) && 
					nameString.equalsIgnoreCase(currentFormula.getName() + ""))
				currentFormula.value = this.value;
			else if (Character.isLowerCase(currentFormula.getName()) &&
					nameString.equalsIgnoreCase(currentFormula.getName() + "")) {
				currentFormula.value = !this.value;
			}
		}
	}

	public char getName() {
		return name;
	}
	
	/**
	 * for contain method in the linkedlist objects
	 */
	@Override
	public boolean equals(Object e)
	{
		return 
				this.getName() == ((AtomicFormula)e).getName();
	}
	
	@Override
	public String toString()
	{
		return this.getName() + ": " + this.getValue();
	}
	
}
