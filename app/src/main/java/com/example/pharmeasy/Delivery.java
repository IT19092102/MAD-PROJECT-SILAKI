package com.example.pharmeasy;

public class Delivery {


    private String id;

    private String address;
    private String state;
    private String city;
    private Integer postalcode;
    private String deliverydate;

    public Delivery() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(Integer postalcode) {
        this.postalcode = postalcode;
    }

    public String getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(String deliverydate) {
        this.deliverydate = deliverydate;
    }






    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
