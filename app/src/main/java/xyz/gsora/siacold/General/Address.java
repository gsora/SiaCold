package xyz.gsora.siacold.General;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gsora on 6/29/17.
 * <p>
 * Class representing a Siacoin address.
 */
public class Address extends RealmObject {

    @PrimaryKey
    private long id;
    private String address;
    private String description;

    public Address() {
    }

    public Address(String address) {
        this.address = address;
    }

    public Address(String address, String description, long id) {
        this.address = address;
        this.description = description;
        this.id = id;
    }

    public Address(String address, long id) {
        this.address = address;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
