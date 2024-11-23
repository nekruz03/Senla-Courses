package model;

public abstract class Flower {
    protected String name;
    protected double price;
    protected int availableQuantity;
    public Flower(String name, double price ) {
        this.name = name;
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getAvailableQuantity() {
        return availableQuantity;
    }
    public void setAvailableQuantity(int availableQuantity) {

        this.availableQuantity = availableQuantity;
    }
    @Override
    public String toString() {
        return  name;
    }
}
