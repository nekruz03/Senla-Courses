package dao.model.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

public class Guest {
    private int id;
    private String guest_name;
    private String guest_surname;
    private String passport_number;
    public Guest(int id, String guest_name, String guest_surname, String passport_number) {
        this.id  = id;
        this.guest_name = guest_name;
        this.guest_surname = guest_surname;
        this.passport_number = passport_number;
    }
    public Guest( String guest_name, String guest_surname, String passport_number) {
        this.guest_name = guest_name;
        this.guest_surname = guest_surname;
        this.passport_number = passport_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuest_name() {
        return guest_name;
    }

    public void setGuest_name(String guest_name) {
        this.guest_name = guest_name;
    }

    public String getGuest_surname() {
        return guest_surname;
    }

    public void setGuest_surname(String guest_surname) {
        this.guest_surname = guest_surname;
    }

    public String getPassport_number() {
        return passport_number;
    }

    public void setPassport_number(String passport_number) {
        this.passport_number = passport_number;
    }

    @Override
    public String toString() {
        return "GuestRecord{" +
                "id=" + id +
                ", guest_name='" + guest_name + '\'' +
                ", guest_surname='" + guest_surname + '\'' +
                ", passport_number='" + passport_number + '\'' +
                '}';
    }
}
