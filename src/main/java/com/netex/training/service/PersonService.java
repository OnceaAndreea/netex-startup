package com.netex.training.service;

import com.netex.training.model.Person;
import com.netex.training.repository.maria.PersonRepositoryMaria;
import com.netex.training.repository.postgres.PersonRepositoryPostgres;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@EnableTransactionManagement
public class PersonService {

    @Autowired
    private PersonRepositoryPostgres personRepositoryPostgres;

    @Autowired
    private PersonRepositoryMaria personRepositoryMaria;

    @Transactional("postgresDatabaseTransactionManager")
    public void createInPostgres(String firstName,String lastName,int age) {
        Person person=new Person(firstName,lastName,age);
        personRepositoryPostgres.save(person);
    }

    @Transactional("mariaDatabaseTransactionManager")
    public void createInMaria(String firstName,String lastName,int age){
        Person person=new Person(firstName,lastName,age);
        personRepositoryMaria.save(person);
    }

    @Transactional("mariaDatabaseTransactionManager")
    public void saveInMaria(Person person){
        personRepositoryMaria.save(person);
    }

    @Transactional("postgresDatabaseTransactionManager")
    public void saveInPostgres(Person person){
        personRepositoryPostgres.save(person);
    }

    @Transactional("mariaDatabaseTransactionManager")
    public Optional<Person> findPersonFromMariaDbById(Integer id){
       return personRepositoryMaria.findById(id);
    }


    @Transactional("postgresDatabaseTransactionManager")
    public Optional<Person> findPersonFromPostgresById(Integer id){
        return personRepositoryPostgres.findById(id);
    }

    @Transactional("mariaDatabaseTransactionManager")
    public void deletePersonFromMariaDb(Person person){
        personRepositoryMaria.delete(person);
    }


    @Transactional("postgresDatabaseTransactionManager")
    public void deletePersonFromPostgres(Person person) {
        personRepositoryPostgres.delete(person);
    }

    @Transactional("mariaDatabaseTransactionManager")
    public List<Person> getAllPersonsStoredInMariaDB(){
        return personRepositoryMaria.findAll();
    }

    @Transactional("postgresDatabaseTransactionManager")
    public List<Person> getAllPersonsStoredInPostgres(){
        return personRepositoryPostgres.findAll();
    }

}
