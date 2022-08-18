package com.app.java.controllers;

import com.app.java.models.Destinos;
import com.app.java.models.Usuarios;
import com.app.java.services.IUsuarioServices;
import com.app.java.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private IUsuarioServices services;
    @Autowired
    private JWTUtil jwtUtil;
    private boolean validarToken(String token){
        String usuarioId = jwtUtil.getKey(token);
        return usuarioId != null;
    }

    @GetMapping("/api/usuarios")
    public List<Usuarios> getAll(@RequestHeader(value = "Authorization") String token){
        if (!validarToken(token)){return null;}
        return services.getAll();
    }

    @GetMapping("/api/usuarios/{id}")
    public Usuarios getById(@RequestHeader(value = "Authorization") String token,@PathVariable String id){
        if (!validarToken(token)){return null;}
        return services.getById(Long.parseLong(id));
    }

    @GetMapping("/api/usuarios/email/{email}")
    public String getByEmail(@PathVariable String email){
        return services.getByEmail(email);
    }

    @DeleteMapping("/api/usuarios/{id}")
    public void remove(@RequestHeader(value = "Authorization") String token, @PathVariable String id) {
        if (!validarToken(token)){return;}
        services.remove(Long.parseLong(id));
    }

    @PostMapping("/api/usuarios")
    public void save(@RequestBody Usuarios usuario) {
        services.save(usuario);
    }

    @PostMapping("/api/subir/img")
    public void saveImagen(@RequestParam("image") MultipartFile file, @RequestParam("name") String name) {
        services.saveImagen(file,name);
    }

    @PutMapping("/api/usuarios/")
    public void update(@RequestHeader(value = "Authorization") String token,@RequestBody Usuarios usuario) {
        if (!validarToken(token)){return;}
        services.update(usuario);
    }

    @PutMapping("/api/usuarios/password")
    public String updatePassword(@RequestHeader(value = "Authorization") String token,@RequestBody Usuarios usuario) {
        if (!validarToken(token)){return null;}
        return services.updatePassword(usuario);
    }

    @GetMapping("/api/usuarios/destinos/{id}")
    public List<Destinos>  getDestinoByUsuario(@RequestHeader(value = "Authorization") String token, @PathVariable String id){
        if (!validarToken(token)){return null;}
        return services.getDestinoByUsuario(Long.parseLong(id));
    }


}
