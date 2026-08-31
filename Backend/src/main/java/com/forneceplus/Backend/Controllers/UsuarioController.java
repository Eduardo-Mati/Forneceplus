package com.forneceplus.Backend.Controllers;

import com.forneceplus.Backend.Entities.Usuario;
import com.forneceplus.Backend.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    private ResponseEntity<List<Usuario>> ListarUsuarios(){
        return ResponseEntity.ok(usuarioService.ListarUsuarios());
    }

    @PostMapping
    private ResponseEntity<Usuario> SalvarUsuario(@RequestBody Usuario usuario){

        Usuario usuarioSalvo = usuarioService.SalvarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Usuario> BuscarUsuarioPorId(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioService.BuscarUsuarioPorId(id);

        if(usuario.isPresent()){
            return ResponseEntity.ok(usuario.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> DeletarUsuario(@PathVariable Long id){
        usuarioService.DeletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<Usuario> AtualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        Usuario usuarioAtualizado = usuarioService.AtualizarUsuario(id, usuario);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioAtualizado);
    }

}
