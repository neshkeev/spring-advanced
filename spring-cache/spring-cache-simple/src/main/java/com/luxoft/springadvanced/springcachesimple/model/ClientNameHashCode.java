package com.luxoft.springadvanced.springcachesimple.model;

import java.util.Objects;

public class ClientNameHashCode extends Client {
    public ClientNameHashCode(String name, String address) {
        super(name, address);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Client) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }
}