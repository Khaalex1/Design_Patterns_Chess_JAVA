package Server;


import java.io.InputStream;
import java.io.OutputStream;

public interface IGame extends IContext {

    public void appliquerModeJeu(InputStream input1, OutputStream output1, InputStream input2, OutputStream output2);
}
