/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package appbarcos;

import Controlador.ControladorTablero;
import Controlador.MulticastPublisher;
import Controlador.MulticastReceiver;
import Modelo.Ficha;
import Modelo.SocketMessage;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author vchir
 */
public class AppBarcos {

    public final static int PLAYER_1 = 1;
    public final static int PLAYER_2 = 2;

    public static ControladorTablero[] tableros = new ControladorTablero[2];

    public static long client_id = new Date().getTime();  //Obtiene un long único
    public static int currentPlayer = -1;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Conocer si hay un canal abierto...
        MulticastReceiver receptor = new MulticastReceiver();
        receptor.start();
    }

    public static void empiezaAJugar() {
        tableros[0] = new ControladorTablero();
        tableros[1] = new ControladorTablero();
        SyncTableros();

    }

    public static void setCurrentPlayer(int currentPlayer) {
        AppBarcos.currentPlayer = currentPlayer;
    }

    public static void sincronizarTableros(ControladorTablero[] tableros) {
        AppBarcos.tableros = tableros;
    }

    public static void mostrarTableros() {
        int i = 1;
        System.out.println("");
        for (ControladorTablero tablero : tableros) {
            System.out.println("======== JUGADOR " + (i) + " ===========");
            tablero.mostrarTablero(currentPlayer == i);
            System.out.println("********************");
            System.out.println("");
            i++;
        }
    }

    public static void SyncTableros() {
        try {
            MulticastPublisher.multicast(new SocketMessage(AppBarcos.client_id, "sincronizar", tableros));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
