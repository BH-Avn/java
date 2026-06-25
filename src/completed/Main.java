package completed;

import java.io.*;
import java.util.*;
import static utils.Base.*;

class Formula {

    int id;
    String expression;
    String type;
    String category;
    ArrayList<String> key;

    Formula(int id, String ex, String type, String category, ArrayList<String> s) {
        this.id = id;
        this.expression = ex;
        this.type = type;
        this.category = category;
        key = s;

    }

    void addKeywords() {
        int count = key.size() + 1;
        pl("ONLY ENTER SINGLE WORD and DO NOT ENTER ','  IN KEYWORDS ");

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (true) {
            pl("Enter Keyword " + count + ":");
            String s = scanner.nextLine().trim().replace(" ", "").replace(",", "").toLowerCase();

            if (s.isEmpty()) {
                continue; // Will prompt for the exact same count number again
            }

            this.key.add(s);
            count++; // Increment only after a successful addition

            pl("1-Add more keyword\n2-Exit ");

            // Use nextLine() to read the choice to avoid leaving trailing newlines in the
            // buffer
            String choice = scanner.nextLine().trim();
            if (choice.equals("1")) {
                continue;
            } else {
                return;
            }
        }
    }

    void plKeywords() {
        p("Keyword - {");
        for (int i = 0; i < key.size(); i++) {
            if (i != key.size() - 1) {
                p(key.get(i) + ", ");
            } else {
                p(key.get(i));
            }
        }
        pl("}");
    }

    void info() {
        p("Id => " + this.id + " | ");
        p("Expression => " + this.expression + " | ");
        p("Type => " + this.type + " | ");
        p("Category => " + this.category + "|");
        plKeywords();
    }

}

class Database {

    ArrayList<Formula> database = new ArrayList<>();
    int lastIndex = 0;

    void sort_ID() {
        if (!database.isEmpty()) {
            int size = database.size();
            for (int i = 0; i < size; i++) {
                database.get(i).id = i + 1;
            }
            lastIndex = size;
        }
    }

