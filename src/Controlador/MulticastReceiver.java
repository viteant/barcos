/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.SocketMessage;
import appbarcos.AppBarcos;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vchir
 */
public class MulticastReceiver extends Thread {

    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[256];
    private static int number = 0;
    private ArrayList<Long> ids = new ArrayList<>();

    /**
     *
     * @throws IOException
     */
    @Override
    public void run() {
        try {
            socket = new MulticastSocket(4446);
            socket.joinGroup(InetAddress.getByName("230.0.0.1"));

            saludar();

            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                SocketMessage message = getSocketMessage(packet.getData());

                if (message != null) {
                    if ("saludar".equals(message.getMessage())) {
                        devolverSaludo();
                    }

                    if ("saludando".equals(message.getMessage())) {
                        if (ids.size() >= 2) {
                            if (!ids.contains(message.getId_client()) && AppBarcos.client_id == message.getId_client()) {
                                rechazar();
                            } else {
                                System.out.println("El cliente está lleno.");
                            }
                        } else {
                            if (ids.isEmpty() || !ids.contains(message.getId_client())) {
                                ids.add(message.getId_client());
                            }
                            System.out.println("Existen " + ids.size() + " clientes");
                        }

                    }

                    if ("end".equals(message.getMessage())) {
                        break;
                    }

                    if ("rechazar".equals(message.getMessage())) {
                        System.out.println("No se puede unirse, el cliente no acepta más peticiones.");
                        break;
                    }
                }

            }
            socket.leaveGroup(InetAddress.getByName("230.0.0.1"));
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void saludar() {
        try {
            MulticastPublisher.multicast(new SocketMessage(AppBarcos.client_id, "saludar"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void rechazar() {
        try {
            MulticastPublisher.multicast(new SocketMessage(AppBarcos.client_id, "rechazar"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void devolverSaludo() {
        try {
            MulticastPublisher.multicast(new SocketMessage(AppBarcos.client_id, "saludando"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private SocketMessage getSocketMessage(byte[] data) {
        ObjectInputStream ois = null;
        SocketMessage message = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ois = new ObjectInputStream(bis);
            message = (SocketMessage) ois.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return message;
    }
}
