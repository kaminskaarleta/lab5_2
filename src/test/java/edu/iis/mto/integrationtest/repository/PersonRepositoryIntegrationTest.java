package edu.iis.mto.integrationtest.repository;

import static edu.iis.mto.integrationtest.repository.PersonBuilder.person;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import edu.iis.mto.integrationtest.model.Person;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)

public class PersonRepositoryIntegrationTest extends IntegrationTest {

	@Autowired
	private PersonRepository personRepository;

	@Test
	public void testCanAccessDbAndGetTestData() {
		List<Person> foundTestPersons = personRepository.findAll();
		assertEquals(2, foundTestPersons.size());
	}

	@Test
	public void testSaveNewPersonAndCheckIsPersisted() {
		long count = personRepository.count();
		personRepository.save(a(person().withId(count + 1)
				.withFirstName("Roberto").withLastName("Mancini")));
		assertEquals(count + 1, personRepository.count());
		assertEquals("Mancini", personRepository.findOne(count + 1)
				.getLastName());
	}
	
	@Test
	public void testDeletePersonWithId1_countShoudBe1_idShouldBeNull() {
		long count = personRepository.count();
		personRepository.delete(a(person().withId(1)
				.withFirstName("Piotr").withLastName("Morowiecki")));
		assertEquals(null, personRepository.findOne(1L));
		assertEquals(count - 1, personRepository.count());
	}
	
	@Test
	public void testUpdatePersonWithId1_CheckChange() {
		personRepository.saveAndFlush(a(person().withId(1)
				.withFirstName("Arleta").withLastName("Kaminska")));
		assertEquals("Arleta", personRepository.findOne(1L).getFirstName());
		assertEquals("Kaminska", personRepository.findOne(1L).getLastName());
	}

	private Person a(PersonBuilder builder) {
		return builder.build();
	}

}
