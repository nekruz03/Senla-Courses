package dao.model.entity;

public class Guest {
    private int id;
    private String guestName;
    private String guestSurname;
    private String passportNumber;
    public Guest(int id, String guestName, String guestSurname, String passportNumber) {
        this.id  = id;
        this.guestName = guestName;
        this.guestSurname = guestSurname;
        this.passportNumber = passportNumber;
    }
    public Guest( String guest_name, String guestSurname, String passportNumber) {
        this.guestName = guest_name;
        this.guestSurname = guestSurname;
        this.passportNumber = passportNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestSurname() {
        return guestSurname;
    }

    public void setGuestSurname(String guestSurname) {
        this.guestSurname = guestSurname;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @Override
    public String toString() {
        return "GuestRecord{" +
                "id=" + id +
                ", guest_name='" + guestName + '\'' +
                ", guest_surname='" + guestSurname + '\'' +
                ", passport_number='" + passportNumber + '\'' +
                '}';
    }
}
