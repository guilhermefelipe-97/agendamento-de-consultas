package ufrn.tads.agendamentodeconsultas.controller;

import ufrn.tads.agendamentodeconsultas.App;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws Exception {
        if (fazerLogin()) {
            App.setRoot("SecondaryController");
        }
    }

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtSenha;

    // Lógica para quando o botão de login for pressionado
    @FXML
    private boolean fazerLogin() {
        String usuario = txtUsuario.getText();
        String senha = txtSenha.getText();

        // Validação simples
        if ("admin".equals(usuario) && "54321".equals(senha)) {
            // Se o login for bem-sucedido, exibe mensagem de sucesso
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Bem-Sucedido");
            alert.setHeaderText(null);
            alert.setContentText("Bem-vindo, " + usuario + "!");
            alert.showAndWait();
            return true;
        } else {
            // Se o login falhar, exibe uma mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Login");
            alert.setHeaderText("Credenciais Inválidas");
            alert.setContentText("Usuário ou senha incorretos. Tente novamente.");
            alert.showAndWait();
            return false;
        }
    }
}
