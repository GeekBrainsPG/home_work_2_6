package com.leson_2_6;

import com.leson_2_6.server.MessengerServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Messenger {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public static void main(String[] args) {
        new Messenger();

        System.out.println("Messenger: Main thread is not blocked!");
    }

    Messenger() {
        start();
    }

    private void start() {
        try {
            socket = new Socket("127.0.0.1", MessengerServer.PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());


            Scanner scanner = new Scanner(System.in);

            new Thread(() -> {
                while (true) {
                    try {
                        String message = scanner.nextLine();
                        out.writeUTF(message);

                        if ("/quit".equalsIgnoreCase(message)) {
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            new Thread(() -> {
                while (true) {
                    try {
                        String message = in.readUTF();

                        System.out.println("Messenger got a message: " + message);

                        if ("/quit".equalsIgnoreCase(message)) {
                            out.writeUTF(message);
                            closeConnection();

                            break;
                        }

                        System.out.println(message);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
