package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO extends GenericDAO {

    // Método para verificar se o banco está online
    public Boolean bancoOnline() {
        Connection con = conectarDAO();
        if (con != null) {
            try {
                // Fecha a conexão após o teste
                conectarDAO().close();
            } catch (SQLException e) {
                // Ignora exceções ao fechar a conexão
            }
            return true;
        } else {
            return false;
        }
    }

    // Método para autenticar usuários pelo login e senha
    public Usuario autenticar(String login, String senha) throws SQLException {
        String sql = "SELECT * FROM USUARIOS WHERE login=? AND senha=?";
        Usuario usuario = null;
        Connection con = conectarDAO();

        if (con != null) {
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setFone(rs.getString("fone"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setPerfil(rs.getString("perfil"));
                usuario.setEmail(rs.getString("email"));
                usuario.setData(rs.getDate("data"));
            }

            rs.close();
            stmt.close();
            conectarDAO().close();

            return usuario;
        } else {
            return null;
        }
    }
}
