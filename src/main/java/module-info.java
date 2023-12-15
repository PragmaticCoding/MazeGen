module ca.pragmaticcoding.mazegen {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens ca.pragmaticcoding.mazegen to javafx.fxml;
    exports ca.pragmaticcoding.mazegen;
}