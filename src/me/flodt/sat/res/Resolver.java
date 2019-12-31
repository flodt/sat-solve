package me.flodt.sat.res;

import me.flodt.sat.logic.*;

import java.util.HashSet;
import java.util.Set;

public class Resolver {
	public Resolution resolve(AbstractClauseSet clauseSet) {
		if (clauseSet.containsEmptyClause()) {
			return new Resolution(true, clauseSet);
		}

		int bound = 32 * clauseSet.size();

		AbstractClauseSet todo = clauseSet.clone();
		AbstractClauseSet added = ClauseSet.empty();

		while (!todo.isEmpty()) {
			AbstractClauseSet todoClone = todo.clone();
			AbstractClauseSet clauseSetClone = clauseSet.clone();

			for (AbstractClause first : todoClone) {
				todo.removeLiteralsFromClauses(first.getContents());

				for (AbstractClause second : clauseSet) {
					for (AbstractLiteral literal : first) {
						if (second.containsLiteral(literal.negated())) {
							//generate resolvent
							Set<AbstractLiteral> union = new HashSet<>();

							first.getContents()
									.stream()
									.filter(lit -> !lit.equals(literal))
									.forEach(union::add);

							second.getContents()
									.stream()
									.filter(lit -> !lit.equals(literal.negated()))
									.forEach(union::add);

							AbstractClause resolvent = new Clause(union);

							if (!clauseSet.containsClause(resolvent)) {
								clauseSetClone.addClause(resolvent);
								added.addClause(resolvent);
							}

							if (resolvent.isEmpty()) {
								return new Resolution(true, clauseSetClone);
							}
						}
					}
				}
			}

			clauseSet = clauseSetClone.clone();
			todo = added.clone();
			added = ClauseSet.empty();

			//if all the clauses in the set are the same and we're above the bound, we're not going anywhere
			if (todo.size() > bound && clauseSet.isRepetitive()) {
				return new Resolution(false, clauseSet);
			}
		}

		return new Resolution(false, clauseSet);
	}
}
