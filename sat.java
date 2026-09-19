import java.util.ArrayList;

public class sat {

	public static void main(String[] args) {
		System.out.println(checkSatisfiability(args[0]));
	}
	
	private static String checkSatisfiability(String exp) {
		ArrayList<String> vars = parseString(exp);
		boolean[] vals = new boolean[vars.size()];
		
		for (int i = 0; i < Math.pow(2, vars.size()); i++) {
			int indexEncode = i;
			for (int j = 0; j < vars.size(); j++) {
				if ((indexEncode & 1) == 0) {
					vals[j] = false;
				} else {
					vals[j] = true;
				}
				indexEncode = indexEncode >> 1;
			}
			
			if (recursiveCheck(exp, 0, vars, vals)) {
				String sat = "satisfiable";
				for (int j = 0; j < vars.size(); j++) {
					sat = sat + " " + vars.get(j) + "=" + (vals[j] ? "T" : "F");
				}
				return sat;
			}
		}
		return "unsatisfiable";
	}
	
	private static boolean recursiveCheck(String exp, int expIndex, ArrayList<String> vars, boolean[] vals) {
		if (nextAnd(exp, expIndex) == exp.length() - 1) {
			return orCheck(exp, expIndex, vars, vals);
		} else {
			return (orCheck(exp, expIndex, vars, vals) & recursiveCheck(exp, nextAnd(exp, expIndex) + 1, vars, vals));
		}
	}
	
	private static boolean orCheck(String exp, int expIndex, ArrayList<String> vars, boolean[] vals) {
		//notVar works as a logical not, being on if true and off if false
		//if the value of the next atom is false and notVar is false, f ^ f = f
		//if the next atom is true and notVar is false, t ^ f = t
		//if the next atom is false and notVar is true, f ^ t = t
		//if the next atom is true and notVar is true, t ^ t = f
		
		boolean notVar = false;
		
		if (exp.charAt(expIndex) == '~') {
			notVar = true;
			expIndex++;
		}
		
		int nextOp = nextOperator(exp, expIndex);
		boolean nextAtom = (notVar ^ vals[vars.indexOf(exp.substring(expIndex, nextOp))]);
		if (nextOp >= exp.length() || exp.charAt(nextOp) == '\n') {
			return nextAtom;
		}
		else {
			return (nextAtom | orCheck(exp, nextOp + 1, vars, vals));
		}
	}
	
	private static ArrayList<String> parseString(String exp) {
		ArrayList<String> vars = new ArrayList<String>();
		
		for (int i = 0; i < exp.length(); i++) {
			if (exp.charAt(i) == '~') i++;
			
			int nextIndex = nextOperator(exp, i);
			String atom = exp.substring(i, nextIndex);
			i += (nextIndex - i);
			if (!vars.contains(atom)) {
				vars.add(atom);
			}
		}
		return vars;
	}
	
	private static int nextOperator(String str, int start) {
		for (int i = start; i < str.length(); i++) {
			if (str.charAt(i) == ',' || str.charAt(i) == '\n') {
				return i;
			}
		}
		return str.length();
	}
	
	private static int nextAnd(String str, int start) {
		for (int i = start; i < str.length(); i++) {
			if (str.charAt(i) == '\n') {
				return i;
			}
		}
		return str.length() - 1;
	}
}
