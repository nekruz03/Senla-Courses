package util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import model.Room;
import model.RoomManager;
import org.example.Enum.RoomStatus;
import org.example.Enum.RoomType;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Auxiliary {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public static RoomType getRoomTypeByNumber(int n ){
        if (n == 1){ return RoomType.STANDARD;}
        else if (n == 2){ return RoomType.DELUXE;}
        else if (n == 3){ return RoomType.VIP;}
        return null;
    }
    public static RoomStatus getRoomStatusByNumber(int n ){
        if (n == 1){ return RoomStatus.SERVICED;}
        else if (n == 2) return RoomStatus.REPAIRABLE;
        return null;
    }
    public static  void initializationRoomsFromFile(RoomManager roomManager) throws IOException, CsvValidationException {
        CSVReader csvReader = new CSVReaderBuilder(new FileReader("src/main/java/org/example/data/exports/Rooms.csv"))
                .withSkipLines(1)
                .build();
        String[] lineInArray;
        while ((lineInArray = csvReader.readNext()) != null) {
            int roomNumber = Integer.parseInt(lineInArray[0]);
            RoomStatus roomStatus = RoomStatus.valueOf(lineInArray[1].trim());

            double price = Double.parseDouble(lineInArray[2].trim());

            RoomType roomType = RoomType.valueOf(lineInArray[3].trim());
            int capacity = Integer.parseInt(lineInArray[5].trim());

            int numberOfStars = Integer.parseInt(lineInArray[4].trim());

            Room room = new Room(roomNumber, roomType, roomStatus, price, capacity, numberOfStars);
            roomManager.addMapRoom(roomNumber, room);
        }
    }

    public static  void initializationGuestsFromFile(RoomManager roomManager) throws IOException, CsvValidationException, ParseException {
        CSVReader csvReader = new CSVReaderBuilder(new FileReader("src/main/java/org/example/data/exports/guestListExport.csv"))
                .withSkipLines(1)
                .build();
        String[] lineInArray;
        while ((lineInArray = csvReader.readNext()) != null) {
            int roomNumber = Integer.parseInt(lineInArray[0]);
            Room room =roomManager.getRooms().get(roomNumber);
            String guestName = lineInArray[1].trim();
            String guestSurname = lineInArray[2].trim();
            String passportNumber = lineInArray[3].trim();
            RoomType roomType = RoomType.valueOf(lineInArray[4].trim());
            String dateInput = lineInArray[5].trim();
            Date dateOfOccupation = dateFormat.parse(dateInput);
            String date = lineInArray[6].trim();
            Date dateOfEviction = dateFormat.parse(date);
            if (room.getGuestName() == null && !room.getIsOccupied() && room.getRoomStatus() == RoomStatus.SERVICED){
                room.setOccupied(true);
                room.setGuestName(guestName);
                room.setGuestSurname(guestSurname);
                room.setPassportNumber(passportNumber);
                room.setRoomType(roomType);
                room.setDateOfOccupation(dateOfOccupation);
                room.setDateOfEviction(dateOfEviction);
                room.addGuestToHistory(guestName,guestSurname,passportNumber,dateOfOccupation,dateOfEviction);
            }
            else {
                System.out.println("Failed to add, " + Arrays.toString(lineInArray));
            }
        }
    }
}
