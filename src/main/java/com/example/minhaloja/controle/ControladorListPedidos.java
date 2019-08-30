package com.example.minhaloja.controle;

import com.example.minhaloja.modelo.Pedido;
import com.example.minhaloja.repositorios.RepositorioPedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ControladorListPedidos {

    @Autowired
    RepositorioPedido repositorioPedido;

    @RequestMapping("/listarPedidos")
    public ModelAndView listarPedidos() {
        ModelAndView retorno = new ModelAndView("listarPedidos.html");

        Iterable<Pedido> pedidos = repositorioPedido.findAll();

        retorno.addObject("pedidos", pedidos);

        return retorno;
    }

}