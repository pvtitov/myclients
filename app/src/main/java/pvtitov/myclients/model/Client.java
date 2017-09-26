package pvtitov.myclients.model;


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
    private String picture;


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

    public String getPicture() {
        return picture;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }



    public Client(List<Result> results, int position) {
        this.firstName = results.get(position).getName().getFirst();
        this.lastName = results.get(position).getName().getLast();
        this.email = results.get(position).getEmail();
        this.phone = results.get(position).getPhone();
        this.nationality = results.get(position).getNat();
        this.address = results.get(position).getLocation().getCity();
        this.picture = results.get(position).getPicture().getLarge();
    }

    public Client(){
    }

}
