/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package appbarcos;

import Controlador.MulticastPublisher;
import Controlador.MulticastReceiver;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vchir
 */
public class AppBarcos {

    public static long client_id = new Date().getTime();  //Obtiene un long único

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Conocer si hay un canal abierto...
        MulticastReceiver receptor = new MulticastReceiver();
        receptor.start();
    }

}
