package util;

import model.RoomManager;
import org.example.Action.*;

import java.util.Arrays;

public class Builder {
    private Menu rootMenu;
    public void buildMenu(RoomManager roomManager) {
        MenuItem addRoomItem = new MenuItem("Add Room", new AddRoomAction(roomManager), null);
        MenuItem deleteRoomItem = new MenuItem("Delete Room", new DeleteRoomAction(roomManager), null);
      MenuItem changePrise = new MenuItem("Change prise", new ChangePriceAction(roomManager),null);
      MenuItem changeStatus = new MenuItem("Change status", new ChangeStatusAction(roomManager),null);
      MenuItem changeType = new MenuItem("Change type", new ChangeTypeAction(roomManager),null);
      MenuItem checkInGuest = new MenuItem("Check in guest", new ChekInAction(roomManager),null);
      MenuItem CheckOutGuest =   new MenuItem("Check out guest", new ChekOutAction(roomManager),null);
      MenuItem availableServices = new MenuItem("Display  list of available additional services", new DispleyServiceTypesAction(),null);
      MenuItem addingAdditionalServices = new MenuItem("Add additional services to the room",new AddingAdditionalServicesAction(roomManager),null);
      MenuItem dispeyListofRooms = new MenuItem("Display rooms, (sorted by price) , (sorted by capacity), (sorted by number of stars", new DispeyListofRoomsAction(roomManager),null);
      MenuItem dispeyListofGuests = new MenuItem("Display list of guests, (sorted by Name), (sorted by Date Of Eviction", new DispeyListofGuestsAction(roomManager),null);
      MenuItem displeyListOfAvailableRooms = new MenuItem("Display, available numbers, (sorted by price), (sorted by capacity), (sorted by number of stars)", new DispleyListOfAvailableRoomsAction(roomManager),null);
      MenuItem numberOfFreeRooms = new MenuItem("Display, number of free roms", new NumberOfFreeRoomsAction(roomManager),null);
      MenuItem numberOfGests  = new MenuItem("Display, number of guests", new NumberOfGestsAction(roomManager),null);
      MenuItem  getListOfVailableRoomsInDate = new MenuItem("Display, List of rooms that will be free on a specific date in the future", new GetListOfVailableRoomsInDateAction(roomManager),null);
      MenuItem priseCalculation = new MenuItem("Prise calculation", new PriseCalculationAction(roomManager),null);
      MenuItem viewTheLastThreeGuests = new MenuItem("Display, the last 3 guests of the room and their dates of stay", new ViewTheLastThreeGuestsAction(roomManager),null);
      MenuItem sortedServiceTypesByPrise = new MenuItem("Display,additional services by prise", new SortedServiceTypesByPriseAction(roomManager),null);
      MenuItem exportRoomsFromFile = new MenuItem("Add rooms from file", new ImportRoomsFromFileAction(roomManager),null);
      MenuItem importGuestsFromFileAction = new MenuItem("Import guests from file", new ImportGuestsFromFileAction(roomManager),null);
      MenuItem exportOfAvailableRoomsAction = new MenuItem("Export  rooms", new ExportRoomsAction(roomManager),null);
      MenuItem exportGuestsList = new MenuItem("Export goests list",new ExportGuestsList(roomManager),null);
        rootMenu = new Menu("Main Menu", Arrays.asList(addRoomItem, deleteRoomItem, changePrise,changeStatus, changeType,checkInGuest,CheckOutGuest,availableServices,addingAdditionalServices,
               dispeyListofRooms,dispeyListofGuests,displeyListOfAvailableRooms,numberOfFreeRooms,numberOfGests,getListOfVailableRoomsInDate,priseCalculation,viewTheLastThreeGuests,sortedServiceTypesByPrise,exportRoomsFromFile,importGuestsFromFileAction,exportOfAvailableRoomsAction,exportGuestsList));
    }
    public Menu getRootMenu() {
        return rootMenu;
    }

}
