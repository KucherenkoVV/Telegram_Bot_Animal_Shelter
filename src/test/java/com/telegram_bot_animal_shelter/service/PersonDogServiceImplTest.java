package com.telegram_bot_animal_shelter.service;

import com.telegram_bot_animal_shelter.model.PersonDog;
import com.telegram_bot_animal_shelter.model.Status;
import com.telegram_bot_animal_shelter.repository.PersonDogRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;

/**
 * Class PersonCatServiceImplTest
 * @author
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
public class PersonDogServiceImplTest {
    private static final String PERSON_NAME = "Дима";
    private static final int YEAR_OF_BIRTH = 2001;
    private static final String PHONE = "+77777777777";
    private static final String ADDRESS = "Швеция";
    private static final Long CHAT_ID = 1L;
    private static final Status STATUS = Status.SEARCH;

    private static final List<PersonDog> personDogs = new ArrayList<>(Arrays.asList(
            new PersonDog(PERSON_NAME, YEAR_OF_BIRTH, PHONE, ADDRESS, CHAT_ID, STATUS),
            new PersonDog(PERSON_NAME, YEAR_OF_BIRTH, PHONE, ADDRESS, CHAT_ID, STATUS),
            new PersonDog(PERSON_NAME, YEAR_OF_BIRTH, PHONE, ADDRESS, CHAT_ID, STATUS)));
    @Mock
    private PersonDogRepository personDogRepositoryMock;

    @InjectMocks
    private PersonDogService personDogService;

    private final PersonDog personDog = new PersonDog(PERSON_NAME, YEAR_OF_BIRTH, PHONE, ADDRESS, CHAT_ID, STATUS);

    /**
     * Testing method for getById personDog
     */
    @Test
    public void getByIdPersonDog() {
        Mockito.when(personDogRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(personDog));
        PersonDog dog = personDogService.getByIdPersonDog(1L);
        Assertions.assertThat(dog.getId()).isEqualTo(personDog.getId());
        Assertions.assertThat(dog.getName()).isEqualTo(personDog.getName());
        Assertions.assertThat(dog.getYearOfBirth()).isEqualTo(personDog.getYearOfBirth());
        Assertions.assertThat(dog.getPhone()).isEqualTo(personDog.getPhone());
        Assertions.assertThat(dog.getAddress()).isEqualTo(personDog.getAddress());
        Assertions.assertThat(dog.getChatId()).isEqualTo(personDog.getChatId());
    }

    /**
     * Testing method for adding personDog
     */
    @Test
    public void addPersonDog() {
        Mockito.when(personDogRepositoryMock.save(any(PersonDog.class))).thenReturn(personDog);
        PersonDog personDog1 = personDogService.addPersonDog(personDog);
        Assertions.assertThat(personDog1.getId()).isEqualTo(personDog.getId());
        Assertions.assertThat(personDog1.getName()).isEqualTo(personDog.getName());
        Assertions.assertThat(personDog1.getYearOfBirth()).isEqualTo(personDog.getYearOfBirth());
        Assertions.assertThat(personDog1.getPhone()).isEqualTo(personDog.getPhone());
        Assertions.assertThat(personDog1.getAddress()).isEqualTo(personDog.getAddress());
        Assertions.assertThat(personDog1.getChatId()).isEqualTo(personDog.getChatId());
    }

    /**
     * Testing method for updates personDog
     */
    @Test
    public void updatePersonDog() {
        PersonDog personDog1 = new PersonDog();
        personDog1.setName(PERSON_NAME);
        personDog1.setYearOfBirth(YEAR_OF_BIRTH);
        personDog1.setPhone(PHONE);
        personDog1.setAddress(ADDRESS);
        personDog1.setChatId(CHAT_ID);
        personDog1.setStatus(STATUS);
        Mockito.when(personDogRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(personDog1));
        Mockito.when(personDogRepositoryMock.save(any(PersonDog.class))).thenReturn(personDog1);
        PersonDog personDog2 = personDogService.updatePersonDog(personDog1);
        Assertions.assertThat(personDog2.getName()).isEqualTo(personDog1.getName());
        Assertions.assertThat(personDog2.getYearOfBirth()).isEqualTo(personDog1.getYearOfBirth());
        Assertions.assertThat(personDog2.getPhone()).isEqualTo(personDog1.getPhone());
        Assertions.assertThat(personDog2.getAddress()).isEqualTo(personDog1.getAddress());
        Assertions.assertThat(personDog2.getChatId()).isEqualTo(personDog1.getChatId());
        Assertions.assertThat(personDog2.getStatus()).isEqualTo(personDog1.getStatus());
    }

    /**
     * Testing method for getting all personDog
     */
    @Test
    public void getAllPersonDog() {
        Mockito.when(personDogRepositoryMock.findAll()).thenReturn(personDogs);
        Collection<PersonDog> personDog1 = personDogService.getAllPersonDog();
        Assertions.assertThat(personDog1.size()).isEqualTo(personDogs.size());
        Assertions.assertThat(personDog1).isEqualTo(personDogs);
    }
}
