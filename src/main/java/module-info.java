module ufrn.tads.agendamentodeconsultas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ufrn.tads.agendamentodeconsultas to javafx.fxml;
    exports ufrn.tads.agendamentodeconsultas;
    exports ufrn.tads.agendamentodeconsultas.controller;
    opens ufrn.tads.agendamentodeconsultas.controller to javafx.fxml;
    exports ufrn.tads.agendamentodeconsultas.service;
    opens ufrn.tads.agendamentodeconsultas.service to javafx.fxml;
    exports ufrn.tads.agendamentodeconsultas.repository;
    opens ufrn.tads.agendamentodeconsultas.repository to javafx.fxml;
    exports ufrn.tads.agendamentodeconsultas.model;
    opens ufrn.tads.agendamentodeconsultas.model to javafx.fxml;
}