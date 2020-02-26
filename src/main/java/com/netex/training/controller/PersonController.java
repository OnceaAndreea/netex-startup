package com.netex.training.controller;

import com.netex.training.model.Person;
import com.netex.training.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired()
    PersonService personService;

    @PostMapping("/saveInBothDatabases")
    public void create(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,@RequestParam("age") int age){
       personService.createInMaria(firstName,lastName,age);
       personService.createInPostgres(firstName,lastName,age);
    }

    @PostMapping("/saveInMariaDB")
    public void createInMariaDB(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,@RequestParam("age") int age){
        personService.createInMaria(firstName,lastName,age);
    }

    @PostMapping("/saveInPostgres")
    public void createInPostgres(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,@RequestParam("age") int age){
        personService.createInPostgres(firstName,lastName,age);
    }

    @GetMapping("/showDataFromMariaDB")
    public List<Person> getDataFromMariaDB(){
        return personService.getAllPersonsStoredInMariaDB();
    }

    @GetMapping("/showDataFromPostgres")
    public List<Person> getDataFromPostgresDB(){
        return personService.getAllPersonsStoredInPostgres();
    }

    @GetMapping("/findPersonByIdInMAriaDB")
    public Optional<Person> findPersonByIdInMAriaDB(@RequestParam int id){
        return personService.findPersonFromMariaDbById(id);

    }
}

