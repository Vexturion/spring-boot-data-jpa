package com.example.springboot.datajpa.app.springbootdatajpa.models.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ClienteDaoImpl implements IClienteDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        try {
            return em.createQuery("from Cliente").getResultList();
        } catch (UnsupportedOperationException e) {
            System.err.println("Exception caught: " + e.getMessage());
            return new ArrayList<>(); // Retorna una lista vacía en caso de excepción
        }
    }

}
