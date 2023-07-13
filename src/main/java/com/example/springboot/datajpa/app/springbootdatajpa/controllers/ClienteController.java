package com.example.springboot.datajpa.app.springbootdatajpa.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;
import com.example.springboot.datajpa.app.springbootdatajpa.service.ClienteServiceImpl;
import com.example.springboot.datajpa.app.springbootdatajpa.util.paginator.PageRender;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

    @Autowired
    private ClienteServiceImpl clienteService;

    @GetMapping("/listar")
    public String listar(@RequestParam(name ="page", defaultValue="0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 10);
        Page<Cliente> clientes = clienteService.findAll(pageRequest);
        PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
        model.addAttribute("titulo", "Listado de clientes");
        model.addAttribute("clientes", clientes);
        model.addAttribute("page", pageRender);
        return "listar";
    }

    @GetMapping("/form")
    public String crear(Model model, RedirectAttributes flash) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("titulo", "Formulario de cliente");
        return "form";
    }

    @PostMapping("/form")
public String guardar(@Valid Cliente cliente, BindingResult result, Model model,@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
    if (result.hasErrors()) {
        model.addAttribute("titulo", "Formulario de cliente");
        model.addAttribute("errors", result.getFieldErrors());
        return "form";
    }

    if (!foto.isEmpty()) {
        Path directorioRecursos = Paths.get("src//main//resources//static//uploads");
        String rootPath = directorioRecursos.toFile().getAbsolutePath();
        try {
            byte[] bytes = foto.getBytes();
            Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
            Files.write(rutaCompleta, bytes);
            cliente.setFoto(foto.getOriginalFilename());

            // Mover el mensaje de confirmación aquí
            flash.addFlashAttribute("info", "Has subido correctamente '" + foto.getOriginalFilename() + "'");
        } catch (Exception e) {
            e.printStackTrace();

            // Agregar manejo de errores
            flash.addFlashAttribute("error", "Error al subir el archivo: " + e.getMessage());
            return "form";
        }
    }
    String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con éxito" : "Cliente creado con éxito";
    clienteService.save(cliente);
    status.setComplete();
    flash.addFlashAttribute("success", mensajeFlash);
    return "redirect:listar";
}




    @GetMapping("/form/{id}")
    public String editar(@PathVariable Long id, ModelMap model, RedirectAttributes flash) {
        if (id == null || id <= 0) {
            flash.addFlashAttribute("error", "El ID del cliente es inválido");
            return "redirect:/listar";
        }

        Cliente cliente = clienteService.findOne(id);
        if (cliente == null) {
            flash.addFlashAttribute("warning", "El ID del cliente no existe en la base de datos");
            return "redirect:/listar";
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar cliente");
        return "form";
    }

    @RequestMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        if (id > 0) {
            clienteService.delete(id);
            flash.addFlashAttribute("success", "Cliente eliminado con exito");
        }
        return "redirect:/listar";
    }



}
