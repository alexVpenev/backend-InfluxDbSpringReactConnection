package dbconnection.demo.entity;

import lombok.Getter;
import lombok.Setter;

public class Truck{

    @Getter @Setter
    public int id;

    @Getter @Setter
    public String name;

    @Getter @Setter
    public Boolean status;

    @Getter @Setter
    public String owner;

    public Truck() {

    }

    public Truck(int id, String name, Boolean status, String owner) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.owner = owner;
    }

//    public String getMaikaTi() {
//        return "maika ti";
//    }

}
