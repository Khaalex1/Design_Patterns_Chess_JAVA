package Server;

import Server.ServeurTCP;

import java.io.IOException;
import java.net.Socket;

public class ProcessusJeu extends Thread {

    private Socket clientSocket1;
    private Socket clientSocket2;
    private ServeurTCP monServeurTCP;

    public  ProcessusJeu(Socket socket1, Socket socket2, ServeurTCP unServeur) {
        super("ServeurThread");
        clientSocket1 = socket1;
        clientSocket2 = socket2;
        System.out.println("[ProcessusJeu] CLIENT1 : " + clientSocket1);
        System.out.println("[ProcessusJeu] CLIENT2 : " + clientSocket2);
        monServeurTCP = unServeur;
    }

    public void run() {
        try {
            monServeurTCP.getProtocole().execute(monServeurTCP.getContexte() , clientSocket1.getInputStream() , clientSocket1.getOutputStream(), clientSocket2.getInputStream(), clientSocket2.getOutputStream() );
            System.out.println("Processus jeu fait");
        } catch (IOException e) {
            System.err.println("[ProcessusJeu] Exception : " + e );
            e.printStackTrace();
        }
    }
}

