package pvtitov.myclients.model;

import junit.framework.Test;

import java.util.List;

import pvtitov.myclients.api.Result;

/**
 * Created by Павел on 23.09.2017.
 */

public class Client {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String nationality;
    private String address;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getNationality() {
        return nationality;
    }

    public String getAddress() {
        return address;
    }



    public Client(List<Result> results, int position) {
        this.firstName = results.get(position).getName().getFirst();
        this.lastName = results.get(position).getName().getLast();
        this.email = results.get(position).getEmail();
        this.phone = results.get(position).getPhone();
        this.nationality = results.get(position).getNat();
        this.address = results.get(position).getLocation().getCity();
    }

    public Client(){
        this.firstName = "test first name";
        this.lastName = "test last name";
    }

}
