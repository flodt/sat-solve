package me.flodt.sat.dpll;

public class Solver {
	private final Assignment assignments;

	public Solver() {
		assignments = new Assignment();
	}

	public SatisfiabilitySolution solve(AbstractClauseSet clauseSet) {
		clauseSet.literals().forEach(assignments::putDontCare);

		return recursiveSolve(clauseSet);
	}

	public SatisfiabilitySolution recursiveSolve(AbstractClauseSet clauseSet) {
		if (clauseSet.containsEmptyClause()) {
			return new SatisfiabilitySolution();
		}

		if (clauseSet.isEmpty()) {
			return new SatisfiabilitySolution(assignments);
		}

		if (clauseSet.OLRapplicable()) {
			AbstractLiteral olr = clauseSet.getOLRLiteral();
			assignments.putTrueByRule(olr);

			AbstractClauseSet newSet = clauseSet.clone();
			newSet.cleanUpWhenTrue(olr);

			return recursiveSolve(newSet);
		}

		if (clauseSet.PLRapplicable()) {
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

		if (!recursiveSolve(newSet).isSatisfiable()) {
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
		}

		return recursiveSolve(newSet);
	}
}
