package com.birthdayemail.userdetails.service;

import com.birthdayemail.userdetails.entity.PersonEntity;
import com.birthdayemail.userdetails.mapper.PersonMapper;
import com.birthdayemail.userdetails.model.Person;
import com.birthdayemail.userdetails.repository.PersonRepository;
import com.birthdayemail.userdetails.response.PersonResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class PersonService {
	@Autowired
	private final PersonRepository personRepository;

	@Autowired
	private final PersonMapper personMapper;

	public PersonService(PersonRepository personRepository, PersonMapper personMapper) {
		this.personRepository = personRepository;
		this.personMapper = personMapper;
	}

	public PersonResponse createPerson(Person person) {
		PersonEntity personEntity = new PersonEntity();
		personEntity = personMapper.modelToEntity(person);
		personRepository.save(personEntity);
		PersonResponse personResponse = new PersonResponse();
		personResponse.setId(personEntity.getId());
		log.info("");
		return personResponse;

	}

	public Person findByDate(Date birthdate) {
		Person person = new Person();
		Optional<PersonEntity> optionalPersonEntity = personRepository.findByDate(birthdate);
		if (optionalPersonEntity.isPresent()) {
			person = personMapper.entityToModel(optionalPersonEntity.get());
			log.info("");
		} else {
			log.info("");
		}
		return person;

	}

	public List<?> findByDayMonth(MonthDay monthDay) {
		List<?> entityList = new ArrayList<>();
		List<?> personEntityList = personRepository.findAllByDayMonth(monthDay);
		Date date = new Date();
		log.info("" + date);
		return personEntityList;

	}
}
