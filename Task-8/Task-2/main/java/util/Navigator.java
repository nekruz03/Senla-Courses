package util;

public class Navigator {
    private Menu currentMenu;

    public void setCurrentMenu(Menu menu) {
        this.currentMenu = menu;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void navigateTo(Menu nextMenu) {
        currentMenu = nextMenu;
    }

}
