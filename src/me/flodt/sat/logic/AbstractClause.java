package me.flodt.sat.logic;

import java.util.Set;
import java.util.stream.Stream;

public interface AbstractClause extends Iterable<AbstractLiteral> {
	Set<AbstractLiteral> getContents();
	boolean containsLiteral(AbstractLiteral literal);
	void removeLiteral(AbstractLiteral literal);
	boolean isEmpty();
	boolean isSingleton();
	AbstractLiteral getSingletonLiteral();
	boolean equals(Object o);
	AbstractClause clone();
	Stream<AbstractLiteral> stream();
}
