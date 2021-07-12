package br.com.zup.gerenciador.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/nova-empresa")
public class NovaEmpresaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeEmpresa = request.getParameter("nome");
		
		PrintWriter out = response.getWriter();
		
		out.println("<html><body>" + nomeEmpresa  + " cadastrada com sucesso.</body></html>");
		
		System.out.println(nomeEmpresa  + " cadastrada com sucesso.");
	}

}
