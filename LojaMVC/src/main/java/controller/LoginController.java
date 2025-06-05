package controller;

import dal.ConexaoBD;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.LoginDAO;
import model.Usuario;
import util.AlertaUtil;

public class LoginController {

    private Stage stageLogin;
    private final LoginDAO dao = new LoginDAO();
    private ArrayList<String> listaDados;
    private Usuario user;

    @FXML
    private ImageView imgBancoOnline;
    
    @FXML
    private Button bntFechar;

    @FXML
    private Button bntLogar;

    @FXML
    private Label lblDB;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private TextField txtUsuario;

    @FXML
    void bntFecharClick(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void bntLogarClick(ActionEvent event) throws IOException, SQLException {
        processarLogin();
    }

    public void setStage(Stage stage) {
        this.stageLogin = stage;
    }

    public void verificarBanco() {
        if(dao.bancoOnline()){
            Image imagem = new Image(getClass().getResourceAsStream("/icones/dbok.png"));
            imgBancoOnline.setImage(imagem);
        } else {
            Image imagem = new Image(getClass().getResourceAsStream("/icones/dberror.png"));
            imgBancoOnline.setImage(imagem);
        }
    }

    public void abrirJanela() {
        bntLogar.setDefaultButton(true);
        verificarBanco();
    }

    public void processarLogin() throws IOException, SQLException {
        if (!dao.bancoOnline()) {
            AlertaUtil.mostrarErro("Erro", "Banco de dados desconectado!");
        } else if (txtUsuario.getText() != null && !txtUsuario.getText().isEmpty() && txtSenha.getText() != null && !txtSenha.getText().isEmpty()) {
            listaDados = autenticar(txtUsuario.getText(), txtSenha.getText());
            if (listaDados != null) {
                AlertaUtil.mostrarInformacao("Informação", "Bem vindo " + listaDados.get(0) + " acesso liberado!");
                if (stageLogin != null) {
                    stageLogin.close();
                }
                abrirTelaPrincipal(listaDados);
            } else {
                AlertaUtil.mostrarErro("Erro", "Usuário e senha inválidos!");
            }
        } else {
            AlertaUtil.mostrarErro("Erro", "Verifique as informações!");
        }
    }

    private ArrayList<String> autenticar(String login, String senha) throws SQLException {
        user = dao.autenticar(login, senha);
        if (user != null) {
            ArrayList<String> listaDados = new ArrayList<>();
            listaDados.add(user.getNome());
            listaDados.add(user.getPerfil());
            return listaDados;
        }
        return null;
    }

    private void abrirTelaPrincipal(ArrayList<String> dados) throws MalformedURLException, IOException {
        URL url = getClass().getResource("/view/Principal.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        Stage telaPrincipal = new Stage();
        PrincipalController pc = loader.getController();
        pc.setStage(telaPrincipal);

        telaPrincipal.setOnShown(evento -> {
            pc.ajustarElementosJanela(dados);
        });

        Scene scene = new Scene(root);
        
        Image icone = new Image(getClass().getResourceAsStream("/icones/loja.png"));
        telaPrincipal.getIcons().add(icone);

        telaPrincipal.setTitle("Tela principal do Sistema");
        telaPrincipal.setScene(scene);
        telaPrincipal.show();
    }

}
