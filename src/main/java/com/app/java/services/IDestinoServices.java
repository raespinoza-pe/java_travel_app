package com.app.java.services;

import com.app.java.models.Destinos;
import java.util.List;

public interface IDestinoServices {
    List<Destinos> getAll();

    void save(Destinos destino);
}
