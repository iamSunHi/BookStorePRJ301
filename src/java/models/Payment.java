/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Nhật Huy
 */
public class Payment implements Serializable {
    private int id;
    private int name;
    
    private ArrayList<Store> storesList;
}
