package Server;

import java.io.*;

public class ModeJeuLocal implements IModeJeu {

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

            printWriterClient1.println("LocalMode");
            printWriterClient1.flush();

            int i = 0;

            while (true) {
                if (i % 2 == 0) {
                    printWriterClient1.println("Tour des Blancs");
                } else {
                    printWriterClient1.println("Tour des Noirs");
                }

                printWriterClient1.flush();
                String coup_client1 = bufferedReaderClient1.readLine();
                i++;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
