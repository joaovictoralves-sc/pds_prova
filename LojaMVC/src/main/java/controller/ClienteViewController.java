package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClienteViewController {

    @FXML
    private Button btnSalvar;

    @FXML
    private Label lblDataNascimento;

    @FXML
    private Label lblEndereco;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblTelefone;

    @FXML
    private TextField txtDataNascimento;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextArea txtListaClientes;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTelefone;

    @FXML
    void onClickBtnSalvarActionPerfomed(ActionEvent event) {

    }

}
