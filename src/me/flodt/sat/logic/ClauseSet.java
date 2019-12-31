package me.flodt.sat.logic;

import java.util.*;

@SuppressWarnings("MethodDoesntCallSuperMethod")
public class ClauseSet implements AbstractClauseSet {
	private final Set<AbstractClause> clauseSet;

	public ClauseSet(Set<AbstractClause> clauseSet) {
		this.clauseSet = clauseSet;
	}

	@Override
	public boolean containsClause(AbstractClause clause) {
		return clauseSet.contains(clause);
	}

	public static AbstractClauseSet empty() {
		return new ClauseSet(new HashSet<>());
	}

	@Override
	public boolean isEmpty() {
		return clauseSet.isEmpty();
	}

	@Override
	public boolean containsEmptyClause() {
		return clauseSet.stream().anyMatch(AbstractClause::isEmpty);
	}

	@Override
	public boolean containsSingleton() {
		return clauseSet.stream().anyMatch(AbstractClause::isSingleton);
	}

	@Override
	public boolean OLRapplicable() {
		return containsSingleton();
	}

	@Override
	public boolean PLRapplicable() {
		//check the set of literals if it only ever occurs positively or negatively
		return literals().stream().anyMatch(this::doesNotContainOppositeLiteralAsWell);
	}

	@Override
	public Set<AbstractLiteral> literals() {
		Set<AbstractLiteral> literals = new HashSet<>();

		clauseSet.stream().map(AbstractClause::getContents).forEach(literals::addAll);

		return literals;
	}

	@Override
	public AbstractLiteral first() {
		return literals().stream().min(AbstractLiteral.LITERAL_COMPARATOR).orElse(null);
	}

	@Override
	public void addClause(AbstractClause clause) {
		clauseSet.add(clause);
	}

	@Override
	public void removeClause(AbstractClause clause) {
		clauseSet.remove(clause);
	}

	@Override
	public AbstractClause anyClause() {
		return clauseSet.iterator().next();
	}

	@Override
	public boolean isRepetitive() {
		AbstractClause compareable = anyClause();

		return clauseSet.stream().allMatch(cl -> cl.equals(compareable));
	}

	@Override
	public void removeLiteralsFromClauses(Set<AbstractLiteral> literals) {
		for (AbstractClause clause : clauseSet) {
			literals.forEach(clause::removeLiteral);
		}
	}

	@Override
	public AbstractClause findFirstSingleton() {
		return clauseSet.stream()
				.filter(AbstractClause::isSingleton)
				.min(Comparator
							 .comparing(clause -> ((Clause) clause).getSingletonLiteral().isPositive())
							 .reversed()
							 .thenComparing(clause -> ((Clause) clause).getSingletonLiteral().descriptor()))
				.orElse(null);
	}

	@Override
	public AbstractLiteral getOLRLiteral() {
		AbstractClause single = findFirstSingleton();
		return single.getContents().stream().findFirst().orElse(null);
	}

	@Override
	public AbstractLiteral getPLRLiteral() {
		return literals()
				.stream().filter(this::doesNotContainOppositeLiteralAsWell).findFirst().orElse(null);
	}

	private boolean doesNotContainOppositeLiteralAsWell(AbstractLiteral literal) {
		return !literals().contains(literal.negated());
	}

	public static ClauseSet of(AbstractClause... clauses) {
		Set<AbstractClause> set = new HashSet<>(Arrays.asList(clauses));
		return new ClauseSet(set);
	}

	@Override
	public int size() {
		return clauseSet.size();
	}

	@Override
	public AbstractClauseSet clone() {
		Set<AbstractClause> newSet = new HashSet<>();

		for (AbstractClause clause : clauseSet) {
			newSet.add(clause.clone());
		}

		return new ClauseSet(newSet);
	}

	@Override
	public void cleanUpWhenTrue(AbstractLiteral literal) {
		//remove all clauses containing literal
		//remove the negated literal from all clauses

		clauseSet.removeIf(cl -> cl.containsLiteral(literal));
		clauseSet.forEach(cl -> cl.removeLiteral(literal.negated()));
	}

	@Override
	public void cleanUpWhenFalse(AbstractLiteral literal) {
		//remove all clauses containing negated literal
		//remove the literal from all clauses

		clauseSet.removeIf(cl -> cl.containsLiteral(literal.negated()));
		clauseSet.forEach(cl -> cl.removeLiteral(literal));
	}

	@Override
	public Iterator<AbstractClause> iterator() {
		return clauseSet.iterator();
	}

	@Override
	public String toString() {
		return "ClauseSet{" +
				"clauses=" + clauseSet +
				'}';
	}
}
