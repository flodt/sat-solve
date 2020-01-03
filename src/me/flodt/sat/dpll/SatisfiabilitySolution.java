package me.flodt.sat.dpll;

import me.flodt.sat.logic.AbstractClauseSet;
import me.flodt.sat.logic.AbstractLiteral;

public class SatisfiabilitySolution {
	private final boolean satisfiable;
	private final Assignment assignments;
	private AbstractClauseSet clauseSet;

	public SatisfiabilitySolution() {
		satisfiable = false;
		assignments = null;
		clauseSet = null;
	}

	public SatisfiabilitySolution(Assignment assignments) {
		satisfiable = true;
		this.assignments = assignments;
		clauseSet = null;
	}

	public boolean isSatisfiable() {
		return satisfiable;
	}

	public Assignment getAssignments() {
		return assignments;
	}

	public void setClauseSet(AbstractClauseSet clauseSet) {
		if (this.clauseSet != null) {
			throw new IllegalStateException("Clause set inside solution is immutable");
		}

		this.clauseSet = clauseSet;
	}

	public AbstractClauseSet getClauseSet() {
		return clauseSet;
	}

	public Value valueOf(AbstractLiteral literal) {
		if (assignments != null) {
			return assignments.valueOf(literal);
		} else {
			return Value.INVALID;
		}
	}

	@Override
	public String toString() {
		return "SatisfiabilitySolution{" +
				"satisfiable=" + satisfiable +
				", assignments=" + ((assignments == null) ? ("impossible") : (assignments)) +
				'}';
	}
}
