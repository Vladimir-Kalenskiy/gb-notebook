package notebook;

import notebook.controller.UserController;
import notebook.model.User;
import notebook.model.repository.GBRepository;
import notebook.model.repository.impl.UserRepository;
import notebook.util.DBConnector;
import notebook.view.UserView;

public class Main {
    public static void main(String[] args) {
        DBConnector dbConnector = new DBConnector();
        dbConnector.createDB();
        GBRepository repository = new UserRepository(dbConnector);
        UserController controller = new UserController(repository);
        UserView view = new UserView(controller);
        view.run();

//        DBConnector dbConnector1 = new DBConnector("db1.txt");
//        dbConnector1.createDB();
//        GBRepository repository1 = new UserRepository(dbConnector1, "; ");
//        UserController controller1 = new UserController(repository1);
//        UserView view1 = new UserView(controller1);
//        view1.run();
    }


}
