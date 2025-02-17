package dao.model.entity;

public class ServiceType {
    private int id;
    String name;
    double price ;
    int serviceTypeId;
    public ServiceType(int serviceTypeId, String name, double price) {
        this.serviceTypeId = serviceTypeId;
        this.name = name;
        this.price = price;
    }
    public ServiceType( String name, double price) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    @Override
    public String toString() {
        return "ServiceType{" +
                " name='" + name + '\'' +
                ", price=" + price +
                ", serviceTypeId=" + serviceTypeId +
                '}';
    }
}
