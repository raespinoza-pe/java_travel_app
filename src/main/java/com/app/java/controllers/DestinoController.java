package com.app.java.controllers;


import com.app.java.models.Destinos;
import com.app.java.models.Usuarios;
import com.app.java.services.IDestinoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.app.java.utils.JWTUtil;
import java.util.List;


@RestController
public class DestinoController {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private IDestinoServices services;
    private boolean validarToken(String token){
        String usuarioId = jwtUtil.getKey(token);
        return usuarioId != null;
    }

    @GetMapping("/api/destinos")
    public List<Destinos>  getAll(/*@RequestHeader(value = "Authorization") String token, */){
        //if (!validarToken(token)){return null;}
        return services.getAll();
    }

    @PostMapping("/api/destinos")
    public void save(@RequestBody Destinos Destino) {
        services.save(Destino);
    }

}
