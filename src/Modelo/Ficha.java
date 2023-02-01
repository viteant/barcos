/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vchir
 */
public class Ficha {

    public static int ESTADO_VACIO = 0;
    public static int ESTADO_OCUPADO = 1;
    public static int ESTADO_GOLPEADO = 2;

    private int id_barco;
    private int fila;
    private int columna;
    private int orientacion;
    private int estado;
    private boolean ocultarTablero = false;

    public Ficha(int fila, int columna, int estado) {
        this.fila = fila;
        this.columna = columna;
        this.estado = estado;
    }

    public int getId_barco() {
        return id_barco;
    }

    public void setId_barco(int id_barco) {
        this.id_barco = id_barco;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(int orientacion) {
        this.orientacion = orientacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public boolean isOcultarTablero() {
        return ocultarTablero;
    }

    public void setOcultarTablero(boolean ocultarTablero) {
        this.ocultarTablero = ocultarTablero;
    }

    @Override
    public String toString() {
        if (ocultarTablero) {
            return "▮▮";
        } else if (estado == ESTADO_OCUPADO) {
            return "B" + id_barco;
        } else if (estado == ESTADO_VACIO) {
            return "▤▤";
        }
        return "";
    }

}
