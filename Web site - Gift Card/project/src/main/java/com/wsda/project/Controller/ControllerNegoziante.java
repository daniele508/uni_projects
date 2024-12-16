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

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/negoziante")
public class ControllerNegoziante {

    @Autowired
    private CardRepository repository;

    @Autowired
    private MovimentoRepository mRepository;

    @Autowired
    private UserRepository uRepository;
    
    @GetMapping("/home")
    public String getHome(Model model) {
        model.addAttribute("nome", this.getUser().getNome());
        model.addAttribute("cognome", this.getUser().getCognome());
        return "home_negoziante";
    }

    @GetMapping("/movimenti")
    public String getMovimenti(Model model){
        User user=this.getUser();
        List<Movimento> movimenti = this.mRepository.findByUser(user);
        model.addAttribute("ruolo", "negoziante");
        if(movimenti.isEmpty()){
            model.addAttribute("testo", "Non hai effettuato nessun movimento");
            return "movimenti";
        }
        model.addAttribute("movimenti", movimenti);
        return "movimenti";
    }

    @GetMapping("/request-saldo")
    public String getRequestSaldo(Model model) {
        model.addAttribute("ruolo", "negoziante");
        return "request";
    }

    @GetMapping("/saldo")
    public String getSaldo(Model model,RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("testo", "Inserire un numero");
        model.addAttribute("ruolo", "negoziante");
        return "redirect:/negoziante/request-saldo";
    }
    
    @PostMapping("/saldo")
    public String getSaldo(@RequestParam("numero") String numero, Model model, RedirectAttributes redirectAttributes){
        numero=numero.replaceAll(" ", "");
        Carta carta = this.repository.findByNumero(numero);

        if (carta == null){
            redirectAttributes.addFlashAttribute("testo", "Numero carta non trovato");
            return "redirect:/negoziante/request-saldo";
        }
        model.addAttribute("ruolo", "negoziante");
        model.addAttribute("saldo", carta.getSaldo());
        model.addAttribute("numero", numero);
        model.addAttribute("status", carta.getBlock());
        return "saldo";
    }

    @GetMapping("/request-importo")
    public String getRequestImport() {
        return "request_negoziante";
    }

    @GetMapping("/credit")
    public String updateSaldo(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("testo","Inserire un numero");
        return "redirect:/negoziante/request-importo";
    }
    
    @PostMapping("/credit")
    public String updateSaldo(@RequestParam("numero") String numero, @RequestParam("quantita") float quantita, @RequestParam("op") String operazione, Model model, RedirectAttributes redirectAttributes) {
        numero=numero.replaceAll(" ", "");
        Carta carta = this.repository.findByNumero(numero);

        if (carta == null){
            redirectAttributes.addFlashAttribute("testo", "Numero carta non trovato");
            return "redirect:/negoziante/request-importo";
        }

        if(carta.getBlock()){
            redirectAttributes.addFlashAttribute("testo", "Impossibile eseguire l'operazione: carta bloccata");
            return "redirect:/negoziante/request-importo";
        }

        float saldoCorrente = carta.getSaldo();

        if(operazione.equals("carica")){
            this.repository.modifySaldo(numero, saldoCorrente+quantita);
            model.addAttribute("numero", numero);
            Movimento movimento = new Movimento(quantita, LocalDate.now(), numero, carta, carta.getOwner());
            this.mRepository.save(movimento);
            return "credit";
        }
        if(saldoCorrente>=quantita){
            this.repository.modifySaldo(numero, saldoCorrente-quantita);
            model.addAttribute("numero", numero);
            Movimento movimento = new Movimento(0-quantita, LocalDate.now(), numero, carta, carta.getOwner());
            this.mRepository.save(movimento);
            return "credit";
        }
        else{
            redirectAttributes.addFlashAttribute("testo", "Credito insufficiente");
            return "redirect:/negoziante/request-importo";
        }

    }

    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails)authentication.getPrincipal();
        return this.uRepository.findByUsername(user.getUsername());
    }
}
