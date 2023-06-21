/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tendencias.app.Usuarios.model;




import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;




@Data
@Entity
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue
    
    private int id_usuario;

    //@Size(min = 3, max = 10, message = "El usuario debe tener entre 3 y 10 caracteres")
   // @NotBlank(message = "El usuario no puede estar en blanco")
    @NonNull
    
    private String nombre;
    
    //@NotBlank(message = "La clave no puede estar en blanco")
    //@Column(name = "clave")
    private String clave;

    //@Email(message = "Debe ingresar una dirección de correo válida")
    //@Column(name = "email")
    private String email;
    
    
    private int estado;
    
    @Transient
    private String foto;
    
    private String cedula;

}
