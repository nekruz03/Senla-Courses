package util;
import annotation.OwnInject;
import annotation.OwnInject;
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
}
