package com.app.java.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Entity
@Table(name = "destinos")
@ToString
@EqualsAndHashCode
public class Destinos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter @Setter
    private Long id;

    @Column(name = "nombre")
    @Getter @Setter
    private String nombre;

    @Column(name = "descripcion")
    @Getter @Setter
    private String descripcion;

    @Column(name = "hora_viaje")
    @Getter @Setter
    private double hora_viaje;

    @Column(name = "dia_viaje")
    @Getter @Setter
    private double dia_viaje;

    @Column(name = "presupuesto")
    @Getter @Setter
    private double presupuesto;


    @ManyToOne(targetEntity = Usuarios.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @Setter
    private Usuarios id_usuario;

    public long getId_usuario(){
        return id_usuario.getId();
    }


}
