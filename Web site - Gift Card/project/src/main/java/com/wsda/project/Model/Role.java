package com.wsda.project.Model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Role(){}

    public Role(Integer id, String name, List<User> users){
        this.setId(id);
        this.setName(name);
        this.setUsers(users);
    }

    private void setId(Integer id){
        this.id=id;
    }

    private void setName(String name){
        this.name=name;
    }

    private void setUsers(List<User> users){
        this.users=users;
    }

    public Integer getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public List<User> getUsers(){
        return this.users;
    }

}
