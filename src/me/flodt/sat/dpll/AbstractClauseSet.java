package me.flodt.sat.dpll;

import java.util.Set;

public interface AbstractClauseSet {
	boolean containsClause(AbstractClause clause);
	boolean isEmpty();
	void removeClause(AbstractClause clause);
	boolean containsEmptyClause();
	boolean containsSingleton();
	AbstractClause findFirstSingleton();
	boolean OLRapplicable();
	boolean PLRapplicable();
	Set<AbstractLiteral> literals();
	AbstractLiteral first();
	AbstractLiteral getOLRLiteral();
	AbstractLiteral getPLRLiteral();
	AbstractClauseSet clone();
	void cleanUpWhenTrue(AbstractLiteral literal);
	void cleanUpWhenFalse(AbstractLiteral literal);
}
