module com.example.flash_card {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.flash_card to javafx.fxml;
    exports com.example.flash_card;
}