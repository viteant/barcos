/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Controlador.ControladorTablero;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author vchir
 */
public class SocketMessage implements Serializable {

    private long id_client;
    private String message;
    private ControladorTablero[] tableros;

    public long getId_client() {
        return id_client;
    }

    public void setId_client(long id_client) {
        this.id_client = id_client;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ControladorTablero[] getTableros() {
        return tableros;
    }

    public void setTableros(ControladorTablero[] tableros) {
        this.tableros = tableros;
    }
    
    

    public SocketMessage(long id_client, String message) {
        this.id_client = id_client;
        this.message = message;
    }

    public SocketMessage(long id_client, String message, ControladorTablero[] tableros) {
        this.id_client = id_client;
        this.message = message;
        this.tableros = tableros;
    }

    public byte[] getByteClass() {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            return bos.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

}
