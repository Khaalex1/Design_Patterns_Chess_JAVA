package Server;

import java.io.*;

public class ModeJeuClassique implements IModeJeu{

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void jouer(InputStream input1, OutputStream output1, InputStream input2, OutputStream output2) {

        try {
            PrintWriter printWriterClient1 = new PrintWriter(output1);
            InputStreamReader inputStreamReaderClient1 = new InputStreamReader(input1);
            BufferedReader bufferedReaderClient1 = new BufferedReader(inputStreamReaderClient1);

            PrintWriter printWriterClient2 = new PrintWriter(output2);
            InputStreamReader inputStreamReaderClient2 = new InputStreamReader(input2);
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
}
