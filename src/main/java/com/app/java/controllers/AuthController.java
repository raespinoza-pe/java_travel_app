package com.app.java.controllers;
import com.app.java.models.Usuarios;
import com.app.java.services.IUsuarioServices;
import com.app.java.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private IUsuarioServices services;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/api/login")
    public String login(@RequestBody Usuarios usuario) {

        Usuarios usuarioLogeado = services.obtenerUsuarioPorCredenciales(usuario);
        if( usuarioLogeado != null) {
            String token = jwtUtil.create(String.valueOf(usuarioLogeado.getId()), usuarioLogeado.getEmail());
            return token;
        }
        return "fail";

    }


}
