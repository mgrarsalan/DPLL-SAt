package literal;

import java.util.LinkedList;

public class Clause {
	//will have all the formulas in a clause
	private LinkedList<AtomicFormula> conjuctionFormulas = new LinkedList<AtomicFormula>();
	
	public Clause(LinkedList<AtomicFormula> inputAtomicFormulas)
	{
		for (AtomicFormula atomicFormula : inputAtomicFormulas) {
			conjuctionFormulas.add(atomicFormula);
		}
		if (conjuctionFormulas.size() == 1 && 
				Character.isUpperCase(conjuctionFormulas.get(0).getName())) //Unit propagation
		{
			conjuctionFormulas.peek().setValue(true);
			conjuctionFormulas.peek().setUnit(true);
		}
	}
	
	
	/**
	 * will evaluate the value of the clause
	 */
	public boolean evaluate()
	{
		for (AtomicFormula atomicFormula : conjuctionFormulas) {
			if (atomicFormula.getValue() == true)
				return true;
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		/*String aString = "";
		for (AtomicFormula atomicFormula : conjuctionFormulas) {
			aString += atomicFormula.toString();
		}
		return aString;
		*/
		return conjuctionFormulas.toString();
	}
	
}
