package Server;

import java.io.InputStream;
import java.io.OutputStream;

public interface IModeJeu {

    public void jouer(InputStream input1, OutputStream os1, InputStream input2, OutputStream os2);
}
