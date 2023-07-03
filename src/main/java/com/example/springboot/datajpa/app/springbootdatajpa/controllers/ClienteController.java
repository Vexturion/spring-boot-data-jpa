package com.example.springboot.datajpa.app.springbootdatajpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;
import com.example.springboot.datajpa.app.springbootdatajpa.service.ClienteServiceImpl;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

    @Autowired
    private ClienteServiceImpl clienteService;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de clientes");
        model.addAttribute("clientes", clienteService.findAll());
        return "listar";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("titulo", "Formulario de cliente");
        return "form";
    }

    @PostMapping("/form")
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status) {
        System.out.println("cliente: " + cliente);
        System.out.println("result: " + result);
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de cliente");
            model.addAttribute("errors", result.getFieldErrors());
            return "form";
        }
        clienteService.save(cliente);
        status.setComplete();
        return "redirect:listar";
    }

    @RequestMapping("/form/{id}")
    public String editar(@PathVariable Long id, ModelMap model) {
        Cliente cliente = null;
        if (id > 0) {
            cliente = clienteService.findOne(id);
        } else {
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("titulo", "Editar cliente");
        return "form";
    }

    @RequestMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        if (id > 0) {
            clienteService.delete(id);
        }
        return "redirect:/listar";
    }

}
