package ufrn.tads.agendamentodeconsultas.controller;

import ufrn.tads.agendamentodeconsultas.App;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws Exception {
        App.setRoot("SecondaryController");
    }
}
