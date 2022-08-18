package com.app.java.services;

import com.app.java.models.Destinos;
import com.app.java.models.Usuarios;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface IUsuarioServices {


    List<Usuarios> getAll();

    Usuarios getById(Long id);

    void remove(Long id);

    void save(Usuarios usuario);

    Usuarios obtenerUsuarioPorCredenciales(Usuarios usuario);

    void saveImagen(MultipartFile file, String name);

    void update(Usuarios usuario);

    String getByEmail(String email);

    String updatePassword(Usuarios usuario);

    List<Destinos>  getDestinoByUsuario(long id);
}
