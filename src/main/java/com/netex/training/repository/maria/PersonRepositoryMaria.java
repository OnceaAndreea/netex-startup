package com.netex.training.repository.maria;

import com.netex.training.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepositoryMaria  extends JpaRepository<Person,Integer> {
}
