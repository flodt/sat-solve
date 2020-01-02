package me.flodt.sat.dpll;

public class Options {
	private static boolean PLR = true;
	private static boolean OLR = true;

	public static boolean PLRallowed() {
		return PLR;
	}

	public static void allowPLR() {
		PLR = true;
	}

	public static void disallowPLR() {
		PLR = false;
	}

	public static boolean OLRallowed() {
		return OLR;
	}

	public static void allowOLR() {
		OLR = true;
	}

	public static void disallowOLR() {
		OLR = false;
	}

	public static void reset() {
		allowOLR();
		allowPLR();
	}
}
