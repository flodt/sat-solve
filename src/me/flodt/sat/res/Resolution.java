package me.flodt.sat.res;

import me.flodt.sat.logic.AbstractClauseSet;

import java.util.Objects;

public class Resolution {
	private final boolean emptyClause;
	private final AbstractClauseSet clauseSet;

	public Resolution(boolean containsEmptyClause, AbstractClauseSet clauseSet) {
		this.emptyClause = containsEmptyClause;
		this.clauseSet = clauseSet;
	}

	public boolean isContradictory() {
		return emptyClause;
	}

	public AbstractClauseSet getClauseSet() {
		return clauseSet;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Resolution that = (Resolution) o;
		return emptyClause == that.emptyClause &&
				Objects.equals(clauseSet, that.clauseSet);
	}

	@Override
	public int hashCode() {
		return Objects.hash(emptyClause, clauseSet);
	}

	@Override
	public String toString() {
		return "Resolution{" +
				"isContradictory=" + emptyClause +
				", clauseSet=" + clauseSet +
				'}';
	}
}
