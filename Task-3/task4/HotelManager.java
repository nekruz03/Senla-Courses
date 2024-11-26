import Enums.RoomStatus;
import Enums.RoomType;
import java.util.ArrayList;

public class HotelManager {
    public static void main(String[] args) {
        RoomManager roomManager = new RoomManager();
        Command command = new Command(roomManager);

        // Добавляем комнаты
        command.addRoom(1, RoomType.STANDARD, RoomStatus.SERVICED, 100.0);
        command.addRoom(2, RoomType.DELUXE, RoomStatus.REPAIRABLE, 200.0);
        command.addRoom(3, RoomType.VIP, RoomStatus.SERVICED, 500.0);
        command.addRoom(4, RoomType.DELUXE, RoomStatus.SERVICED, 250.0);
        // Изменяем цену комнаты
        command.changePrice(1, 150.0);
        // Изменяем тип комнаты
        command.changeType(2, RoomType.VIP);
        // Заселяем гостя
        command.checkIn(2, "ANDREY", "123456");
        // Выселяем гостя
        command.checkOut(2);
        // Удаляем комнату
        command.deleteRoom(1);

        // Выводим все комнаты
        ArrayList<Room> rooms = command.display();
        for (Room room : rooms) {
            System.out.println(room);
        }
    }
}
