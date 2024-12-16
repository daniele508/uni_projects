package com.wsda.project.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wsda.project.Model.Negoziante;
import com.wsda.project.Model.Role;
import com.wsda.project.Model.User;
import com.wsda.project.Repository.RoleRepository;
import com.wsda.project.Repository.UserRepository;

@Controller
public class ControllerRegister {
    
    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("nome") String nome, @RequestParam("cognome") String cognome, @RequestParam("negozio") String negozio, RedirectAttributes redirectAttributes){
        
        User u=this.repository.findByUsername(username);

        if(u!=null){
            redirectAttributes.addFlashAttribute("testo", "Username già esistente. Scegline un altro");
            return "redirect:/register";
        }

        String coded=this.passwordEncoder().encode(password);

        Role role;
        User user;

        if(!negozio.isBlank()){
            role = this.roleRepository.findByName("NEGOZIANTE");
            user=new Negoziante(username, coded, nome, cognome, new ArrayList<>(), role, negozio);
        }
        else{
            role= this.roleRepository.findByName("USER");
            user=new User(username, coded, nome, cognome, new ArrayList<>(), role);
        }
        this.repository.save(user);
        redirectAttributes.addFlashAttribute("testo", "Utente registrato!");
        return "redirect:/login";
    }   
}