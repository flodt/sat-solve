package me.flodt.sat.dpll;

import me.flodt.sat.logic.AbstractClauseSet;
import me.flodt.sat.logic.AbstractLiteral;

public class Solver {
	private final Assignment assignments;

	public Solver() {
		assignments = new Assignment();
	}

	public SatisfiabilitySolution solve(AbstractClauseSet clauseSet) {
		AbstractClauseSet original = clauseSet.clone();

		clauseSet.literals().forEach(assignments::putDontCare);

		SatisfiabilitySolution solution = recursiveSolve(clauseSet);
		solution.setClauseSet(original);

		if (solution.isSatisfiable()
				&& !isSatisfiableUnderAssignment(solution.getClauseSet(), solution.getAssignments())) {
			throw new IllegalStateException("Invalid solution");
		}

		return solution;
	}

	private SatisfiabilitySolution recursiveSolve(AbstractClauseSet clauseSet) {
		if (Options.debugAllowed()) {
			System.out.println(clauseSet.size() + " clauses in set");
		}

		if (clauseSet.containsEmptyClause()) {
			return new SatisfiabilitySolution();
		}

		if (clauseSet.isEmpty()) {
			return new SatisfiabilitySolution(assignments);
		}

		if (Options.OLRallowed() && clauseSet.OLRapplicable()) {
			AbstractLiteral olr = clauseSet.getOLRLiteral();
			assignments.putTrueByRule(olr);

			AbstractClauseSet newSet = clauseSet.clone();
			newSet.cleanUpWhenTrue(olr);

			return recursiveSolve(newSet);
		}

		if (Options.PLRallowed() && clauseSet.PLRapplicable()) {
			AbstractLiteral plr = clauseSet.getPLRLiteral();
			assignments.putTrueByRule(plr);

			AbstractClauseSet newSet = clauseSet.clone();
			newSet.cleanUpWhenTrue(plr);

			return recursiveSolve(newSet);
		}

		AbstractLiteral literal = clauseSet.first();

		//set to true and clean up
		assignments.putTrue(literal);
		AbstractClauseSet newSet = clauseSet.clone();
		newSet.cleanUpWhenTrue(literal);

		SatisfiabilitySolution rest = recursiveSolve(newSet);

		if (!rest.isSatisfiable()) {
			//otherwise set to false and clean up

			//clean up the assignment map to preserve DONT_CAREs (was changed by recursion)
			assignments.getAsMap()
					.keySet()
					.stream()
					.sorted(AbstractLiteral.LITERAL_COMPARATOR)
					.filter(lit -> AbstractLiteral.LITERAL_COMPARATOR.compare(lit, literal) > 0)
					.filter(lit -> !assignments.isFixedByRule(lit))
					.forEach(assignments::putDontCare);

			//set literal to false and clean up
			assignments.putFalse(literal);
			newSet = clauseSet.clone();
			newSet.cleanUpWhenFalse(literal);

			rest = recursiveSolve(newSet);
		}

		return rest;
	}

	private static boolean isSatisfiableUnderAssignment(AbstractClauseSet clauseSet, Assignment assignment) {
		return clauseSet.stream()
				.allMatch(clause -> clause.stream().anyMatch(lit -> assignment.valueOf(lit).couldBeTrue()));
	}
}
