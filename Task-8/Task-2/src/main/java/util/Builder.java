package util;

import annotation.OwnInject;
import model.RoomManager;
import org.example.Action.*;
import util.DI.DI;

import java.util.Arrays;

public class Builder {
    private Menu rootMenu;
    @OwnInject
    private AddRoomAction addRoomAction;
    @OwnInject
    private DeleteRoomAction deleteRoomAction;
    @OwnInject
    private ChangePriceAction changePriceAction;
    @OwnInject
    private ChangeStatusAction changeStatusAction;
    @OwnInject
    private ChangeTypeAction changeTypeAction;
    @OwnInject
    private ChekInAction checkInAction;
    @OwnInject
    private ChekOutAction checkOutAction;
    @OwnInject
    private DispleyServiceTypesAction displayServiceTypesAction;
    @OwnInject
    private AddingAdditionalServicesAction addingAdditionalServicesAction;
    @OwnInject
    private DispeyListofRoomsAction displayListOfRoomsAction;
    @OwnInject
    private DispeyListofGuestsAction displayListOfGuestsAction;
    @OwnInject
    private DispleyListOfAvailableRoomsAction displayListOfAvailableRoomsAction;
    @OwnInject
    private NumberOfFreeRoomsAction numberOfFreeRoomsAction;
    @OwnInject
    private NumberOfGestsAction numberOfGuestsAction;
    @OwnInject
    private GetListOfVailableRoomsInDateAction getListOfVailableRoomsInDateAction;
    @OwnInject
    private PriseCalculationAction priseCalculationAction;
    @OwnInject
    private ViewTheLastThreeGuestsAction viewTheLastThreeGuestsAction;
    @OwnInject
    private SortedServiceTypesByPriseAction sortedServiceTypesByPriseAction;
    @OwnInject
    private ImportRoomsFromFileAction importRoomsFromFileAction;
    @OwnInject
    private ImportGuestsFromFileAction importGuestsFromFileAction;
    @OwnInject
    private ExportRoomsAction exportRoomsAction;
    @OwnInject
    private ExportGuestsList exportGuestsListAction;

    public void buildMenu(RoomManager roomManager) throws IllegalAccessException {
        DI di = DI.getInstance();
        di.registerBean(RoomManager.class, roomManager);
        di.infectDependencies(this);
        rootMenu = new Menu("Main Menu", Arrays.asList(
                new MenuItem("Add Room", addRoomAction, null),
                new MenuItem("Delete Room", deleteRoomAction, null),
                new MenuItem("Change Price", changePriceAction, null),
                new MenuItem("Change Status", changeStatusAction, null),
                new MenuItem("Change Type", changeTypeAction, null),
                new MenuItem("Check in Guest", checkInAction, null),
                new MenuItem("Check out Guest", checkOutAction, null),
                new MenuItem("Display List of Available Services", displayServiceTypesAction, null),
                new MenuItem("Add Additional Services to Room", addingAdditionalServicesAction, null),
                new MenuItem("Display List of Rooms", displayListOfRoomsAction, null),
                new MenuItem("Display List of Guests", displayListOfGuestsAction, null),
                new MenuItem("Display List of Available Rooms", displayListOfAvailableRoomsAction, null),
                new MenuItem("Display Number of Free Rooms", numberOfFreeRoomsAction, null),
                new MenuItem("Display Number of Guests", numberOfGuestsAction, null),
                new MenuItem("Display List of Rooms Available on a Specific Date", getListOfVailableRoomsInDateAction, null),
                new MenuItem("Price Calculation", priseCalculationAction, null),
                new MenuItem("View Last Three Guests", viewTheLastThreeGuestsAction, null),
                new MenuItem("Display Additional Services by Price", sortedServiceTypesByPriseAction, null),
                new MenuItem("Import Rooms from File", importRoomsFromFileAction, null),
                new MenuItem("Import Guests from File", importGuestsFromFileAction, null),
                new MenuItem("Export Rooms", exportRoomsAction, null),
                new MenuItem("Export Guests List", exportGuestsListAction, null)
        ));
    }

    public Menu getRootMenu() {
        return rootMenu;
    }
}
