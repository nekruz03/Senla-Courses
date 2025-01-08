package org.example.App;

import annotation.Configurator;
import model.RoomManager;
import org.example.Action.*;
import org.example.Contoller.MenuController;
import util.Builder;
import util.DI.DI;
import util.Navigator;
import viev.ConsoleView;

import java.io.IOException;
import java.text.ParseException;

public class HotelManager {
    public static void main(String[] args) throws ParseException, IOException, IllegalAccessException {
        DI di = DI.getInstance();
        RoomManager roomManager = new RoomManager();
        di.registerBean(RoomManager.class, roomManager);
        Builder builder = new Builder();
        di.registerBean(Builder.class, builder);
        Navigator navigator = new Navigator();
        di.registerBean(Navigator.class, navigator);
        ConsoleView consoleView = new ConsoleView();
        di.registerBean(ConsoleView.class, consoleView);
        di.registerBean(AddRoomAction.class, new AddRoomAction());
        di.registerBean(DeleteRoomAction.class, new DeleteRoomAction());
        di.registerBean(ChangePriceAction.class, new ChangePriceAction());
        di.registerBean(ChangeStatusAction.class, new ChangeStatusAction());
        di.registerBean(ChangeTypeAction.class, new ChangeTypeAction());
        di.registerBean(ChekInAction.class, new ChekInAction());
        di.registerBean(ChekOutAction.class, new ChekOutAction());
        di.registerBean(DispleyServiceTypesAction.class, new DispleyServiceTypesAction());
        di.registerBean(AddingAdditionalServicesAction.class, new AddingAdditionalServicesAction());
        di.registerBean(DispeyListofRoomsAction.class, new DispeyListofRoomsAction());
        di.registerBean(DispeyListofGuestsAction.class, new DispeyListofGuestsAction());
        di.registerBean(DispleyListOfAvailableRoomsAction.class, new DispleyListOfAvailableRoomsAction());
        di.registerBean(NumberOfFreeRoomsAction.class, new NumberOfFreeRoomsAction());
        di.registerBean(NumberOfGestsAction.class, new NumberOfGestsAction());
        di.registerBean(GetListOfVailableRoomsInDateAction.class, new GetListOfVailableRoomsInDateAction());
        di.registerBean(PriseCalculationAction.class, new PriseCalculationAction());
        di.registerBean(ViewTheLastThreeGuestsAction.class, new ViewTheLastThreeGuestsAction());
        di.registerBean(SortedServiceTypesByPriseAction.class, new SortedServiceTypesByPriseAction());
        di.registerBean(ImportRoomsFromFileAction.class, new ImportRoomsFromFileAction());
        di.registerBean(ImportGuestsFromFileAction.class, new ImportGuestsFromFileAction());
        di.registerBean(ExportRoomsAction.class, new ExportRoomsAction());
        di.registerBean(ExportGuestsList.class, new ExportGuestsList());
        MenuController menuController = new MenuController();
        di.registerBean(MenuController.class, menuController);
        DI.infectDependencies(menuController);
        Configurator.configure(roomManager);
        menuController.run();
    }
}
