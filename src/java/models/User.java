/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.ArrayList;

/**
 *
 * @author Nhật Huy
 */
public class User {

    private String id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String imageUrl;

    private ArrayList<Role> roleList;

    private ArrayList<Book> rentedBooks;
    private ArrayList<Book> boughtBooks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(ArrayList<Role> roleList) {
        this.roleList = roleList;
    }

    public ArrayList<Book> getRentedBooks() {
        return rentedBooks;
    }

    public void setRentedBooks(ArrayList<Book> rentedBooks) {
        this.rentedBooks = rentedBooks;
    }

    public ArrayList<Book> getBoughtBooks() {
        return boughtBooks;
    }

    public void setBoughtBooks(ArrayList<Book> boughtBooks) {
        this.boughtBooks = boughtBooks;
    }
}
