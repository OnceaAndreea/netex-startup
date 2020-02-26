package com.netex.training.controller;

import com.netex.training.model.Person;
import com.netex.training.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class SimpleController {

    @Autowired
    PersonService personService;

    @GetMapping("/signup")
    public String showSignUpForm(Person person) {
        return "add-person";
    }

    @RequestMapping(value="/addperson", method=RequestMethod.POST, params="action=SaveInMariaDB")
    public String addPerson(@Valid Person person, BindingResult result, Model model, String action) {

        if (result.hasErrors()) {
            return "add-person";
        }
        personService.saveInMaria(person);
        model.addAttribute("persons", personService.getAllPersonsStoredInMariaDB());
        return "index";
    }
    @RequestMapping(value="/addperson", method=RequestMethod.POST, params="action=Show Postgres DB")
    public String showInPostgres(@Valid Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-person";
        }
        model.addAttribute("persons", personService.getAllPersonsStoredInPostgres());
        return "index-for-postgres";
    }
    @RequestMapping(value="/addperson", method=RequestMethod.POST, params="action=Show Maria DB")
    public String showInMaria(@Valid Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-person";
        }
        model.addAttribute("persons", personService.getAllPersonsStoredInMariaDB());
        return "index";
    }

    @RequestMapping(value="/addperson", method=RequestMethod.POST, params="action=Show Both")
    public String showBoth(@Valid Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-person";
        }
        model.addAttribute("personsInPostgres", personService.getAllPersonsStoredInPostgres());
        model.addAttribute("personsInMaria", personService.getAllPersonsStoredInMariaDB());
        return "index-for-both";
    }


    @RequestMapping(value="/addperson", method=RequestMethod.POST, params="action=SaveInPostgres")
    public String addPersonInPostgres(@Valid Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-person";
        }
        personService.saveInPostgres(person);
        model.addAttribute("persons", personService.getAllPersonsStoredInPostgres());
        return "index-for-postgres";
    }

    @RequestMapping(value="/addperson", method=RequestMethod.POST, params="action=SaveInBoth")
    public String addPersonInBothDatabases(@Valid Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-person";
        }
        personService.saveInPostgres(person);
        personService.saveInMaria(person);
        model.addAttribute("personsInPostgres", personService.getAllPersonsStoredInPostgres());
        model.addAttribute("personsInMaria", personService.getAllPersonsStoredInMariaDB());
        return "index-for-both";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Person person = personService.findPersonFromMariaDbById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id:" + id));

        model.addAttribute("person", person);
        return "update-person";
    }

    @GetMapping("/editPostgres/{id}")
    public String showUpdateForm1(@PathVariable("id") int id, Model model) {
        Person person = personService.findPersonFromPostgresById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id:" + id));

        model.addAttribute("person", person);
        return "update-person-postgres";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") int id, @Valid Person person,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            person.setId(id);
            return "update-person";
        }

        personService.saveInMaria(person);
        model.addAttribute("persons", personService.getAllPersonsStoredInMariaDB());
        return "index";
    }

    @PostMapping("/updatePostgres/{id}")
    public String updateUser1(@PathVariable("id") int id, @Valid Person person,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            person.setId(id);
            return "update-person-postgres";
        }

        personService.saveInPostgres(person);
        model.addAttribute("persons", personService.getAllPersonsStoredInPostgres());
        return "index-for-postgres";
    }

    @GetMapping("/delete/{id}")
    public String deletePerson(@PathVariable("id") int id, Model model) {
        Person person = personService.findPersonFromMariaDbById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        personService.deletePersonFromMariaDb(person);
        model.addAttribute("persons", personService.getAllPersonsStoredInMariaDB());
        return "index";
    }

    @GetMapping("/deletePostgres/{id}")
    public String deletePerson1(@PathVariable("id") int id, Model model) {
        Person person = personService.findPersonFromPostgresById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        personService.deletePersonFromPostgres(person);
        model.addAttribute("persons", personService.getAllPersonsStoredInPostgres());
        return "index-for-postgres";
    }
}
