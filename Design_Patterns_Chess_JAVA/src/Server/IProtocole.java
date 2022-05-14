package Server;


import java.io.InputStream;
import java.io.OutputStream;

public interface IProtocole {
    public void execute(IContext c, InputStream anInputStream, OutputStream anOutputStream, InputStream is2, OutputStream os2);
}
