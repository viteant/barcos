/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Ficha;
import java.util.Random;

/**
 *
 * @author vchir
 */
public class ControladorTablero {

    public static final int FILAS = 6;
    public static final int COLUMNAS = 7;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int[] TAMAÑOS_BARCOS = {3, 3, 2, 2, 2};
    
    private Ficha[][] tablero;

    public Ficha[][] getTablero() {
        return tablero;
    }

    public void setTablero(Ficha[][] tablero) {
        this.tablero = tablero;
    }

    public ControladorTablero() {

        Ficha[][] tablero = new Ficha[FILAS][COLUMNAS];
        llenarTablero(tablero);

        for (int i = 0; i < TAMAÑOS_BARCOS.length; i++) {
            posicionarBarco(tablero, TAMAÑOS_BARCOS[i], (i + 1), Ficha.ESTADO_OCUPADO);
        }
        
        mostrarTablero(tablero);
    }

    
    public static Ficha[][] llenarTablero(Ficha[][] tablero) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = new Ficha(i, j, Ficha.ESTADO_VACIO);
            }
        }
        return tablero;
    }

    public static Ficha[][] posicionarBarco(Ficha[][] tablero, int extension, int identificador, int estado) {
        Random random = new Random();
        int fila_inicial, columna_inicial, orientacion;
        boolean continuar;
        do {
            continuar = false;
            fila_inicial = random.nextInt(FILAS);
            columna_inicial = random.nextInt(COLUMNAS);
            orientacion = random.nextInt(2);

            if ((orientacion == HORIZONTAL && (columna_inicial + extension) >= COLUMNAS)
                    || (orientacion == VERTICAL && (fila_inicial + extension) >= FILAS)) {
                continuar = true;
            } else {
                if (orientacion == HORIZONTAL) {
                    int columna_final = columna_inicial + extension;
                    for (int columna = columna_inicial; columna < columna_final; columna++) {
                        if (tablero[fila_inicial][columna].getEstado() != Ficha.ESTADO_VACIO) {
                            continuar = true;
                            break;
                        }
                    }
                } else {
                    int fila_final = fila_inicial + extension;
                    for (int fila = fila_inicial; fila < fila_final; fila++) {
                        if (tablero[fila][columna_inicial].getEstado() != Ficha.ESTADO_VACIO) {
                            continuar = true;
                            break;
                        }
                    }

                }
            }

        } while (continuar);

        if (orientacion == HORIZONTAL) {
            int columna_final = columna_inicial + extension;

            for (int columna = columna_inicial; columna < columna_final; columna++) {
                tablero[fila_inicial][columna].setId_barco(identificador);
                tablero[fila_inicial][columna].setEstado(estado);
                tablero[fila_inicial][columna].setOrientacion(orientacion);
            }
        } else {
            int fila_final = fila_inicial + extension;
            for (int fila = fila_inicial; fila < fila_final; fila++) {
                tablero[fila][columna_inicial].setId_barco(identificador);
                tablero[fila][columna_inicial].setEstado(estado);
                tablero[fila][columna_inicial].setOrientacion(orientacion);
            }

        }

        return tablero;

    }

    public static void mostrarTablero(Ficha[][] tablero) {
        for (Ficha[] tablero1 : tablero) {
            for (Ficha tablero11 : tablero1) {
                System.out.print(" " + tablero11 + " ");
            }
            System.out.println("");
        }

    }

}
