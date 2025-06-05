import controller.LoginController;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import model.Cliente;
import model.ClienteDAO;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Loja MVC");
        URL url = new File("src/main/java/view/Login.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        Stage telaLogin = new Stage();
        LoginController lc = loader.getController();
        lc.setStage(telaLogin);
        telaLogin.setOnShown(event -> {
            lc.abrirJanela();
        });
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
        telaLogin.setScene(scene);
        telaLogin.show();
    }

    public static void main(String[] args) {
        ClienteDAO clienteDAO = new ClienteDAO();
        
        Cliente novoCliente = new Cliente();
        novoCliente.setNome("João Victor ALves");
        novoCliente.setTelefone("999999999");
        novoCliente.setEndereco("Rua x");
        novoCliente.setDataNascimento(Date.valueOf("2000-05-05"));
        
        clienteDAO.inserirCliente(novoCliente);
        
        clienteDAO.listarClientes();
        launch();
    }

}
