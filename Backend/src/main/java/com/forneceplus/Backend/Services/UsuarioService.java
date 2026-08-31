package com.forneceplus.Backend.Services;
import com.forneceplus.Backend.Entities.Usuario;
import com.forneceplus.Backend.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> ListarUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario SalvarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> BuscarUsuarioPorId (Long Id){
        return usuarioRepository.findById(Id);
    }

    public void DeletarUsuario(Long Id){
        usuarioRepository.deleteById(Id);
    }

    public Usuario AtualizarUsuario(Long id, Usuario usuarioNovo){

        Usuario usuarioAntigo = usuarioRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (usuarioNovo.getCPF() != null) {
            usuarioAntigo.setCPF(usuarioNovo.getCPF());
        }
        if (usuarioNovo.getEmail() != null){
            usuarioAntigo.setEmail(usuarioNovo.getEmail());
        }
        if(usuarioNovo.getEndereco() != null){
            usuarioAntigo.setEndereco(usuarioNovo.getEndereco());
        }
        if (usuarioNovo.getNome() != null){
            usuarioAntigo.setNome(usuarioNovo.getNome());
        }
        if (usuarioNovo.getSenha() != null) {
            usuarioAntigo.setSenha(usuarioNovo.getSenha());
        }
        if(usuarioNovo.getTelefone() != null){
            usuarioAntigo.setTelefone(usuarioNovo.getTelefone());
        }
        if (usuarioNovo.getIdUsuario() != null) {
            usuarioAntigo.setIdUsuario(usuarioNovo.getIdUsuario());
        }
        if(usuarioNovo.getTelefone() != null){
            usuarioAntigo.setTelefone(usuarioNovo.getTelefone());
        }

        return usuarioRepository.save(usuarioAntigo);









    }

}
