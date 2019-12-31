package me.flodt.sat.dpll;

import me.flodt.sat.logic.Clause;
import me.flodt.sat.logic.ClauseSet;
import me.flodt.sat.logic.Literal;

public class Caller {
	public static void main(String[] args) {
		//experimentation here
	}

	private static void exampleOne() {
		Literal A = new Literal(true, "A");
		Literal B = new Literal(true, "B");
		Literal C = new Literal(true, "C");
		Literal D = new Literal(true, "D");

		Literal NOT_A = A.negated();
		Literal NOT_B = B.negated();
		Literal NOT_C = C.negated();
		Literal NOT_D = D.negated();

		ClauseSet clauseSet = ClauseSet.of(
				Clause.of(C, NOT_A, NOT_D),
				Clause.of(D, NOT_A, NOT_C),
				Clause.of(A, C, NOT_B),
				Clause.of(C, D, NOT_A),
				Clause.of(NOT_A, NOT_C, NOT_D),
				Clause.of(A, B, NOT_C),
				Clause.of(A, B, C)
		);

		SatisfiabilitySolution solution = new Solver().solve(clauseSet);
		System.out.println(solution.toString());
	}

	private static void exampleTwo() {
		Literal p = new Literal(true, "p");
		Literal q = new Literal(true, "q");
		Literal t = new Literal(true, "t");
		Literal u = new Literal(true, "u");
		Literal y = new Literal(true, "y");

		Literal not_p = p.negated();
		Literal not_q = q.negated();
		Literal not_t = t.negated();
		Literal not_u = u.negated();
		Literal not_y = y.negated();

		ClauseSet clauseSet = ClauseSet.of(
				Clause.of(u),
				Clause.of(p, not_y),
				Clause.of(y, not_t, not_u, not_q),
				Clause.of(not_y, not_q),
				//Clause.of(y, p, not_t, not_u), //forgot this one
				Clause.of(y, q, not_p),
				Clause.of(t),
				Clause.of(q, not_t, not_y, not_u, not_p)
		);

		SatisfiabilitySolution solution = new Solver().solve(clauseSet);
		System.out.println(solution.toString());
	}

	private static void exampleThree() {
		System.out.println(
				new Solver().solve(
						ClauseSet.of(
								Clause.of(
										new Literal(true, "a"),
										new Literal(true, "b")
								)
						)
				).toString()
		);
	}
}
