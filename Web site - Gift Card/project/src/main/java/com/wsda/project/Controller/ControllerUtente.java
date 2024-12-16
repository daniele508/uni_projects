package com.wsda.project.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wsda.project.Model.Carta;
import com.wsda.project.Model.Movimento;
import com.wsda.project.Model.User;
import com.wsda.project.Repository.CardRepository;
import com.wsda.project.Repository.MovimentoRepository;
import com.wsda.project.Repository.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/user")
public class ControllerUtente{

    @Autowired
    private CardRepository repository;

    @Autowired 
    private UserRepository uRepository;

    @Autowired
    private MovimentoRepository mRepository;


    @GetMapping("/home")
    public String getHome(Model model){
        model.addAttribute("nome", this.getUser().getNome());
        model.addAttribute("cognome", this.getUser().getCognome());
        return "home";
    }

    @GetMapping("/movimenti")
    public String getMovimenti(Model model) {
        User user=this.getUser();
        List<Movimento> movimenti = this.mRepository.findByUser(user);
        model.addAttribute("ruolo", "user");
        if(movimenti.isEmpty()){
            model.addAttribute("testo", "Non hai effettuato nessun movimento");
            return "movimenti";
        }
        model.addAttribute("movimenti", movimenti);
        return "movimenti";
    }
    
    @GetMapping("/request")
    public String getRequestSaldo(Model model) {
        model.addAttribute("ruolo", "user");
        return "request";
    }

    @GetMapping("/saldo")
    public String getSaldo(Model model, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("testo", "Inserire un numero");
        model.addAttribute("ruolo", "user");
        return "redirect:/user/request";
    }
    
    @PostMapping("/saldo")
    public String getSaldo(@RequestParam("numero") String numero, Model model, RedirectAttributes redirectAttributes){
        numero=numero.replaceAll(" ", "");
        Carta carta = this.repository.findByNumero(numero);

        if (carta == null){
            redirectAttributes.addFlashAttribute("testo", "Numero carta non trovato");
            return "redirect:/user/request";
        }

        String username = this.getUser().getUsername();

        if(!username.equals(carta.getOwner().getUsername())){
            redirectAttributes.addFlashAttribute("testo", "Non risulti essere intestatario di questa carta");
            return "redirect:/user/request";
        }

        model.addAttribute("ruolo", "user");
        model.addAttribute("saldo", carta.getSaldo());
        model.addAttribute("numero", numero);
        model.addAttribute("status", carta.getBlock());
        return "saldo";
    }

    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails)authentication.getPrincipal();
        return this.uRepository.findByUsername(user.getUsername());
    }
}