package app;
import Comparators.Comparators;
import Controller.Command;
import Enums.RoomStatus;
import Enums.RoomType;
import Enums.ServiceType;
import Model.Room;
import Model.RoomManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HotelManager {
    public static void main(String[] args) throws Exception {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            RoomManager roomManager = new RoomManager();
            Command command = new Command(roomManager);
            Comparators comparators = new Comparators();
            // Добавляем комнаты
            command.addRoom(1, RoomType.STANDARD, RoomStatus.SERVICED, 100.0,1,2);
            command.addRoom(2, RoomType.DELUXE, RoomStatus.REPAIRABLE, 200.0,2,3);
            command.addRoom(3, RoomType.VIP, RoomStatus.SERVICED, 500.0,1,2);
            command.addRoom(4, RoomType.DELUXE, RoomStatus.SERVICED, 250.0,2,4);
            // Изменяем цену комнаты
            command.changePrice(1, 150.0);
            // Изменяем тип комнаты
            command.changeType(2, RoomType.VIP);
            // Заселяем гостя
            command.checkIn(1,"Nekruz","321123",dateFormat.parse("8.11.2024"),dateFormat.parse("9.11.2024"));

            command.checkIn(3, "Sasha", "123456",dateFormat.parse("24.11.2024"), dateFormat.parse("01.01.2025"));
            command.checkIn(4, "Andrey", "21233",dateFormat.parse("21.11.2024"), dateFormat.parse("31.12.2024"));
            // Выселяем гостя
           // command.checkOut(2);
            // Изменяем статус комнаты
            command.changeStatus(2, RoomStatus.SERVICED);
            command.checkIn(2,"Nikita","344531",dateFormat.parse("5.11.2024"),dateFormat.parse("6.11.2024"));
            command.changeStatus(3, RoomStatus.REPAIRABLE);
            // Удаляем комнату
          //  command.deleteRoom(1);
            ArrayList<Room> sortedRooms  = roomManager.getAllRooms();
            // сортировка по цене
            sortedRooms.sort(comparators.byPrise);
            // сортировка по вместимости
            sortedRooms.sort(comparators.byCapasity);
            //сортировка по количеству звезд
            sortedRooms.sort(comparators.byNumberOfStars);
            // Выводим все комнаты
            ArrayList<Room> listOfAvailableRooms = command.getListOfAvailableRooms();
            // сортировка, список свободных номеров по цене
            listOfAvailableRooms.sort(comparators.byPrise);
            // сортировка, список свобдобных номеров по вместимости
            listOfAvailableRooms.sort(comparators.byCapasity);
            // сортировка, список свобдобных номеров по количеству звезд
            listOfAvailableRooms.sort(comparators.byNumberOfStars);
            // получение список постоялцев
            ArrayList<Room> getListOfGuests = command.getListOfGuests();
            // cортировка,список постоялцев по алфавиту
            getListOfGuests.sort(comparators.byName);
            //сортировка по дате выселение
            getListOfGuests.sort(comparators.byDateOfEviction);
            //количесво  свободных номеров
            command.numberOfFreeRooms();
            //количество гостей
            command.numberOfGuests();
            //добавление доп услуг в номер
            command.addingAdditionalServices(2, List.of(new ServiceType[]{ServiceType.CLEANING,ServiceType.BREAKFAST}));
            command.addingAdditionalServices(3, List.of(new ServiceType[]{ServiceType.SPA}));
            // cсписок номеров, которые будут свободными по определенном дате
            ArrayList<Room> tmp = (ArrayList<Room>) command.getListOfVailableRoomsInDate(dateFormat.parse("01.11.2024"));
            //сумма за номер , должен оплатить гость
            command.addingAdditionalServices(1, List.of(new ServiceType[]{ServiceType.CLEANING,ServiceType.BREAKFAST}));
            double  a = command.priseColculation(1);

            //System.out.println(a);
            // посмотр 3 последних гостей, номера и дата их прибывание
             ArrayList<Room> cur = (ArrayList<Room>) command.viewTheLastThreeGuests();

            //сортировка доп услуг по цене
            List<ServiceType> serviceTypes = ServiceType.getServiceTypeList();
            serviceTypes.sort(comparators.bуServicePrise);
            System.out.println(cur);
//            for(Room room : getListOfGuests){
//                    System.out.println(room.getGuestName() +" " + room.getRoomNumber() +" " + dateFormat.format(room.getDateOfOccupation()) + " " + dateFormat.format(room.getDateOfEviction()));
//            }

//            for (Room room : listOfAvailableRooms) {
//                System.out.println(room);
//            }


    }
}