package Server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerBackup {

    int port = 5000;
    ServerSocket serverSocket ;

    @SuppressWarnings("InfiniteLoopStatement")
    public ServerBackup(){
        try {
            serverSocket = new ServerSocket(port);
            Socket socketClient1 = serverSocket.accept();
            PrintWriter printWriterClient1 = new PrintWriter(socketClient1.getOutputStream());
            InputStreamReader inputStreamReaderClient1 = new InputStreamReader(socketClient1.getInputStream());
            BufferedReader bufferedReaderClient1 = new BufferedReader(inputStreamReaderClient1);
            Socket socketClient2 = serverSocket.accept();
            PrintWriter printWriterClient2 = new PrintWriter(socketClient2.getOutputStream());
            InputStreamReader inputStreamReaderClient2 = new InputStreamReader(socketClient2.getInputStream());
            BufferedReader bufferedReaderClient2 = new BufferedReader(inputStreamReaderClient2);

            printWriterClient1.println("Joueur Blanc");
            printWriterClient1.flush();

            printWriterClient2.println("Joueur Noir");
            printWriterClient2.flush();


            while(true){
                printWriterClient1.println("Votre tour");
                printWriterClient1.flush();
                printWriterClient2.println("Tour de l'adversaire");
                printWriterClient2.flush();
                String coup_client1 = bufferedReaderClient1.readLine();
                printWriterClient2.println(coup_client1);
                printWriterClient2.flush();

                printWriterClient2.println("Votre tour");
                printWriterClient2.flush();
                printWriterClient1.println("Tour de l'adversaire");
                printWriterClient1.flush();
                String coup_client2 = bufferedReaderClient2.readLine();
                printWriterClient1.println(coup_client2);
                printWriterClient1.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        ServerBackup server = new ServerBackup();
    }
}
