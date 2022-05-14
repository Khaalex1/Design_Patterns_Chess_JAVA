package GraphicalElements;

import All_Pieces.None;
import All_Pieces.Piece;
import GameCommons.Board;
import GameCommons.Engine;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class AppEngine extends Application {

    public static final int W = 544;
    public static final int w = W/8;
    String path = "file:Chess pieces/";
    final Canvas canvas = new Canvas(W, W);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    Piece p0 = new None("", "");
    boolean drag = false;
    Color green = Color.web("#779952");
    Color beige = Color.web("#edeed1");

    Board board = new Board();
    Engine engine = new Engine();

    ArrayList<String> names = new ArrayList<>(List.of("bb", "bk", "bn", "bp", "bq", "br", "wb", "wk", "wn", "wp", "wq", "wr"));
    HashMap<String, Image> images = new HashMap<>();
    ArrayList<String> TRS = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        stage.setTitle("CHESS");
        stage.setResizable(false);

        StackPane root = new StackPane();
        Scene scene = new Scene(root);
        root.setOnMouseDragged(this::DragEffect);
        root.setOnMouseReleased(this::DragReleased);
        stage.setScene(scene);
        root.getChildren().add(canvas);

        for (String name: names) {
            images.put(name, new Image(path + String.format("%s.png", name)));
        }

        draw(board);
        stage.show();
    }

    private void draw(Board B) {
        if (TRS.isEmpty() && p0.getVal() != 0) {
            TRS = board.truly_reachable_squares(p0);
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 1) {
                    gc.setFill(green);
                } else {
                    gc.setFill(beige);
                }
                gc.fillRect(i*w, j*w, w, w);

                if (!TRS.isEmpty() && TRS.contains((String) board.getCoords().get(i + 8*j))) {
                    if (board.getState().get(i + 8*j).getVal() != 0) {
                        gc.setStroke(new Color(0, 0, 0, .1));
                        gc.setLineWidth(3);
                        gc.strokeOval(i*w, j*w, w, w);
                    } else {
                        gc.setFill(new Color(0, 0, 0, .1));
                        gc.fillOval(i*w + w/2 - w/8, j*w + w/2 - w/8, w/4, w/4);
                    }
                }

                gc.drawImage(images.get(B.getState().get(i + 8*j).name()), i*w, j*w, w, w);
            }
        }

        if ((boolean) board.check(board.getWho_plays()).get(0)) {
            String king_coord = board.king(board.getWho_plays()).getCoord();
            gc.setFill(Color.web("#FF0000"));
            int k = Character.getNumericValue(king_coord.charAt(0)) - 10;
            int l = ((int) board.getCoords().get(king_coord) - k)/8;
            gc.fillRect(k*w, l*w, w, w);
            gc.drawImage(images.get(B.getState().get(k + 8*l).name()), k*w, l*w, w, w);
        }

        if (board.mate() || board.stalemate()) {
            board = new Board();
            draw(board);
        }
    }

    private void DragEffect(MouseEvent e) {
        if (p0.getVal() == 0) {
            int i0 = (int) Math.floor(e.getX() / w);
            int j0 = (int) Math.floor(e.getY() / w);
            p0 = board.getState().get(i0 + 8*j0);
        }

        if (!drag) {
            drag = true;
        }

        draw(board);
        gc.drawImage(images.get(p0.name()), e.getX()-w/2, e.getY()-w/2, w, w);
    }

    private void DragReleased(MouseEvent e) {
        if (!drag) {
            return;
        } else { drag = false;}

        double x1 = e.getX();
        double y1 = e.getY();

        int i1 = (int) Math.floor(x1 / w);
        int j1 = (int) Math.floor(y1 / w);

        String target = board.getCoords().get(i1 + 8*j1).toString();

        if (!TRS.contains(target)) {
            System.out.println("CAN'T MOVE PIECE");
            p0 = new None("", "");
            TRS.clear();
            draw(board);
            return;
        } else if (!Objects.equals(board.getWho_plays(), p0.getColor())) {
            System.out.println("NOT YOUR TURN");
            p0 = new None("", "");
            TRS.clear();
            draw(board);
            return;
        }

        board.effective_move(p0, target);
        p0 = new None("", "");
        TRS.clear();
        draw(board);

        if (board.getRecords().size() <= 1) {
            String coupIA = "e5";
            board.make_move("b", coupIA);
            draw(board);
        } else {
            String coupIA = (String) engine.best_move(board.copyBoard(), "b", 2, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).get(0);
            System.out.println(coupIA);
            board.make_move("b", coupIA);
            draw(board);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}