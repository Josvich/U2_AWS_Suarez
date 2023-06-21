/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tendencias.app.Usuarios.controller;

import com.tendencias.app.Usuarios.model.Usuario;
import com.tendencias.app.Usuarios.repository.UsuarioRepository;
import com.tendencias.app.Usuarios.service.S3Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
     private S3Service s3service;

    @GetMapping
    List<Usuario> getAll(){
        return usuarioRepository.findAll()
                .stream()
                .peek(curso -> curso.setCedula(s3service.getObjectUrl(curso.getCedula())))
                .collect(Collectors.toList());
    }
    
    @PostMapping
    Usuario create(@RequestBody Usuario curso){
        usuarioRepository.save(curso);
        curso.setCedula(s3service.getObjectUrl(curso.getCedula()));
        return curso;
    }
   

}
