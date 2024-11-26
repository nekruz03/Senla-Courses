public class Visitor {
    private String name;
    private int passportId;
    Visitor(String name, int passportId) {
        this.name = name;
        this.passportId = passportId;
    }
    public String getName() {
        return name;
    }
    public int getPassportId() {
        return passportId;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPassportId(int passportId) {
        this.passportId = passportId;
    }
}
