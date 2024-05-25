package com.registro.modelo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Nombre_de_usuario")  // Asumiendo que 'nombre de usuario' es 'nombre'
    private String nombreUsuario;

    @Column(name = "password")  // Asumiendo que 'Contraseña' es 'password'
    private String password;

    @Column(name = "Rol")
    private String rol;

    @Column(name = "apellido")  // Conservando minúscula para mantener coherencia con la tabla
    private String apellido;

    @Column(name = "email")  // Conservando minúscula para mantener coherencia con la tabla
    private String email;

    @Column(name = "nombre")  // Asumiendo que 'nombre' se refiere a otro tipo de nombre
    private String nombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Usuario(Long id, String nombre, String password, String rol, String apellido, String email, String nombreUsuario) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
        this.apellido = apellido;
        this.email = email;
        this.nombreUsuario = nombreUsuario;
    }

    public Usuario(String nombre, String password, String rol, String apellido, String email, String nombreUsuario) {
        super();
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
        this.apellido = apellido;
        this.email = email;
        this.nombreUsuario = nombreUsuario;
    }

    public Usuario() {
    }
}