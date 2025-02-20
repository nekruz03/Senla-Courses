package util;

import annotation.OwnInject;
import dao.model.connection.ConnectionManager;
import org.example.Action.*;

import java.util.List;

public class Builder {
    @OwnInject
    ConnectionManager connectionManager;
    private Menu rootMenu;
    @OwnInject
    private AddRoomAction addRoomAction;
    @OwnInject
    private DeleteRoomAction deleteRoomAction;
    @OwnInject
    private UpdateRoomAction updateRoomAction;
    @OwnInject
    private DisplayAllRoomsAction displayAllRoomsAction;
    @OwnInject
    DisplayRoomByNumberAction displayRoomByNumberAction;
    @OwnInject
    private DisplayRoomsSortedByNumberAction displayRoomsSortedByNumberAction;
    @OwnInject
    private DisplayRoomsSortedByPriceAction displayRoomsSortedByPriceAction;
    @OwnInject
    AddGuestAction addGuestAction;
    @OwnInject
    DeleteGuestAction deleteGuestAction;
    @OwnInject
    DisplayGuestById displayGuestByIdAction;
    @OwnInject
    DisplayAllGuests displayAllGuestsAction;
    @OwnInject
    UpdateGuestAction updateGuestAction;
    @OwnInject
    CheckInGuestAction checkInGuestAction;
    @OwnInject
    private CheckOutAction checkOutAction;
    @OwnInject
    private UpdateGuestRecordAction updateGuestRecordAction;
    @OwnInject
    private DisplayGuestRecordByID displayGuestRecordByIDAction;
    @OwnInject
    private DisplayAllGuestRecord displayAllGuestRecordAction;
    @OwnInject
    AddServiceTypeAction addServiceType;
    @OwnInject
    private DisplayServiceTypeById displayServiceTypeByIdAction;
    @OwnInject
    private  UpdateServiceTypeAction updateServiceTypeAction;
    @OwnInject
    private DisplayServiceTypesAction displayServiceTypesAction;
    private  DeleteServiceTypeByIdAction deleteServiceTypeByIdAction;
    @OwnInject
    private AddingAdditionalServicesAction addingAdditionalServicesAction;
    @OwnInject
    private NumberOfGuestsAction numberOfGuestsAction;

    @OwnInject
    private PriceCalculationAction priseCalculationAction;
   public void buildMenu() throws IllegalAccessException {
        rootMenu = new Menu("Main Menu", List.of(
                new MenuItem("Add Room", addRoomAction, null),
                new MenuItem("Delete Room", deleteRoomAction, null),
                new MenuItem("Update Room", updateRoomAction, null),
                new MenuItem("Check in Guest", checkInGuestAction, null),
                new MenuItem("Check out Guest", checkOutAction, null),
                new MenuItem("Add Service", addServiceType, null),
                new MenuItem("Display info room by number", displayRoomByNumberAction,null),
                new MenuItem("Display List of rooms", displayAllRoomsAction, null),
                new MenuItem("Display List of rooms sorted by number" , displayRoomsSortedByNumberAction, null),
                new MenuItem("Add guest",addGuestAction,null),
                new MenuItem("Delete guest",deleteGuestAction,null),
                new MenuItem("Display guest by id",displayGuestByIdAction,null),
                new MenuItem("Display all guest",displayAllGuestsAction , null),
                new MenuItem("Update guest",updateGuestAction,null),
                new MenuItem("Update guest record", updateGuestRecordAction, null),
                new MenuItem("Display guest record by id ",displayGuestRecordByIDAction, null),
                new MenuItem("Display All Guest Record ",displayAllGuestRecordAction,null),
                new MenuItem("Display, number of Guests in room",numberOfGuestsAction,null),
                new MenuItem("Delete service type by id", deleteServiceTypeByIdAction,null),
                new MenuItem("Update service type",updateServiceTypeAction,null),
                new MenuItem("Display List of Available Services", displayServiceTypesAction, null),
                new MenuItem("Display service type by id",displayServiceTypeByIdAction , null),
                new MenuItem("Add Additional Services to Room", addingAdditionalServicesAction, null),
                new MenuItem("Price Calculation", priseCalculationAction, null)
        ));
    }
    public Menu getRootMenu() {
        return rootMenu;
    }
}
