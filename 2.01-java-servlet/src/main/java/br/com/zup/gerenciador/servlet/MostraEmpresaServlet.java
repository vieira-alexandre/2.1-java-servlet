package br.com.zup.gerenciador.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.zup.gerenciador.model.Banco;
import br.com.zup.gerenciador.model.Empresa;

@WebServlet("/mostraEmpresa")
public class MostraEmpresaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String paramId = request.getParameter("id");
		Integer id = null;
		
		try {
			id = Integer.valueOf(paramId);
		} 
		catch(NumberFormatException e) {
			throw new ServletException(e);
		}
		
		Banco banco = new Banco();
		Empresa empresa = banco.buscaPorId(id);
		
		request.setAttribute("empresa", empresa);
		var dispatcher = request.getRequestDispatcher("/formMostraEmpresa.jsp");
		dispatcher.forward(request, response);
	}

}
