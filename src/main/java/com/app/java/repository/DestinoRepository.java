package com.app.java.repository;

import com.app.java.models.Destinos;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinoRepository extends CrudRepository<Destinos,Long> {


}
