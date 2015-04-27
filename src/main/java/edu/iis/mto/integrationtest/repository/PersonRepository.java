package edu.iis.mto.integrationtest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.iis.mto.integrationtest.model.Person;



public interface PersonRepository extends JpaRepository<Person, Long>{
	public List<Person> findByFirstNameLike(String firstName);
}
