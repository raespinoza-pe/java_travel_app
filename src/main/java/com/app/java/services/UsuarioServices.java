package com.app.java.services;

import com.app.java.models.Destinos;
import com.app.java.models.Usuarios;
import com.app.java.repository.UsuarioRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
public class UsuarioServices implements IUsuarioServices{

    @Autowired
    private UsuarioRepository repository;
    @PersistenceContext
    private EntityManager entityManager;

    private String url_img = "E:/app_viajes/app_java_react/java/src/main/resources/static/img/";
    private final Logger logg = LoggerFactory.getLogger(UsuarioServices.class);

    @Override
    public List<Usuarios> getAll(){
        return (List<Usuarios>) repository.findAll();
    }

    @Override
    public Usuarios getById(Long id) {
        return (Usuarios) repository.findById(id).get();
    }

    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void save(Usuarios usuario) {
        File from = new File(url_img + "user.png");
        File to = new File(url_img + "perfil/"+usuario.getUsername()+".jpg");
        try {
            Files.copy(from.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);
        String hash = argon2.hash(1,1024,1,usuario.getPassword());
        usuario.setPassword(hash);
        repository.save(usuario);


    }

    @Override
    public Usuarios obtenerUsuarioPorCredenciales(Usuarios usuario) {

        String query = "FROM Usuarios Where email = :email";

        List<Usuarios> lista = entityManager.createQuery(query)
                .setParameter("email" , usuario.getEmail())
                .getResultList();

        if (lista.isEmpty()){
            return null;
        }

        String passwordHash = lista.get(0).getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);
        if (argon2.verify(passwordHash, usuario.getPassword())){
            return lista.get(0);
        }

        return null;

    }

    @Override
    public void saveImagen(MultipartFile file, String name){
            if (!file.isEmpty()){
                try {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(url_img + "perfil/" + name+ ".jpg");
                    Files.write(path,bytes);
                    logg.info("Archivo guardado");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

    }

    public void update(Usuarios usuario){
        Usuarios usuarios = repository.findById(usuario.getId()).get();
        usuarios.setNombre(usuario.getNombre());
        usuarios.setApellido(usuario.getApellido());
        File from =  new File(url_img + "perfil/"+usuarios.getUsername()+".jpg");
        File to = new File(url_img + "perfil/"+usuario.getUsername()+".jpg");
        from.renameTo(to);
        usuarios.setUsername(usuario.getUsername());
        repository.save(usuarios);
    }

    public String getByEmail(String email){
        String usuarioExiste = "no";
        List<Usuarios> listUsuario = (List<Usuarios>) repository.findAll();
        for (Usuarios p: listUsuario) {
            if (Objects.equals(p.getEmail(), email)){
                usuarioExiste = String.valueOf(p.getId());
            }
        }
        return usuarioExiste;

    }

    public String updatePassword(Usuarios usuario){

        String result = "fail";
        Usuarios usuarios = repository.findById(usuario.getId()).get();
        String passwordHash = usuarios.getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);
        if (argon2.verify(passwordHash, usuario.getPassword())){
            String hash = argon2.hash(1,1024,1,usuario.getUsername());
            usuarios.setPassword(hash);
            repository.save(usuarios);
            result = null;
        }
        return result;

    }

    @Override
    public List<Destinos> getDestinoByUsuario(long id) {

        Usuarios usuarios = repository.findById(id).get();
        List<Destinos> destinos = usuarios.getDestinos();
        return destinos;
    }


}
