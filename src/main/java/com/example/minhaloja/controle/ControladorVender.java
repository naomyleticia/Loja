package com.example.minhaloja.controle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.minhaloja.modelo.Cliente;
import com.example.minhaloja.modelo.Item;
import com.example.minhaloja.modelo.Pedido;
import com.example.minhaloja.repositorios.RepositorioCliente;
import com.example.minhaloja.repositorios.RepositorioItem;
import com.example.minhaloja.repositorios.RepositorioPedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ControladorVender {

    @Autowired
    RepositorioPedido repositioPedido;

    @Autowired
    RepositorioItem repositorioItem;

    @Autowired
    RepositorioCliente repositorioCliente;

    @RequestMapping("/realizar_venda")
    public ModelAndView formularioCadastrarPedido() {
        ModelAndView retorno = new ModelAndView("vender.html");
        Iterable<Item> itens = repositorioItem.findAll();
        Iterable<Cliente> clientes = repositorioCliente.findAll();

        retorno.addObject("itens", itens);
        retorno.addObject("clientes", clientes);

        return retorno;
    }

    @RequestMapping("/formulario_Venda")
    public ModelAndView cadastrarPedido(String itensId, Long clienteId, RedirectAttributes redirect) {
        Optional<Cliente> cliente = repositorioCliente.findById(clienteId);
        ModelAndView retorno = new ModelAndView("redirect:/realizar_venda");
        if (itensId == null) {
            redirect.addFlashAttribute("mensagem", "Selecione ao menos um item");
            return retorno;
        }

        List<Item> itens = obtListaDeItens(itensId);

        Double Total = obtValorTotalDoPedido(itens);

        Date data = new Date();

        Pedido pedido = new Pedido(cliente.get(), itens, data, Total);

        repositioPedido.save(pedido);

        redirect.addFlashAttribute("mensagem", "Compras realizada com sucesso!");
        return retorno;
    }

    private List<Item> obtListaDeItens(String itensId) {
        List<Item> itens = new ArrayList<Item>();
        String vtDeItensId[] = itensId.split(",");

        for (String itemID : vtDeItensId) {
            Optional<Item> item = repositorioItem.findById(Long.parseUnsignedLong(itemID));
            itens.add(item.get());
        }
        return itens;
    }

    private Double obtValorTotalDoPedido(List<Item> itens) {
        Double Total = 0.0;
        for (Item item : itens) {
            Total += item.getPreco();
        }
        return Total;
    }
}