package com.example.springboot.datajpa.app.springbootdatajpa.service;

import java.util.List;

import com.example.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;

public interface ICienteService {

    public List<Cliente> findAll();

    public void save(Cliente cliente);

    public Cliente findOne(Long id);

    public void delete(Long id);

}
