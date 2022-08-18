package com.app.java.services;


import com.app.java.models.Destinos;
import com.app.java.models.Usuarios;
import com.app.java.repository.DestinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;

@Service
public class DestinoServices implements IDestinoServices{

    @Autowired
    private DestinoRepository repository;
    @PersistenceContext
    private EntityManager entityManager;
    public List<Destinos>  getAll(){
        System.out.println("hola");
        return (List<Destinos>) repository.findAll();

    }

    @Override
    public void save(Destinos destino) {
        System.out.println(destino);
        //repository.save(destino);
    }


}
