package dao.model.service;

import annotation.OwnInject;
import dao.model.Dao.Guest_Dao;
import dao.model.entity.Guest;
import viev.ConsoleView;

import java.util.List;

public class Guest_Service {
    @OwnInject
    ConsoleView consoleView;
    @OwnInject
    Guest_Dao guestDao;
    public int addGuest(){
        Guest guest = guestDao.getGuestFromConsole();
        return guestDao.save(guest);
    }
    public int updateGuest(){
        String guestInput = consoleView.getInput("Enter Guest id: ");
        int guestId = Integer.parseInt(guestInput);
        Guest guest = guestDao.getGuestFromConsole();
        guest.setId(guestId);
        guestDao.update(guest);
        return guest.getId();
    }
    public void find_by_id (int id){
        Guest guest = guestDao.findById(id);
        if (guest.getPassport_number() == null) consoleView.print("Guest not found");
        else {
            System.out.println(guest);
        }
    }
    public void getAllGuests(){
        List<Guest> guests = guestDao.findAll();
        if (guests.isEmpty()) consoleView.print("No guests found");
        for (Guest guest : guests) {
            System.out.println(guest);
        }
    }

    public void deleteGuest(){
        String guestInput = consoleView.getInput("Enter Guest id: ");
        int guestId = Integer.parseInt(guestInput);
        Guest guest = guestDao.findById(guestId);
        if (guest.getGuest_name() == null) consoleView.print("Guest not found");
        else guestDao.delete(guest);
    }
}
