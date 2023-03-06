package com.luxoft.springadvanced.springcachesimple.model;

import java.util.Objects;

public class Client {
    protected final String name;
    protected String address;

    public Client(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Client) obj;
        return Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Client[" +
                "name=" + name + ", " +
                "address=" + address + ']';
    }
}