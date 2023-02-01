/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.SocketMessage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author vchir
 */
public class MulticastPublisher {

    /*Con esta clase se envían mensajes a través del socket*/
    public static void multicast(SocketMessage message) throws IOException {

        System.out.println("Send Multicast");
        DatagramSocket socket = new DatagramSocket();
        InetAddress group = InetAddress.getByName("230.0.0.1"); //IP Para localhost, reemplazar con IP del host
        byte[] buf = message.getBytesClass();

        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, group, 4446);
        socket.send(packet);
        socket.close();
    }

}
