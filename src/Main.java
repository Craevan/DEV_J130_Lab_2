import org.apache.derby.iapi.types.SQLDate;
import ru.avalon.model.Order;
import ru.avalon.server.DBServer;
import ru.avalon.utils.ConsoleHelper;
import ru.avalon.utils.Crypt;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws Exception {
//        ConsoleHelper.writeMessage(DBServer.getInstance().getAllProduct());
//        System.out.println("\n\n");
//        ConsoleHelper.writeMessage(DBServer.getInstance().getProductsFromOrder(3));

        Order order = new Order.OrderBuilder()
                .id(123)
                .creationDate("2021-11-23")
                .customerName("TEST")
                .customerPhone("+79888")
                .customerEmail("asd@asd.ru")
                .customerAddress("ololo1233")
                .orderState('P')
                .shipmentDate()
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
