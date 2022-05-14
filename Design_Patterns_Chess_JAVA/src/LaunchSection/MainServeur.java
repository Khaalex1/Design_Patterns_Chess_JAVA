package LaunchSection;

import Server.Game;
import Server.ModeJeuClassique;


public class MainServeur {
    private int port = 5000;

    public static void main(String[] args) {
        Game jeu = new Game(new ModeJeuClassique());

        jeu.lancerJeu();

    }
}
