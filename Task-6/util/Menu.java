package util;

import java.util.List;

public class Menu {
    private String title;
    private List<MenuItem> items;

    public Menu(String title, List<MenuItem> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public List<MenuItem> getItems() {
        return items;
    }

}
