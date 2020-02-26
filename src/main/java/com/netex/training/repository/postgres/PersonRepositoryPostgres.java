package com.netex.training.repository.postgres;

import com.netex.training.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepositoryPostgres extends JpaRepository<Person,Integer> {


}
