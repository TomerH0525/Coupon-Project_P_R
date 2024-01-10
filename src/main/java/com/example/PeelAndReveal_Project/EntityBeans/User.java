package com.example.PeelAndReveal_Project.EntityBeans;

import com.example.PeelAndReveal_Project.EntityBeans.Enum.ClientType;

public class User {
    String email;
    String password;
    ClientType clientType;

    public User(String email, String password, String clientType) {
        this.email = email;
        this.password = password;
        this.clientType = ClientType.valueOf(clientType);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", clientType=" + clientType +
                '}';
    }
}
