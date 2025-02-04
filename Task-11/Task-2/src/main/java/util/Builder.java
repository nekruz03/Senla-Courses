package util;

import annotation.OwnInject;
import org.example.Action.*;
import org.example.Action.GetListOfVailableRoomsInDateAction;

import java.util.List;

public class Builder {
    private Menu rootMenu;
    @OwnInject
    private AddRoomAction addRoomAction;
    @OwnInject
    private DeleteRoomAction deleteRoomAction;
    @OwnInject
    private ChangeRoomPriceAction changePriceAction;
    @OwnInject
    private ChangeStatusAction changeStatusAction;
    @OwnInject
    private ChangeTypeAction changeTypeAction;
    @OwnInject
    private ChekInAction checkInAction;
    @OwnInject
    private ChekOutAction checkOutAction;
    @OwnInject
    AddServiceType addServiceType;
    @OwnInject
    private DispleyServiceTypesAction displayServiceTypesAction;
    @OwnInject
    private AddingAdditionalServicesAction addingAdditionalServicesAction;
    @OwnInject
    private DispeyListofGuestsAction dispeyListofGuestsAction;
    @OwnInject
    private NumberOfFreeRoomsAction numberOfFreeRoomsAction;
    @OwnInject
    private NumberOfGustsAction numberOfGuestsAction;
    @OwnInject
    DispleySortedListOfGuestByName displeySortedListOfGuestByName;
    @OwnInject
    DispeyListofRoomsAction dispeyListofRoomsAction;
    @OwnInject
    DispeyListofRoomsActionSortedByNumber dispeyListofRoomsActionSortedByNumber;
    @OwnInject
    DispleySortedServiceTypesByPrise displeySortedServiceTypesByPrise;
    @OwnInject
    private GetListOfVailableRoomsInDateAction getListOfVailableRoomsInDateAction;
    @OwnInject
    private PriceCalculationAction priseCalculationAction;
    public void buildMenu() throws IllegalAccessException {
        rootMenu = new Menu("Main Menu", List.of(
                new MenuItem("Add Room", addRoomAction, null),
                new MenuItem("Delete Room", deleteRoomAction, null),
                new MenuItem("Change Room Price", changePriceAction, null),
                new MenuItem("Change Room Status", changeStatusAction, null),
                new MenuItem("Change Type", changeTypeAction, null),
                new MenuItem("Check in Guest", checkInAction, null),
                new MenuItem("Check out Guest", checkOutAction, null),
                new MenuItem("Add Service", addServiceType, null),
                new MenuItem("Dispey List of rooms", dispeyListofRoomsAction, null),
                new MenuItem("Displey List of rooms sorted by number" , dispeyListofRoomsActionSortedByNumber, null),
                new MenuItem("Display, number of Guests in room",numberOfGuestsAction,null),
                new MenuItem("Display List of Available Services", displayServiceTypesAction, null),
                new MenuItem("Add Additional Services to Room", addingAdditionalServicesAction, null),
                new MenuItem("Display List of Guests", dispeyListofGuestsAction, null),
                new MenuItem("Display List all Guests sorted by name",displeySortedListOfGuestByName,null),
                new MenuItem("Display Number of Free Rooms", numberOfFreeRoomsAction, null),
                new MenuItem("Display List of Rooms Available on a Specific Date", getListOfVailableRoomsInDateAction, null),
                new MenuItem("Price Calculation", priseCalculationAction, null),
                new MenuItem("Display Additional Services by Price", displeySortedServiceTypesByPrise, null)
        ));
    }
    public Menu getRootMenu() {
        return rootMenu;
    }
}
