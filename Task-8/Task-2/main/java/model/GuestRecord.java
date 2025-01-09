package model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.text.SimpleDateFormat;
import java.util.Date;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GuestRecord {
    private String guestName;
    private String guestSurname;
    private String passportNumber;
    private Date dateOfOccupation;
    private Date dateOfEviction;
    @JsonIgnore
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public GuestRecord() {}
    public GuestRecord(String guestName, String guestSurname, String passportNumber, Date dateOfOccupation, Date dateOfEviction) {
        this.guestName = guestName;
        this.guestSurname = guestSurname;
        this.passportNumber = passportNumber;
        this.dateOfOccupation = dateOfOccupation;
        this.dateOfEviction = dateOfEviction;
    }
    @Override
    public String toString() {
        return "guestName: " + guestName +
                " guestSurname: " + guestSurname +
                " passportNumber: " + passportNumber +
                " dateOfOccupation: "+ dateFormat.format(dateOfOccupation) +
                " dateOfEviction: " + dateFormat.format(dateOfEviction);
    }
}
