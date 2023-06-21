package com.example.springboot.datajpa.app.springbootdatajpa.models.dao;

import java.util.List;

import com.example.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;

public interface IClienteDao {

    public List<Cliente> findAll();

}
