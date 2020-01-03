package me.flodt.sat.logic;

import java.util.Set;
import java.util.stream.Stream;

public interface AbstractClauseSet extends Iterable<AbstractClause> {
	boolean containsClause(AbstractClause clause);
	boolean isEmpty();
	void addClause(AbstractClause clause);
	void removeClause(AbstractClause clause);
	AbstractClause anyClause();
	boolean isRepetitive();
	void removeLiteralsFromClauses(Set<AbstractLiteral> literals);
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
	int size();
	Stream<AbstractClause> stream();
}
