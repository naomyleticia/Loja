package com.example.minhaloja.controle;

import java.util.Optional;

import com.example.minhaloja.modelo.Pedido;
import com.example.minhaloja.repositorios.RepositorioPedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ControladorPedido {

    @Autowired
    RepositorioPedido repositorioPedido;

    @RequestMapping("/Pedido/{idPedido}")
    public ModelAndView detalhesDoPedido(@PathVariable("idPedido") long id) {
        ModelAndView retorno = new ModelAndView("Pedido");
        Optional<Pedido> pedido = repositorioPedido.findById(id);

        retorno.addObject("pedido", pedido.get());
        return retorno;
    }

}