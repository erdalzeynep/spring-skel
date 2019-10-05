package com.cepheid.cloud.skel.model;
import javax.persistence.*;

@Entity
@Table
public class User extends AbstractEntity {
    @Column
    private String email;
    @Column
    private String password;

    public User() {
    }

    public User(String email, String password) {
        this.email  = email;
        this.password = password ;
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
}
