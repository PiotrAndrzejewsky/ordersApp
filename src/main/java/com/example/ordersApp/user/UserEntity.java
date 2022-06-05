package com.example.ordersApp.user;


import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long mUserId;

    @Column(name = "username")
    private String mUsername;

    @Column(name = "password")
    private String mPassword;

    public UserEntity() {
    }

    public UserEntity(String username, String password) {
        this.mUsername = username;
        this.mPassword = password;
    }

    public Long getUserId() {
        return mUserId;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
