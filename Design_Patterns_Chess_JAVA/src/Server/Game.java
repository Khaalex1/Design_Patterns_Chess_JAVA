package Server;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Game implements IGame{

    private int port = 5000;

    public ArrayList<ServeurTCP> serveurs = new ArrayList<ServeurTCP>();
    private IModeJeu strategyModeJeu;

    public Game(IModeJeu stratM){
        strategyModeJeu = stratM;
    }

    public void lancerJeu(){
        serveurs.add(new ServeurTCP(this, new ProtocoleJeu(), port));
        for (ServeurTCP s : serveurs){
            s.start();
        }
    }

    @Override
    public void appliquerModeJeu(InputStream input1, OutputStream output1, InputStream input2, OutputStream output2) {
        strategyModeJeu.jouer(input1, output1, input2, output2);
    }

    public IModeJeu getStrategyModeJeu() {
        return strategyModeJeu;
    }

    public void setStrategyModeJeu(String typeMode) {
        if (typeMode.equals("ModeJeuClassique")){
            strategyModeJeu = new ModeJeuClassique();
        }
        else if (typeMode.equals("ModeJeuLocal")){
            strategyModeJeu = new ModeJeuLocal();
        }
    }
}
