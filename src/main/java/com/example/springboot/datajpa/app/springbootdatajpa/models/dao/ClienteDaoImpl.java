package com.example.springboot.datajpa.app.springbootdatajpa.models.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ClienteDaoImpl implements IClienteDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Cliente> findAll() {
        try {
            return em.createQuery("from Cliente").getResultList();
        } catch (UnsupportedOperationException e) {
            System.err.println("Exception caught: " + e.getMessage());
            return new ArrayList<>(); // Retorna una lista vacía en caso de excepción
        }
    }

    @Override
    public void save(Cliente cliente) {
        try {
            if (cliente.getId() != null && cliente.getId() > 0) {
                em.merge(cliente);
            } else {
                em.persist(cliente);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar/actualizar el cliente", e);
        }

    }

    @Override
    public Cliente findOne(Long id) {
        try {
            return em.find(Cliente.class, id);
        } catch (Exception e) {
            System.err.println("Error al recuperar el cliente con ID: " + id);
            throw new RuntimeException("Error al recuperar el cliente", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            em.remove(findOne(id));
        } catch (Exception e) {
            System.err.println("Error al eliminar el cliente con ID: " + id);
            throw new RuntimeException("Error al eliminar el cliente", e);
        }
    }

}
