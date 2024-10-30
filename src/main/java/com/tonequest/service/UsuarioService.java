package com.tonequest.service;

import com.tonequest.model.Usuario;
import com.tonequest.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Registro de novo usuário com verificação de e-mail existente
    public Usuario registrarUsuario(Usuario usuario) throws Exception {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new Exception("E-mail já cadastrado.");
        }
        return usuarioRepository.save(usuario);
    }

    // Autenticação do usuário
    public Usuario autenticarUsuario(String email, String senha) throws Exception {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Usuário não encontrado."));

        if (!usuario.getSenha().equals(senha)) {
            throw new Exception("Senha incorreta.");
        }
        return usuario;
    }

    // Encontrar usuário por ID
    public Usuario encontrarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
}
