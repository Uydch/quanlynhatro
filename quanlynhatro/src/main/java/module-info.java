module com.bty.quanlynhatro {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.bty.quanlynhatro to javafx.fxml;
    exports com.bty.quanlynhatro;
}
