package Server;

import Server.IContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServeurTCP extends Thread {

    private static int nbConnexions=0;
    /** Maximum de connexions client autorisées */
    private int maxConnexions;

    private Socket clientSocket1;
    private Socket clientSocket2;

    private IContext contexte;

    private IProtocole protocole;

    private int numeroPort;
    private int numeroPort2;

    public ServeurTCP(int unNumeroPort) {
        numeroPort = unNumeroPort;
        maxConnexions = 2;
    }

    public ServeurTCP(IContext b, IProtocole p, int port) {
        this(port);
        contexte = b;
        protocole = p;
    }

    @Override
    public String toString() {
        return "[ServeurTCP] Port : " + numeroPort + ", Contexte: " + contexte;
    }

    /* l'ancienne methode go est remplacee par run */
    @Override
    public void run() {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(numeroPort);

        } catch (IOException e) {
            System.out.println("Could not listen on port: " + numeroPort + ", " + e);
            System.exit(1);
        }
        try {
            System.out.println(" Attente du serveur pour la communication des clients ");
            clientSocket1 = serverSocket.accept();
            nbConnexions ++;
            serverSocket.setSoTimeout(10000);

            // temps d'attente, sinon cientSocket2 = clientSocket1
            clientSocket2 = serverSocket.accept();

            nbConnexions++;
        }
        catch(SocketTimeoutException e){
            clientSocket2 = clientSocket1;
        }
        catch (IOException e) {
            System.out.println("Accept failed ");
            System.exit(1);
        }
        ProcessusJeu st = new ProcessusJeu(clientSocket1, clientSocket2, this);
        st.start();


        System.out.println("Deja " + nbConnexions + " client(s).");
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Could not close");
        }
    }

    public IProtocole getProtocole() {
        return protocole;
    }

    public IContext getContexte() {
        return contexte;
    }

}

