package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {
	
	/* Criando conexão com o Bando de Dados. */
	private static String banco = "jdbc:postgresql://localhost:5432/curso-jsp?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "admin";
	private static Connection connection = null;
	
	
	public static Connection getConnection() {
		return connection;
	}
	
	static {
		conectar(); 
	}
	
	
	public SingleConnectionBanco() { /* Quando for instanciado, será conectado */
		conectar();
	}
	
	private static void conectar() {
		
		try {
			
			if (connection == null) {
				Class.forName("org.postgresql.Driver"); /* Carrega o driver de conexão do banco */
				connection = DriverManager.getConnection(banco, user, senha);
				connection.setAutoCommit(false); /* Para não efetuar alterações no banco sem comando */
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}