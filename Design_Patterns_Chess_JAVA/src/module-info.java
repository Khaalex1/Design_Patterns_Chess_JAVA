module com.example.chessgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens GraphicalElements to javafx.fxml;
    exports GraphicalElements;

    opens LaunchSection to javafx.fxml;
    exports LaunchSection;
}