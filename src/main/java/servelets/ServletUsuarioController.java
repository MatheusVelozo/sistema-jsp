package servelets;

import beanDTO.BeanDtoGraficoPessoas;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLogin;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import util.ReportUtil;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

@MultipartConfig
public class ServletUsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;

	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public ServletUsuarioController() {

	}
	
	/* Consultar e deletar */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String acao = request.getParameter("acao");

			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {

				String idUser = request.getParameter("id");

				daoUsuarioRepository.deletarUser(idUser);
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				
				request.setAttribute("msg", "Excluido com sucesso!");
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {

				String idUser = request.getParameter("id");

				daoUsuarioRepository.deletarUser(idUser);

				response.getWriter().write("Excluido com sucesso!");

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {

				String nomeBusca = request.getParameter("nomeBusca");

				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca, super.getUserLogado(request));

				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writeValueAsString(dadosJsonUser);

				response.addHeader("totalPagina", ""+ daoUsuarioRepository.consultaUsuarioListTotalPaginacao(nomeBusca, super.getUserLogado(request)));
				response.getWriter().write(json);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {

				String nomeBusca = request.getParameter("nomeBusca");
				String pagina = request.getParameter("pagina");

				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioListOffSet(nomeBusca, super.getUserLogado(request), Integer.parseInt(pagina));

				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writeValueAsString(dadosJsonUser);

				response.addHeader("totalPagina", ""+ daoUsuarioRepository.consultaUsuarioListTotalPaginacao(nomeBusca, super.getUserLogado(request)));
				response.getWriter().write(json);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {

				String id = request.getParameter("id");

				ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(id.trim(), super.getUserLogado(request));
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);

				request.setAttribute("msg", "Usuário em edição");
				RequestDispatcher redireciona = request.getRequestDispatcher("principal/usuario.jsp");
				request.setAttribute("modelLogin", modelLogin);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp");
				redireciona.forward(request, response);
			}

			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {

				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));

				request.setAttribute("msg", "Usuários carregados...");
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

			}
			
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {

				String idUser = request.getParameter("id");
				
				ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(idUser, super.getUserLogado(request));
				
				if (modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {
					
					response.setHeader("Content-Disposition", "attachment;filename=arquivo." + modelLogin.getExtensaofotouser());
					response.getOutputStream().write(new Base64().decodeBase64(modelLogin.getFotouser().split("\\,")[1]));
				}

			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
				Integer offset = Integer.parseInt(request.getParameter("pagina"));

				List<ModelLogin> modelLoginList = daoUsuarioRepository.consultaUsuarioListPaginado(this.getUserLogado(request), offset);

				request.setAttribute("modelLogins", modelLoginList);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUser")) {

				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");

				if (dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()) {

					request.setAttribute("listaUser", daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request)));

				} else {
					request.setAttribute("listaUser", daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request), dataInicial, dataFinal));
				}

				request.setAttribute("dataInicial", dataInicial);
				request.setAttribute("dataFinal", dataFinal);
				request.getRequestDispatcher("principal/relUser.jsp").forward(request, response);
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioPDF")
			|| acao.equalsIgnoreCase("imprimirRelatorioExcel")) {

				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");

				List<ModelLogin> modelLogins = null;

				if ((dataInicial == null || dataInicial.isEmpty()) && (dataFinal == null || dataFinal.isEmpty())) {

					modelLogins = daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request));

				} else {

					modelLogins = daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request), dataInicial, dataFinal);

				}

				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorio") + File.separator);

				byte[] relatorio = null;
				String extensao = "";

				if (acao.equalsIgnoreCase("imprimirRelatorioPDF")) {
					relatorio = new ReportUtil().geraRelatorioPDF(modelLogins, "rel-user-jsp", params, request.getServletContext());
					extensao = "pdf";
				} else {
					if (acao.equalsIgnoreCase("imprimirRelatorioExcel")) {
						relatorio = new ReportUtil().geraRelatorioExcel(modelLogins, "rel-user-jsp", params, request.getServletContext());
						extensao = "xls";
					}
				}


				response.setHeader("Content-Disposition", "attachment;filename=arquivo." + extensao);
				response.getOutputStream().write(relatorio);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("graficoPessoas")) {

				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");

				if ((dataInicial == null || dataInicial.isEmpty()) && (dataFinal == null || dataFinal.isEmpty())) {

					BeanDtoGraficoPessoas beanDtoGraficoPessoas = daoUsuarioRepository.
							montarGraficoMediaPessoas(super.getUserLogado(request));

					ObjectMapper objectMapper = new ObjectMapper();
					String json = objectMapper.writeValueAsString(beanDtoGraficoPessoas);

					response.getWriter().write(json);

				} else {

					BeanDtoGraficoPessoas beanDtoGraficoPessoas = daoUsuarioRepository.
							montarGraficoMediaPessoas(super.getUserLogado(request), dataInicial, dataFinal);

					ObjectMapper objectMapper = new ObjectMapper();
					String json = objectMapper.writeValueAsString(beanDtoGraficoPessoas);

					response.getWriter().write(json);

				}

			} else {
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}

	}

	/* Salvar e atualizar */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String msg = "";

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String perfil = request.getParameter("perfil");
			String Sexo = request.getParameter("sexo");
			String cep = request.getParameter("cep");
			String logradouro = request.getParameter("logradouro");
			String bairro = request.getParameter("bairro");
			String localidade = request.getParameter("localidade");
			String uf = request.getParameter("uf");
			String numero = request.getParameter("numero");
			String dataNascimento = request.getParameter("dataNascimento");

			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			modelLogin.setPerfil(perfil);
			modelLogin.setSexo(Sexo);
			modelLogin.setCep(cep);
			modelLogin.setLogradouro(logradouro);
			modelLogin.setBairro(bairro);
			modelLogin.setLocalidade(localidade);
			modelLogin.setUf(uf);
			modelLogin.setNumero(numero);
			modelLogin.setDataNascimento(Date.valueOf(dataNascimento));
			
			if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {
				
				Part part = request.getPart("fileFoto"); /*pega foto da tela*/
				
				if (part.getSize() > 0) {
				byte[] foto = IOUtils.toByteArray(part.getInputStream()); /*converte imagem para byte*/
				String imagemBase64 = "data:image/" + part.getContentType().split("\\/")[1] + ";base64," + new Base64().encodeBase64String(foto);
					
				modelLogin.setFotouser(imagemBase64);
				modelLogin.setExtensaofotouser(part.getContentType().split("\\/")[1]);
				}
				
				}

			if (daoUsuarioRepository.validaLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				msg = "Login ja cadastrado!";
			} else {

				if (modelLogin.isNovo()) {
					msg = "Gravado com sucesso!";
				} else {
					msg = "Atualizado com sucesso!";
				}

				modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin, super.getUserLogado(request));

			}
			
			List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			request.setAttribute("modelLogins", modelLogins);

			String origem = request.getParameter("origem");

			if ("cadastro-login".equals(origem)) {

				msg = "Cadastro efetuado com sucesso!";
				request.setAttribute("msg", msg);
				RequestDispatcher redireciona = request.getRequestDispatcher("index.jsp");
				request.setAttribute("modelLogin", modelLogin);
				redireciona.forward(request, response);

			} else { 

				request.setAttribute("msg", msg);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				RequestDispatcher redireciona = request.getRequestDispatcher("principal/usuario.jsp");
				request.setAttribute("modelLogin", modelLogin);
				redireciona.forward(request, response);

			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}

	}

}
