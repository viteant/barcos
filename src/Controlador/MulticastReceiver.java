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
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

/**
 *
 * @author vchir
 */
public class MulticastReceiver extends Thread {

    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[25600];
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
                socket.receive(packet); //En bucle hasta que llegue un paquete

                SocketMessage message = getSocketMessage(packet.getData());

                if (message != null) {
                    if (message.getMessage().equals("saludar")) {
                        devolverSaludo();
                    }

                    if ("saludando".equals(message.getMessage())) {
                        if (ids.size() >= 2) {
                            if (!ids.contains(message.getId_client())
                                    && AppBarcos.client_id == message.getId_client()) {
                                System.out.println("No se aceptan màs clientes");
                                break;
                            } else {
                                System.out.println("El cliente está lleno.");
                            }
                        } else {
                            if (ids.isEmpty() || !ids.contains(message.getId_client())) {
                                ids.add(message.getId_client());
                            }
                            if (ids.size() >= 2) {
                                long id = ids.get(0);
                                if (AppBarcos.client_id == id) {
                                    AppBarcos.empiezaAJugar();
                                }
                                AppBarcos.setCurrentPlayer(ids.indexOf(AppBarcos.client_id) + 1);
                            } else {
                                System.out.println("Existen "
                                        + ids.size() + " clientes");
                            }

                        }

                    }

                    if ("sincronizar".equals(message.getMessage())) {
                        if (message.getId_client() != AppBarcos.client_id) {
                            AppBarcos.sincronizarTableros(message.getTableros());
                        }
                        AppBarcos.mostrarTableros();
                    }

                    if ("end".equals(message.getMessage())) {
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
