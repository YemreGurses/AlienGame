package main.java.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {

    static Vector<ClientHandler> ar = new Vector<>();

    private static Integer i = 0;
    static Integer counter = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(1234);

        Socket s;

        while (true) {
            s = ss.accept();

            System.out.println("New client request received : " + s);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            System.out.println("Creating a new handler for this client...");

            ClientHandler mtch = new ClientHandler(s, "client " + i, dis, dos);

            Thread t = new Thread(mtch);

            System.out.println("Adding this client to active client list");

            ar.add(mtch);

            t.start();

            i++;
            if (i == 2) {
                i = 0;
            }

        }
    }
}

class ClientHandler implements Runnable {
    private String name;
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private Socket s;
    private static Integer flag = 0;

    ClientHandler(Socket s, String name,
                  DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
    }

    @Override
    public void run() {

        String received;
        while (true) {
            try {
                received = dis.readUTF();

                System.out.println(received);

                if (received.equals("logout")) {
                    for (ClientHandler mc : Server.ar) {
                        mc.s.close();
                        mc.dis.close();
                        mc.dos.close();
                    }
                    Server.ar.clear();
                    break;
                } else if (received.equals("level4")) {
                    Server.counter++;
                    if (Server.counter == 2) {
                        Server.ar.get(1).dos.writeUTF("start");
                        Server.ar.get(0).dos.writeUTF("start");
                        Server.counter = 0;
                    }
                } else {
                    if (this.name.equals("client 0")) {
                        if (flag != 0) {
                            Server.ar.get(1).dos.writeUTF(received);
                            System.out.println("0dan 1e");
                        }
                    } else if (this.name.equals("client 1")) {
                        flag = 1;
                        Server.ar.get(0).dos.writeUTF(received);
                        System.out.println("1den 0a");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

        }

    }
}

