module com.bty.quanlynhatro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;

    opens com.bty.quanlynhatro to javafx.fxml;
    exports com.bty.quanlynhatro;
    exports com.bty.service;
    exports com.bty.pojo;
}