    void load() {
        if ((new File("Formula_database.txt")).exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader("Formula_database.txt"))) {
                String line;
                String firstLine = br.readLine();
                if (firstLine != null) {
                    lastIndex = Integer.parseInt(firstLine.substring(firstLine.indexOf(":") + 1));
                }
                while ((line = br.readLine()) != null) {
                    int id;
                    String expression, type, category;

                    String temp1[] = line.split("\\<PIPE>");
                    // ID:1|EXPRESSION:a+b|TYPE:type|CATEGORY:category|KEYWORDS:{A,B,C}

                    id = Integer.parseInt(temp1[0].substring(temp1[0].indexOf(":") + 1));
                    expression = temp1[1].substring(temp1[1].indexOf(":") + 1);
                    type = temp1[2].substring(temp1[2].indexOf(":") + 1);
                    category = temp1[3].substring(temp1[3].indexOf(":") + 1);
                    ArrayList<String> list = new ArrayList<>(Arrays
                            .asList((temp1[4].substring(temp1[4].indexOf("{") + 1, temp1[4].indexOf("}"))).split(",")));
                    Formula ob = new Formula(id, expression, type, category, list);
                    database.add(ob);

                }
                if (database.isEmpty()) {
                    ArrayList<String> temp = new ArrayList<>(List.of("sin^2", "cos^2", "1"));
                    database.add(new Formula(1, "sin^2x+cos^2x=1", "identity", "pythagorean identity", temp));
                }
            } catch (IOException e) {
                System.out.println("Error loading file , please contact administrator!\nExiting Program..");
                System.exit(0);
            }
        } else {
            try (PrintWriter pw = new PrintWriter(new FileWriter("Formula_database.txt"), true);) {
                String default_Formula = "ID:1<PIPE>EXPRESSION:sin^2x+cos^2x=1<PIPE>TYPE:identity<PIPE>CATEGORY:pythagorean identity<PIPE>KEYWORDS:{sin^2,cos^2,1}";
                pw.println("LASTINDEX:1");
                pw.println(default_Formula);
                lastIndex = 1;
                ArrayList<String> temp = new ArrayList<>(List.of("sin^2", "cos^2", "1"));
                database.add(new Formula(1, "sin^2x+cos^2x=1", "identity", "pythagorean identity", temp));
            } catch (IOException e) {
                System.out.println("Error loading file , please contact administrator!\nExiting Program");
                System.exit(0);
            }
        }
    }

    void add() {
        pl("Enter Expression:");
        String ex = scanner.nextLine().replace(" ", "").toLowerCase();
        pl("Enter Type:");
        String ty = scanner.nextLine().replace(" ", "").toLowerCase();
        pl("Enter category:");
        String ca = scanner.nextLine().replace(" ", "").toLowerCase();

        ArrayList<String> keywords = new ArrayList<>();
        Formula ob = new Formula(lastIndex + 1, ex, ty, ca, keywords);
        p("Do you want to add keywords? ");
        if (checkYN() == 0)

            ob.addKeywords();

        scanner.nextLine();

        // pl("Do you want to edit anything scanner this formula ? ");
        // if (checkYN() == 0)
        // edit(ob);
        database.add(ob);
        lastIndex++;

    }

    void search_Ex(String searchWord) {
        int count = 0;
        pl("Search Results");
        for (int i = 0; i < database.size(); i++) {
            if (database.get(i).expression.contains(searchWord)) {
                pl(count + 1 + "=> " + database.get(i).expression);
                count++;
            }
        }
        if (count == 0)
            pl("None !");
        System.out.println("Press 1 to return to the main menu");
        while (true) {

            if (safeNextInt() == 1) {
                return;
            } else {
                System.out.println("Press 1 to return to the main menu , you are dumb aren't you ?");
                continue;
            }
        }
    }

    void search_Key(String searchWord) {
        int count = 0;
        pl("Search Results");
        for (int i = 0; i < database.size(); i++) {
            for (int j = 0; j < database.get(i).key.size(); j++) {
                if (database.get(i).key.get(j).contains(searchWord)) {
                    p(count + 1 + "=> ");
                    database.get(i).info();
                    count++;
                }
            }
        }
        if (count == 0)
            pl("None !");
        System.out.println("Press 1 to return to the main menu");
        while (true) {

            if (safeNextInt() == 1) {
                return;
            } else {
                System.out.println("Press 1 to return !");
                continue;
            }
        }

    }

    void list() { // Id => 1 | Expression => sin2x=2sinxcosx | Type => identity | Category =>
                  // double angle |
        sort_ID();
        if (database.isEmpty()) {
            pl("Empty Database!");
        } else {
            pl("List =>");
            for (int i = 0; i < database.size(); i++) {
                database.get(i).info();
            }
        }
    }

    void edit() {
        while (true) {
            sort_ID();
            pl("1-Edit a formula\n2-Delete a formula\n3-Exit Editing Menu");
            int choice = safeNextInt();

            if (choice == 1) {
                list();
                System.out.println("Enter ID of formula to edit:");

                int editId = safeNextInt();

                Formula ob = null;

                for (int i = 0; i < database.size(); i++) {
                    if (database.get(i).id == editId) {
                        ob = database.get(i);
                        break;
                    }
                }

                if (ob != null) {
                    pl("What do you want to edit?\n1-Expression\n2-Type\n3-Category\n4-Add Keywords\n5-Back to Editing Menu");
                    int choice1 = safeNextInt();

                    switch (choice1) {
                        case 1:
                            System.out.println("Enter New Expression");
                            ob.expression = scanner.nextLine().replace(" ", "").toLowerCase();
                            pl("Successful edit!");
                            break;
                        case 2:
                            System.out.println("Enter New Type");
                            ob.type = scanner.nextLine().replace(" ", "").toLowerCase();
                            pl("Successful edit!");
                            break;
                        case 3:
                            System.out.println("Enter New Category");
                            ob.category = scanner.nextLine().replace(" ", "").toLowerCase();
                            pl("Successful edit!");
                            break;
                        case 4:
                            ob.addKeywords();
                            pl("Successful edit!");
                            break;
                        case 5:
                            continue;
                        default:
                            System.out.println("Invalid Entry!");
                    }
                    save();
                } else {
                    System.out.println("Formula ID not found.");
                }

            } else if (choice == 2) {

                list();
                System.out.println("Enter ID of formula to delete (this action cannot be undone!):");
                int deleteId = safeNextInt();

                boolean found = false;

                for (int i = 0; i < database.size(); i++) {
                    if (database.get(i).id == deleteId) {
                        database.remove(i);
                        found = true;
                        save();
                        pl("Formula Deleted.");
                        break;
                    }
                }
                if (!found)
                    pl("Invalid Id!");
                sort_ID();
            } else if (choice == 3) {
                pl("Exiting Editing Menu..");
                return;
            } else {
                pl("Invalid Choice!");
            }
        }
    }

    void save() {

        try (PrintWriter pw = new PrintWriter(new FileWriter("Formula_database.txt"));) {

            pw.println("LASTINDEX:" + lastIndex);
            if (database.isEmpty()) {
                ArrayList<String> temp = new ArrayList<>(List.of("sin^2", "cos^2", "1"));
                database.add(new Formula(1, "sin^2x+cos^2x=1", "identity", "pythagorean identity", temp));

            }
            String type, category, expression;
            int id;
            String keywords;

            for (int i = 0; i < database.size(); i++) {
                Formula ob = database.get(i);
                id = ob.id;
                type = ob.type;
                category = ob.category;
                expression = ob.expression;
                keywords = ob.key.toString().replace("[", "").replace("]", "");
                pw.println("ID:" + id + "<PIPE>EXPRESSION:" + expression + "<PIPE>TYPE:" + type + "<PIPE>CATEGORY:"
                        + category
                        + "<PIPE>KEYWORDS:{" + keywords + "}");

            }

        } catch (IOException e) {
            System.out.println("Error saving the formulas , contact administrator!\nExiting Program..");
            System.exit(0);
        }
    }

}

public class Main {

    public static void main(String[] args) {
        Database db = new Database();

        db.load();

        while (true) {

            System.out.println("1. Add Formula\n2. Search\n3. List All\n4. Edit\n5. Exit");

            int choice = safeNextInt();

            switch (choice) {
                case 1:
                    // 2. ADD FORMULA: Completely handled by Database.add()
                    db.add();
                    db.save();
                    break;

                case 2:
                    // 3. SEARCH: Requires a sub-menu to route to the correct search function.
                    System.out.println("Search by: 1. Expression  2. Keyword");
                    int searchType = safeNextInt();

                    System.out.println("Enter search term:");
                    String term = scanner.nextLine().trim().toLowerCase();

                    if (searchType == 1) {
                        db.search_Ex(term);
                    } else if (searchType == 2) {
                        db.search_Key(term);
                    } else {
                        System.out.println("Invalid search type.");
                    }
                    break;

                case 3:
                    // 4. LIST: Completely handled by Database.list()
                    db.list();
                    break;

                case 4:
                    // 5. EDIT: You must find the specific Formula object before passing it to
                    // edit().
                    db.edit();
                    db.save();
                    break;

                case 5:
                    // 6. TERMINATION: Must save memory to disk before exiting to prevent data loss.
                    db.save();
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;

                default:
                    scanner.close();
                    System.out.println("Invalid choice.");
            }
        }

    }
}
