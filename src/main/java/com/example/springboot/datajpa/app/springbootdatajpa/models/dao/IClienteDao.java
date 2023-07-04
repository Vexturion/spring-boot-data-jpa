package com.example.springboot.datajpa.app.springbootdatajpa.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;

public interface IClienteDao extends JpaRepository<Cliente, Long> {



}
