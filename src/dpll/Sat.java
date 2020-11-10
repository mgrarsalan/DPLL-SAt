package dpll;

import java.util.LinkedList;
import java.util.Scanner;

import literal.AtomicFormula;
import literal.Clause;

public class Sat {
	//Contains all the atomic formulas in the sentence
		public static LinkedList<AtomicFormula> allAtomicFormulas = new LinkedList<AtomicFormula>();
		
		//Contains all the clauses in the sentence
		public static LinkedList<Clause> clauses = new LinkedList<Clause>();
		
		public static void main(String[] args) {
			/*
			 * Parsing the input
			 */
			System.out.println("Please enter the CNF Sentence.\n");
			Scanner scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			LinkedList<AtomicFormula> clauseFormulas = new LinkedList<AtomicFormula>();//will contain the atomic formulas in a clause
			Clause currentClause;//for the loop
			for (int i = 1; i < input.length() - 1; i++) {
				//checking if the literal exist
				if (i < input.length() - 2 && (input.charAt(i) == ')' && input.charAt(i+1) == '^' && input.charAt(i+2) =='('))
				{
					i += 2;
					currentClause = new Clause(clauseFormulas);
					clauses.add(currentClause);
					
					//making them empty for the next clause
					clauseFormulas.clear();
					currentClause = null;
				}
				else if (input.charAt(i) != '~' && input.charAt(i) != 'v') {
					AtomicFormula formula;
					if (input.charAt(i-1) == '~')
						formula =  new AtomicFormula(Character.toLowerCase(input.charAt(i)));
					else
						formula = new AtomicFormula(input.charAt(i));
					allAtomicFormulas.add(formula);
					clauseFormulas.add(formula); //clause must have it wheter it's new or not
					
				}
			}
			
			//for the last implication:
			currentClause = new Clause(clauseFormulas);
			clauses.add(currentClause);
			
			LinkedList<AtomicFormula> postiveFormulas = positiveFormulas();
			LinkedList<AtomicFormula> positiveFormualWithoutUnits = new LinkedList<AtomicFormula>(postiveFormulas);
			//removing unit formulas
			for (int i = 0; i < positiveFormualWithoutUnits.size(); i++) {
				if (positiveFormualWithoutUnits.get(i).isUnit())
				{
					positiveFormualWithoutUnits.remove(i);
					i--;
				}
					
			}
			
			//if All the clauses are unit formulas then it's SAT
			if (positiveFormualWithoutUnits.size() == 0)
				printSat(postiveFormulas);
				
			
			try {
				backtracking(positiveFormualWithoutUnits, 0);
			} catch (IllegalArgumentException e) {
				printSat(postiveFormulas);
			}
			
			//if not catched then it's not sat
			System.out.println("It's NOT a SAT CNF sentence!");
			
	}
		
		
		/**
		 * @return all the positive formulas in the sentence
		 */
		public static LinkedList<AtomicFormula> positiveFormulas()
		{
			LinkedList<AtomicFormula> positive = new LinkedList<AtomicFormula>();
			for (AtomicFormula atomicFormula : allAtomicFormulas) {
				if (positive.contains(atomicFormula) ||
						positive.contains(new AtomicFormula(
								Character.toUpperCase(atomicFormula.getName()))))
					continue;
				
				if (Character.isUpperCase(atomicFormula.getName()))
					positive.add(atomicFormula);
				else {
					AtomicFormula counterFormula = new AtomicFormula(Character.toUpperCase(atomicFormula.getName()));
					positive.add(counterFormula);
				}
			}
			return positive;
		}
		
		public static void backtracking(LinkedList<AtomicFormula> positveFormulas,int index)
		{
			if (index == positveFormulas.size())
				return;
			for (int i = index; i < positveFormulas.size(); i++)
			{

				for (int j = 0; j < 2; j++) {
					boolean sat = true;
					if (j == 0)
						positveFormulas.get(i).setValue(true);
					else
						positveFormulas.get(i).setValue(false);
					backtracking(positveFormulas, index + 1);
					
					for (Clause currentClause : clauses) {
						if (!currentClause.evaluate())
						{
							sat = false;
							break;
						}
					}
					if (sat)
						throw new IllegalArgumentException();
				}

			}
		}
		
		public static void printSat(LinkedList<AtomicFormula> positive) 
		{
			System.out.println("The CNF is SAT when the value of formulas are: \n" + positive); 
			System.exit(0);
		}
}
