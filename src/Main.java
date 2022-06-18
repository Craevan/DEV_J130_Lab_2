import ru.avalon.model.Order;
import ru.avalon.server.DBServer;
import ru.avalon.utils.ConsoleHelper;
import ru.avalon.utils.CredentialsInitializer;

public class Main {

    public static void main(String[] args) throws Exception {
        CredentialsInitializer initializer = new CredentialsInitializer();
        initializer.run();
        Order order = new Order.OrderBuilder()
                .id(3434)
                .creationDate()
                .customerName("TEST")
                .customerPhone("+798234")
                .customerEmail("asd@asd.ru")
                .customerAddress("add 1233")
                .orderState('C')
                .shipmentDate()
                .build();
        DBServer db = DBServer.getInstance();
        ConsoleHelper.writeMessage(db.getAllProduct());
        System.out.println("\n");
        ConsoleHelper.writeMessage(db.getProductsByID(3));
        System.out.println("\n");
        db.addRow(order);
        try {
            db.close();
        } catch (Exception e) {
            ConsoleHelper.writeErrorMessage("Ошибка при закрытии соединения с БД");
            e.printStackTrace();
        }
    }
}
