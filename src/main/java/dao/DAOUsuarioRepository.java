package dao;

import beanDTO.BeanDtoGraficoPessoas;
import connection.SingleConnectionBanco;
import model.ModelLogin;
import model.ModelTelefone;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	public BeanDtoGraficoPessoas montarGraficoMediaPessoas(Long userLogado, String dataInicial, String dataFinal) throws Exception{

		String sql = "select perfil, count(*) as total_pessoas from model_login where useradmin is false" +
				" and datanascimento >= ? and datanascimento <= ? group by perfil";
		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setDate(1, Date.valueOf(dataInicial));
		statement.setDate(2, Date.valueOf(dataFinal));

		ResultSet resultSet = statement.executeQuery();

		List<String> perfils = new ArrayList<String>();
		List<Integer> totPessoas = new ArrayList<Integer>();

		BeanDtoGraficoPessoas beanDtoGraficoPessoas = new BeanDtoGraficoPessoas();

		while (resultSet.next()) {
			Integer total_pessoas = resultSet.getInt("total_pessoas");
			String perfil = resultSet.getString("perfil");

			perfils.add(perfil);
			totPessoas.add(total_pessoas);
		}
		beanDtoGraficoPessoas.setPerfils(perfils);
		beanDtoGraficoPessoas.setTotPessoas(totPessoas);

		return beanDtoGraficoPessoas;

	}

	public BeanDtoGraficoPessoas montarGraficoMediaPessoas(Long userLogado) throws Exception {

		String sql = "select perfil, count(*) as total_pessoas from model_login where useradmin is false and usuario_id = ? group by perfil";
		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setLong(1, userLogado);

		ResultSet resultSet = statement.executeQuery();

		List<String> perfils = new ArrayList<String>();
		List<Integer> totPessoas = new ArrayList<Integer>();

		BeanDtoGraficoPessoas beanDtoGraficoPessoas = new BeanDtoGraficoPessoas();

		while (resultSet.next()) {
			Integer total_pessoas = resultSet.getInt("total_pessoas");
			String perfil = resultSet.getString("perfil");

			perfils.add(perfil);
			totPessoas.add(total_pessoas);
		}
		beanDtoGraficoPessoas.setPerfils(perfils);
		beanDtoGraficoPessoas.setTotPessoas(totPessoas);

		return beanDtoGraficoPessoas;
	}

	public ModelLogin gravarUsuario(ModelLogin objeto, Long userLogado) throws Exception {

		if (objeto.isNovo()) { /* Gravar um novo usuario */

			String sql = "insert into model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero, datanascimento) " +
					"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

			PreparedStatement statementSql = connection.prepareStatement(sql);

			statementSql.setString(1, objeto.getLogin());
			statementSql.setString(2, objeto.getSenha());
			statementSql.setString(3, objeto.getNome());
			statementSql.setString(4, objeto.getEmail());
			statementSql.setLong(5, userLogado);
			statementSql.setString(6, objeto.getPerfil());
			statementSql.setString(7, objeto.getSexo());

			statementSql.setString(8, objeto.getCep());
			statementSql.setString(9, objeto.getLogradouro());
			statementSql.setString(10, objeto.getBairro());
			statementSql.setString(11, objeto.getLocalidade());
			statementSql.setString(12, objeto.getUf());
			statementSql.setString(13, objeto.getNumero());
			statementSql.setDate(14, objeto.getDataNascimento());

			statementSql.execute();
			connection.commit();

			if (objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
				sql = "update model_login set fotouser =?, extensaofotouser=? where login =?";

				statementSql = connection.prepareStatement(sql);

				statementSql.setString(1, objeto.getFotouser());
				statementSql.setString(2, objeto.getExtensaofotouser());
				statementSql.setString(3, objeto.getLogin());

				statementSql.execute();
				connection.commit();

			}
		} else {

			String sql = "update model_login set login=?,senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=?, datanascimento=? where id = "
					+ objeto.getId() + ";";

			PreparedStatement prepareSql = connection.prepareStatement(sql);
			prepareSql.setString(1, objeto.getLogin());
			prepareSql.setString(2, objeto.getSenha());
			prepareSql.setString(3, objeto.getNome());
			prepareSql.setString(4, objeto.getEmail());
			prepareSql.setString(5, objeto.getPerfil());
			prepareSql.setString(6, objeto.getSexo());

			prepareSql.setString(7, objeto.getCep());
			prepareSql.setString(8, objeto.getLogradouro());
			prepareSql.setString(9, objeto.getBairro());
			prepareSql.setString(10, objeto.getLocalidade());
			prepareSql.setString(11, objeto.getUf());
			prepareSql.setString(12, objeto.getNumero());
			prepareSql.setDate(13, objeto.getDataNascimento());

			prepareSql.executeUpdate();

			connection.commit();

			if (objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
				
				sql = "update model_login set fotouser =?, extensaofotouser =? where id =?";
				
				prepareSql = connection.prepareStatement(sql);
				prepareSql.setString(1, objeto.getFotouser());
				prepareSql.setString(2, objeto.getExtensaofotouser());
				prepareSql.setLong(3, objeto.getId());

				prepareSql.execute();
				connection.commit();
			}
		}

		return this.consultaUsuario(objeto.getLogin(), userLogado);
	}

	/* método de consultar em tela */

	public List<ModelLogin> consultaUsuarioListPaginado(Long userLogado, Integer offset) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado + " order by nome offset "+offset+" limit 5";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* Percorre as linhas de resultado do SQL */

			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			retorno.add(modelLogin);
		}

		return retorno;
	}

	public int totalPagina(Long userLogado) throws Exception {
		String sql = "select count(1) as total from model_login where usuario_id = " + userLogado;

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();
		resultado.next();

		Double cadastros = resultado.getDouble("total");
		Double porPagina = 5.0;
		Double pagina = cadastros / porPagina;
		Double resto = pagina % 2;

		if (resto > 0) {
			pagina ++;
		}
		return pagina.intValue();
	}

	public List<ModelLogin> consultaUsuarioListRel(Long userLogado) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado;

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* Percorre as linhas de resultado do SQL */

			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setTelefones(this.listaFone(modelLogin.getId()));

			retorno.add(modelLogin);
		}

		return retorno;
	}

	public List<ModelLogin> consultaUsuarioListRel(Long userLogado, String dataInicial, String dataFinal) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado + " and datanascimento >= ? and datanascimento <= ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setDate(1, Date.valueOf(dataInicial));
		statement.setDate(2, Date.valueOf(dataFinal));

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* Percorre as linhas de resultado do SQL */

			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setTelefones(this.listaFone(modelLogin.getId()));

			retorno.add(modelLogin);
		}

		return retorno;
	}

	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado + " limit 5";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* Percorre as linhas de resultado do SQL */

			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			retorno.add(modelLogin);
		}

		return retorno;
	}

	public int consultaUsuarioListTotalPaginacao(String nome, Long userLogado) throws Exception {

		String sql = "select count(1) as total from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? ";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		resultado.next();

		Double cadastros = resultado.getDouble("total");
		Double porPagina = 5.0;
		Double pagina = cadastros / porPagina;
		Double resto = pagina % 2;

		if (resto > 0) {
			pagina ++;
		}
		return pagina.intValue();
	}

	public List<ModelLogin> consultaUsuarioListOffSet(String nome, Long userLogado, int offset) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? offset "+offset+" limit 5";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* Percorre as linhas de resultado do SQL */

			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			retorno.add(modelLogin);
		}

		return retorno;
	}

	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? limit 5";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* Percorre as linhas de resultado do SQL */

			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			retorno.add(modelLogin);
		}

		return retorno;
	}

	public ModelLogin consultaUsuarioLogado(String login) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where upper(login) = upper('" + login + "')";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));

			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
		}

		return modelLogin;

	}

	public ModelLogin consultaUsuario(String login) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where upper(login) = upper('" + login + "') and useradmin is false ";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));

			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
		}

		return modelLogin;
	}

	public ModelLogin consultaUsuario(String login, Long userLogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where upper(login) = upper('" + login
				+ "') and useradmin is false and usuario_id = " + userLogado;

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));

			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
		}

		return modelLogin;
	}

	public ModelLogin consultaUsuarioID(String id, Long userLogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where id = ? and useradmin is false and usuario_id = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));

			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
		}

		return modelLogin;
	}

	public ModelLogin consultaUsuarioID(Long id) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where id = ? and useradmin is false";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));

			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
		}

		return modelLogin;
	}

	public boolean validaLogin(String login) throws Exception {

		String sql = "select count(1) > 0 as existe from model_login where upper(login) = upper('" + login + "');";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		resultado.next();
		return resultado.getBoolean("existe");

	}

	public void deletarUser(String idUser) throws Exception {
		String sql = "delete from model_login where id = ? and useradmin is false;";
		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setLong(1, Long.parseLong(idUser));

		statement.executeUpdate();

		connection.commit();
	}

	public List<ModelTelefone> listaFone (Long idUserPai) throws Exception{

		List<ModelTelefone> retorno = new ArrayList<ModelTelefone>();

		String sql = "select * from telefone where usuario_pai_id =?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, idUserPai);

		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			ModelTelefone modelTelefone = new ModelTelefone();

			modelTelefone.setId(rs.getLong("id"));
			modelTelefone.setNumero(rs.getString("numero"));
			modelTelefone.setUsuario_cad_id(this.consultaUsuarioID(rs.getLong("usuario_cad_id")));
			modelTelefone.setUsuario_pai_id(this.consultaUsuarioID(rs.getLong("usuario_pai_id")));

			retorno.add(modelTelefone);
		}
		return retorno;
	}
}
