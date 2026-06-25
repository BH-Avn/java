# Trig Formula Database — Revised Blueprint (may or may not be followed) 

## Architecture

**Formula Object:**
* `id` : int
* `expression` : String
* `type` : String (identity / formula)
* `category` : String
* `keywords` : String (comma-separated, user-entered)

**Database:**
* `ArrayList<Formula>` → runtime storage

**Main:**
* Menu, user input, calls Database methods

---

## Menu

1. Add formula
2. Search
3. List all
4. Edit
5. Exit

---

## Add Formula Flow

1. Input expression → normalize → `.toLowerCase().replace(" ", "")`
2. Input type
3. Input category
4. Input keywords → comma-separated string (e.g., `"sin,cos,pythagorean,sin^2x"`)
5. `id = lastUsedId + 1`
6. Create `Formula` object
7. `database.add(ob)`
8. `store(ob)` → write to file
9. Update `LAST_ID` in file

---

## Search Flow

1. Input query → normalize → `.toLowerCase().replace(" ", "")`
2. Loop through database:
   * If `f.expression.contains(query)` || `f.category.contains(query)` || `f.keywords.contains(query)` → print match
3. If no matches → "no results found"

---

## List All Flow

* Loop through database → print each:
  `[id] expression | type | category | keywords`

---

## Edit Flow

1. Input id
2. Load formula
3. Show current fields
4. User selects field to edit
5. User enters new value
6. Update in memory
7. Rewrite entire file

---

## File Structure (`formulas.txt`)

```text
LAST_ID:5
---
ID:1
EXPRESSION:sin^2x+cos^2x=1
TYPE:identity
CATEGORY:pythagorean
KEYWORDS:sin,cos,sin^2x,cos^2x,pythagorean
---
ID:2
EXPRESSION:sin2x=2sinxcosx
TYPE:identity
CATEGORY:doubleangle
KEYWORDS:sin,cos,sin2x,sinx,cosx,doubleangle
---
