package com.telegram_bot_animal_shelter.service;

import com.telegram_bot_animal_shelter.exceptions.CatNotFoundException;
import com.telegram_bot_animal_shelter.model.Cat;
import com.telegram_bot_animal_shelter.repository.CatRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Class CatServiceImplTest
 * @author Zhitar Vladislav
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
public class CatServiceImplTest {

    private static final String NAME = "Барсик";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String BREED = "Британский";
    private static final int AGE = Integer.parseInt("10");

    private static final List<Cat> cats = new ArrayList<>(Arrays.asList(
            new Cat(NAME, BREED, AGE, DESCRIPTION),
            new Cat(NAME, BREED, AGE, DESCRIPTION),
            new Cat(NAME, BREED, AGE, DESCRIPTION)));
    private static final Cat cat = new Cat("Барсик", "Британский", 10, "Description");
    @Mock
    private CatRepository catRepositoryMock;
    @InjectMocks
    private CatService catService;

    /**
     * Testing method for getById cats
     */
    @Test
    public void getByIdCat() {
        when(catRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(cat));
        Cat cat1 = catService.getByIdCat(1L);
        Assertions.assertThat(cat1.getName()).isEqualTo(cat.getName());
        Assertions.assertThat(cat1.getBreed()).isEqualTo(cat.getBreed());
        Assertions.assertThat(cat1.getDescription()).isEqualTo(cat.getDescription());
        Assertions.assertThat(cat1.getAge()).isEqualTo(cat.getAge());
    }

    /**
     * Error Throwing test
     */
    @Test
    public void getByIdExceptionTest() {
        when(catRepositoryMock.findById(any(Long.class))).thenThrow(CatNotFoundException.class);

        org.junit.jupiter.api.Assertions.assertThrows(CatNotFoundException.class, () -> catService.getByIdCat(1L));
    }

    /**
     * Testing method for adding cats
     */
    @Test
    public void addCat() {
        when(catRepositoryMock.save(any(Cat.class))).thenReturn(cat);
        Cat cat1 = catService.addCat(cat);
        Assertions.assertThat(cat1.getName()).isEqualTo(cat.getName());
        Assertions.assertThat(cat1.getBreed()).isEqualTo(cat.getBreed());
        Assertions.assertThat(cat1.getDescription()).isEqualTo(cat.getDescription());
        Assertions.assertThat(cat1.getAge()).isEqualTo(cat.getAge());
    }

    /**
     * Testing method for updates cats
     */
    @Test
    public void updateCat() {
        Cat cat1 = new Cat();
        cat1.setName("Мартышка");
        cat1.setDescription("Description");
        cat1.setBreed("Африкансий");
        cat1.setAge(15);
        cat1.setId(1L);
        when(catRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(cat1));
        when(catRepositoryMock.save(any(Cat.class))).thenReturn(cat1);
        Cat cat2 = catService.updateCat(cat1);
        Assertions.assertThat(cat2.getName()).isEqualTo(cat1.getName());
        Assertions.assertThat(cat2.getBreed()).isEqualTo(cat1.getBreed());
        Assertions.assertThat(cat2.getDescription()).isEqualTo(cat1.getDescription());
        Assertions.assertThat(cat2.getAge()).isEqualTo(cat1.getAge());
    }

    /**
     * Error Throwing test
     */
    @Test
    public void updateExceptionTest() {
        Cat expected = new Cat();

        org.junit.jupiter.api.Assertions.assertThrows(CatNotFoundException.class, () -> catService.updateCat(expected));
    }

    /**
     * Testing method for getting all cats
     */
    @Test
    public void getAllCats() {
        when(catRepositoryMock.findAll()).thenReturn(cats);
        Collection<Cat> cat = catService.getAllCat();
        Assertions.assertThat(cat.size()).isEqualTo(cats.size());
        Assertions.assertThat(cat).isEqualTo(cats);
    }

    /**
     * Testing method for removeById
     */
    @Test
    public void removeByIdCat() {
        Long catID = 1L;
        catService.removeByIdCat(catID);
        verify(catRepositoryMock, times(1)).deleteById(eq(catID));

    }
}
