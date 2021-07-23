package br.com.alura.forum.config.security;

import br.com.alura.forum.modelo.entities.Usuario;
import br.com.alura.forum.modelo.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> opt = repository.findByEmail(username);

        if(opt.isPresent()) {
            return opt.get();
        }

        throw new UsernameNotFoundException("Dados inválidos");
    }
}
