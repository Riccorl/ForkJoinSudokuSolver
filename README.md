# Fork/Join Sudoku Solver
<i>Fork/Join</i> algorithm that count the number of solutions of a given Sudoku in <b>parallel</b>.

### How to use

Build a jar and run

```bash
java -jar sudoku_solver.jar file_name.txt [cutoff_i] [cutoff_j] 
```

#### E.G.

* With standard cutoff
``` sh
java -jar sudoku_solver.jar test/test1_a.txt 
```

* With custom cutoff
``` sh
java -jar sudoku_solver.jar test/test1_a.txt 0 1 
```

### Test
