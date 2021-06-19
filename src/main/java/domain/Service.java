package domain;

public class Service {

    private final int id;
    private String name;
    private int cost;

    public Service(String name, int cost, int id) {
        this.name = name;
        this.cost = cost;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getCost () {
        return cost;
    }

    public void setCost (int cost) {
        this.cost = cost;
    }

    public int getId () {
        return id;
    }

    public String toString () {
        return "Услуга: " + name + "  Стоимость: " + cost;
    }
}
