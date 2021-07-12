package br.com.zup.gerenciador.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/oi-mundo")
public class OiMundoServlet extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String html = "<html>\n"
				+ "<body>\n"
				+ "<h3>Oi mundo, sou uma servlet!</h3>\n"
				+ "</body>\n"
				+ "</html>";
		var out = resp.getWriter();
		out.println(html);
		System.out.println("OiMundoServlet foi chamada");
	}
}
