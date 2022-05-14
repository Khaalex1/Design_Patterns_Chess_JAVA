package Server;

import java.io.*;

public class ProtocoleJeu implements  IProtocole{
    @Override
    public void execute(IContext c, InputStream input1, OutputStream output1, InputStream input2, OutputStream output2) {
        Game jeu = (Game) c;

        if (input1.equals(input2)){
            jeu.setStrategyModeJeu("ModeJeuLocal");
        }

        jeu.appliquerModeJeu(input1, output1, input2, output2);
    }
}
