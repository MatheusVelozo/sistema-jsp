package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connection.SingleConnectionBanco;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/*
 * Intercepta todas as requisições que vierem do projeto ou mapeamento.
 */
@WebFilter (urlPatterns = {"/principal/*"})
public class filterAutenticacao extends HttpFilter implements Filter {
	
	private static Connection connection;
       
    
    public filterAutenticacao() {
        super();
        
    }

	/*
	 * Encerra os processos quando o servidor é parado.
	 */
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Intercepta todas requisições e as respostas no sistema.
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		try {
		
			HttpServletRequest req = (HttpServletRequest) request; 
			HttpSession session = req.getSession();
			
			String usuarioLogado = (String) session.getAttribute("usuario");
			
			String urlParaAutenticar = req.getServletPath();
			
			/*Validar se esta logado, se não, redireciona para a tela de login*/
			
			if (usuarioLogado == null &&
					!urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {
				
				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor realize o login!");
				redireciona.forward(request, response);
				return; /*Para a execução e redireciona para o login*/
			
			} else {
				
				chain.doFilter(request, response);
			}
			
			connection.commit(); /* Deu tudo certo, então comita as alterações no BD. */
		
		} catch (Exception e) {
			e.printStackTrace();
			
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	/*
	 * Inicia os processos ou recursos quando o servidor sobe o projeto.
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnectionBanco.getConnection(); /* Inicia a conexão quando subir o projeto */
	}

}
