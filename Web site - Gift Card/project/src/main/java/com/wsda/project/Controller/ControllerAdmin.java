package com.wsda.project.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/admin")
public class ControllerAdmin{

    private Random rand = new Random(System.currentTimeMillis());

    @Autowired
    private CardRepository cRepository;

    @Autowired
    private UserRepository uRepository;

    @Autowired
    private MovimentoRepository mRepository;

    private String genNum(){
        String str="";
        int numero=0;
        for (int i=0; i<4; i++){
            do{
                numero=(int)(this.rand.nextDouble()*10000);
            }while(numero/1000==0);
            str+=numero;
        }
        return str;
    }

    @GetMapping("/home")
    public String getHome(Model model){
        model.addAttribute("nome", this.getUser().getNome());
        model.addAttribute("cognome", this.getUser().getCognome());
        return "home_admin";
    }

    @GetMapping("/movimenti")
    public String getMovimenti(Model model) {
        User user=this.getUser();
        List<Movimento> movimenti = this.mRepository.findByUser(user);
        model.addAttribute("ruolo", "admin");
        if(movimenti.isEmpty()){
            model.addAttribute("testo", "Non hai effettuato nessun movimento");
            return "movimenti";
        }
        model.addAttribute("movimenti", movimenti);
        return "movimenti";
    }

    @GetMapping("/request-saldo")
    public String getRequestSaldo(Model model){
        model.addAttribute("ruolo", "admin");
        return "request";
    }

    @GetMapping("/saldo")
    public String getSaldo(Model model, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("testo", "Inserire un numero");
        model.addAttribute("ruolo", "admin");
        return "redirect:/admin/request-saldo";
    }
    
    @PostMapping("/saldo")
    public String getSaldo(@RequestParam("numero") String numero, Model model, RedirectAttributes redirectAttributes){
        numero=numero.replaceAll(" ", "");
        Carta carta = this.cRepository.findByNumero(numero);

        if (carta == null){
            redirectAttributes.addFlashAttribute("testo", "Numero carta non trovato");
            return "redirect:/admin/request-saldo";
        }

        model.addAttribute("ruolo", "admin");
        model.addAttribute("saldo", carta.getSaldo());
        model.addAttribute("numero", numero);
        model.addAttribute("status", carta.getBlock());
        return "saldo";
    }

    @GetMapping("/request-numero")
    public String getRequestNumero(){
        return "request_admin1";
    }

    @GetMapping("/activation")
    public String getStatus(RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("testo", "Inserire un numero");
        return "redirect:/admin/request-numero";
    }
    
    @PostMapping("/activation")
    public String updateStatus(@RequestParam("numero") String numero, Model model, RedirectAttributes redirectAttributes){
        numero=numero.replaceAll(" ", "");
        Carta carta = this.cRepository.findByNumero(numero);
        if (carta == null){
            redirectAttributes.addFlashAttribute("testo", "Numero carta non trovato");
            return "redirect:/admin/request-numero";
        }
        this.cRepository.modifyStatus(!carta.getBlock(), numero);
        model.addAttribute("numero", numero);
        model.addAttribute("stato", carta.getBlock());
        return "activation";
    }
    
    @GetMapping("/request-username")
    public String getRequestUsername(){
        return "request_admin2";
    }

    @GetMapping("/new-card")
    public String updateStatus(RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("testo", "Inserire un username");
        return "redirect:/admin/request-username";
    }

    @PostMapping("/new-card")
    public String createCard(@RequestParam String username, Model model, RedirectAttributes redirectAttributes) {
        User u = this.uRepository.findByUsername(username);
        if (u==null){
            redirectAttributes.addFlashAttribute("testo", "Username errato");
            return "redirect:/admin/request-username";
        }

        String numero=this.genNum();
        int cvv;
        do{
            cvv=(int)(this.rand.nextDouble()*1000);
        }while(cvv/100==0);
        LocalDate data= LocalDate.now().plusYears(3);

        Carta carta = new Carta(numero, data, 0.0f, cvv, false, u, new ArrayList<>());
        this.cRepository.save(carta);
        model.addAttribute("nome", u.getNome());
        model.addAttribute("cognome", u.getCognome());
        model.addAttribute("numero", numero);
        model.addAttribute("cvv", cvv);
        model.addAttribute("data", data);
        return "new_card";
    }
    
    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails)authentication.getPrincipal();
        return this.uRepository.findByUsername(user.getUsername());
    } 
}