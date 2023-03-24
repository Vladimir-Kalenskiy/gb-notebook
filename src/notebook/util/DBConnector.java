package notebook.util;

import java.io.File;

public class DBConnector {
    public String DB_PATH;

    public DBConnector(String s) {this.DB_PATH = s;}

    public DBConnector() {this("db.txt");}

    public void createDB() {
        try {
            File db = new File(DB_PATH);
            if (db.createNewFile()) {
                System.out.println("DB created");
            }
            else {
                System.out.println("DB already exists");
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
}
