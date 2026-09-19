name: Norman Sackett
email: nsackett@u.rochester.edu

This program checks the satisfiability of a list of atoms with NOT, AND, or OR logical operations being performed on them. Running the program should be done in the form:
java sat $'A,B\n~A\nC,~B'
where, in this case, A, B, and C are the atoms. "," represents OR, "\n" represents AND, and "~" represents not. The program will either return unsatisfiable if there is no combination of truth values for the atoms that makes the entire proposition true, or satisfiable followed by the truth values that make it so.
