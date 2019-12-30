package me.flodt.sat.dpll;

import java.util.Set;

public interface AbstractClause {
	Set<AbstractLiteral> getContents();
	boolean containsLiteral(AbstractLiteral literal);
	void removeLiteral(AbstractLiteral literal);
	boolean isEmpty();
	boolean isSingleton();
	AbstractLiteral getSingletonLiteral();
	AbstractClause clone();
}
