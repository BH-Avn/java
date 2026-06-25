# Java Learning & Practice

A collection of Java projects and exercises built while learning the language.

---

## Projects

### Trig Formula Database — `src/completed/Main.java`
A CLI app for storing and searching trigonometric formulas. Formulas are persisted to a flat file (`Formula_database.txt`) and loaded on startup.

**Features:** add · search by expression or keyword · list all · edit · delete

**Run:**
```bash
javac -d bin src/utils/Base.java src/completed/Main.java
java -cp bin completed.Main
```

---

### Linear Equation Solver — `src/completed/equations.java`
Solves single-variable linear equations (e.g. `3x + 2 = 8`) entered as strings.

**How it works:** tokenizes the input → Shunting-Yard algorithm → builds an expression tree → evaluates to find `x`.

**Run:**
```bash
javac -d bin src/completed/equations.java
java -cp bin completed.equations
```

---

## Practice

| File | Contents |
|---|---|
| `src/practice/lc.java` | LeetCode problems (e.g. Contains Duplicate II) |
| `src/practice/learning.java` | Class exercises: OOP, sorting, file I/O, SQLite basics |
| `src/practice/re_1.java` | Recursion + string problems: palindrome, Tower of Hanoi, spiral matrix, anagram, etc. |
| `src/practice/recursion.java` | Recursive fundamentals: digit sum, power, permutations, array reverse |

---

## Structure

```
src/
  completed/      finished, runnable programs
  practice/       exercises and experiments
  utils/          Base.java — shared Scanner, print helpers, input validation
lib/
  sqlite-jdbc-3.53.2.0.jar
docs/
  formula-db-design.md    design notes for the Formula DB
```

---

## Requirements
- Java 11+
- No build tool required — compile manually with `javac` as shown above
- SQLite driver in `lib/` is only needed for the SQLite exercises in `learning.java`

---

## Clone
```bash
git clone https://github.com/BH-Avn/java.git
```
