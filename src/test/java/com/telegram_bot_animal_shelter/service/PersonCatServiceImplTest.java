package com.telegram_bot_animal_shelter.service;

import com.telegram_bot_animal_shelter.exceptions.PersonCatNotFoundException;
import com.telegram_bot_animal_shelter.model.PersonCat;
import com.telegram_bot_animal_shelter.model.Status;
import com.telegram_bot_animal_shelter.repository.PersonCatRepository;
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
 * @author ZhitarVlad
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
public class PersonCatServiceImplTest {
    private static final String PERSON_NAME = "Артём";
    private static final int YEAR_OF_BIRTH = 2000;
    private static final String PHONE = "+77777777777";
    private static final String ADDRESS = "Британия";
    private static final Long CHAT_ID = 5L;
    private static final Status STATUS = Status.APPROVED;

    private static final List<PersonCat> personCats = new ArrayList<>(Arrays.asList(
            new PersonCat(PERSON_NAME, YEAR_OF_BIRTH, PHONE, ADDRESS, CHAT_ID, STATUS),
            new PersonCat(PERSON_NAME, YEAR_OF_BIRTH, PHONE, ADDRESS, CHAT_ID, STATUS),
            new PersonCat(PERSON_NAME, YEAR_OF_BIRTH, PHONE, ADDRESS, CHAT_ID, STATUS)));
    @Mock
    private PersonCatRepository personCatRepositoryMock;

    @InjectMocks
    private PersonCatService personCatService;

    private final PersonCat personCat = new PersonCat(PERSON_NAME, YEAR_OF_BIRTH, PHONE, ADDRESS, CHAT_ID, STATUS);

    /**
     * Testing method for getById personCat
     */
    @Test
    public void getByIdPersonCat() {
        Mockito.when(personCatRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(personCat));
        PersonCat cat = personCatService.getByIdPersonCat(1L);
        Assertions.assertThat(cat.getId()).isEqualTo(personCat.getId());
        Assertions.assertThat(cat.getName()).isEqualTo(personCat.getName());
        Assertions.assertThat(cat.getYearOfBirth()).isEqualTo(personCat.getYearOfBirth());
        Assertions.assertThat(cat.getPhone()).isEqualTo(personCat.getPhone());
        Assertions.assertThat(cat.getAddress()).isEqualTo(personCat.getAddress());
        Assertions.assertThat(cat.getChatId()).isEqualTo(personCat.getChatId());
    }

    @Test
    public void getByIdExceptionTest() {
        Mockito.when(personCatRepositoryMock.findById(any(Long.class))).thenThrow(PersonCatNotFoundException.class);

        org.junit.jupiter.api.Assertions.assertThrows(PersonCatNotFoundException.class, () -> personCatService.getByIdPersonCat(1L));
    }

    /**
     * Testing method for adding personCat
     */
    @Test
    public void addPersonCat() {
        Mockito.when(personCatRepositoryMock.save(any(PersonCat.class))).thenReturn(personCat);
        PersonCat cat = personCatService.addPersonCat(personCat);
        Assertions.assertThat(cat.getId()).isEqualTo(personCat.getId());
        Assertions.assertThat(cat.getName()).isEqualTo(personCat.getName());
        Assertions.assertThat(cat.getYearOfBirth()).isEqualTo(personCat.getYearOfBirth());
        Assertions.assertThat(cat.getPhone()).isEqualTo(personCat.getPhone());
        Assertions.assertThat(cat.getAddress()).isEqualTo(personCat.getAddress());
        Assertions.assertThat(cat.getChatId()).isEqualTo(personCat.getChatId());
    }

    /**
     * Testing method for updates personCat
     */
    @Test
    public void updatePersonCat() {
        PersonCat personCat1 = new PersonCat();
        personCat1.setName(PERSON_NAME);
        personCat1.setYearOfBirth(YEAR_OF_BIRTH);
        personCat1.setPhone(PHONE);
        personCat1.setAddress(ADDRESS);
        personCat1.setChatId(CHAT_ID);
        personCat1.setStatus(STATUS);
        personCat1.setId(1L);
        Mockito.when(personCatRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(personCat1));
        Mockito.when(personCatRepositoryMock.save(any(PersonCat.class))).thenReturn(personCat1);
        PersonCat personCat2 = personCatService.updatePersonCat(personCat1);
        Assertions.assertThat(personCat2.getName()).isEqualTo(personCat1.getName());
        Assertions.assertThat(personCat2.getYearOfBirth()).isEqualTo(personCat1.getYearOfBirth());
        Assertions.assertThat(personCat2.getPhone()).isEqualTo(personCat1.getPhone());
        Assertions.assertThat(personCat2.getAddress()).isEqualTo(personCat1.getAddress());
        Assertions.assertThat(personCat2.getChatId()).isEqualTo(personCat1.getChatId());
        Assertions.assertThat(personCat2.getStatus()).isEqualTo(personCat1.getStatus());
    }

    @Test
    public void updateExceptionTest() {
        PersonCat personCat = new PersonCat();

        org.junit.jupiter.api.Assertions.assertThrows(PersonCatNotFoundException.class, () -> personCatService.updatePersonCat(personCat));
    }

    /**
     * Testing method for getting all personCat
     */
    @Test
    public void getAllPersonCat() {
        Mockito.when(personCatRepositoryMock.findAll()).thenReturn(personCats);
        Collection<PersonCat> cat = personCatService.getAllPersonCat();
        Assertions.assertThat(cat.size()).isEqualTo(personCats.size());
        Assertions.assertThat(cat).isEqualTo(personCats);
    }

    @Test
    public void deleteByIdPersonCat() {
        Assertions.assertThat(personCats.remove(0).equals(personCats.get(0)));
    }
}
