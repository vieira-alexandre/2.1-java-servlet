package br.com.zup.gerenciador.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.zup.gerenciador.model.Banco;
import br.com.zup.gerenciador.model.Empresa;

@WebServlet(urlPatterns = "/editaEmpresa")
public class EditaEmpresaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String paramId = request.getParameter("id");
		String nome = request.getParameter("nome");
		String paramDataAbertura = request.getParameter("data");
		
		Integer id = null;
		Date dataAbertura = null;
		
		try {
			id = Integer.valueOf(paramId);
		} 
		catch(NumberFormatException e) {
			throw new ServletException(e);
		}
		
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataAbertura = sdf.parse(paramDataAbertura);
		} catch (ParseException e) {
			throw new ServletException(e);
		}
		
		Banco banco = new Banco();
		
		Empresa empresa = banco.buscaPorId(id);
		empresa.setNome(nome);
		empresa.setDataAbertura(dataAbertura);
		
		response.sendRedirect("listaEmpresas");
	}
}
