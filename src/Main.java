import ru.avalon.model.Order;
import ru.avalon.server.DBServer;
import ru.avalon.utils.ConsoleHelper;
import ru.avalon.utils.Crypt;

public class Main {
    public static void main(String[] args) {
//        ConsoleHelper.writeMessage(DBServer.getInstance().getAllProduct());
//        System.out.println("\n\n");
//        ConsoleHelper.writeMessage(DBServer.getInstance().getProductsFromOrder(3));

        Order order = new Order.OrderBuilder()
                .id(123)
                .creationDate("2022/11/25")
                .customerName("TEST")
                .customerPhone("+79888")
                .customerEmail("asdasd.ru")
                .customerAddress("ololo1233")
                .orderState('P')
                .build();


        DBServer.getInstance().addOrder(order);

        try {
            DBServer.getInstance().close();
        } catch (Exception e) {
            System.err.println("Ошибка при закрытии соединения с БД");
            e.printStackTrace();
        }
    }
}
