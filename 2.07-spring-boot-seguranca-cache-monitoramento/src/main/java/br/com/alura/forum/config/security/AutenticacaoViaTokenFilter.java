package br.com.alura.forum.config.security;

import br.com.alura.forum.modelo.entities.Usuario;
import br.com.alura.forum.modelo.repositories.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UsuarioRepository usuarioRepository;

    public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request);

        boolean valido = tokenService.isTokenValido(token);

        if(valido) {
            autenticarCliente(token);
        }

        filterChain.doFilter(request, response);
    }

    private void autenticarCliente(String token) {
        Long idusuario = tokenService.getIdusuario(token);
        Optional<Usuario> opt = usuarioRepository.findById(idusuario);

        if(opt.isPresent()) {
            Usuario usuario = opt.get();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getPerfis());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }


    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if(token == null || token.length() < 8 || !token.startsWith("Bearer ")){
            return null;
        }
        else {
            return token.substring(7);
        }
    }
}
