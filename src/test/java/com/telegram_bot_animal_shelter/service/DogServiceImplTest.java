package com.telegram_bot_animal_shelter.service;

import com.telegram_bot_animal_shelter.model.Dog;
import com.telegram_bot_animal_shelter.repository.DogRepository;
import com.telegram_bot_animal_shelter.service.impl.DogServiceImpl;
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
 * Class DogServiceImplTest
 * @author Zhitar Vlad
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
public class DogServiceImplTest {
    private static final String NAME = "Jack";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String BREED = "Хаски";
    private static final int AGE = Integer.parseInt("8");

    private static final List<Dog> dogs = new ArrayList<>(Arrays.asList(
            new Dog(NAME, BREED, AGE, DESCRIPTION),
            new Dog(NAME, BREED, AGE, DESCRIPTION),
            new Dog(NAME, BREED, AGE, DESCRIPTION)));

    private static final Dog dog = new Dog("Джексон", "Британский", 10, "Description");
    @Mock
    private DogRepository dogRepositoryMock;
    @InjectMocks
    private DogServiceImpl dogService;

    /**
     * Testing method for getById dogs
     */
    @Test
    public void getByIdDog() {
        Mockito.when(dogRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(dog));
        Dog dog1 = dogService.getByIdDog(1L);
        Assertions.assertThat(dog1.getName()).isEqualTo(dog.getName());
        Assertions.assertThat(dog1.getBreed()).isEqualTo(dog.getBreed());
        Assertions.assertThat(dog1.getDescription()).isEqualTo(dog.getDescription());
        Assertions.assertThat(dog1.getAge()).isEqualTo(dog.getAge());
    }

    /**
     * Testing method for adding dogs
     */
    @Test
    public void addDog() {
        Mockito.when(dogRepositoryMock.save(any(Dog.class))).thenReturn(dog);
        Dog dog1 = dogService.addDog(dog);
        Assertions.assertThat(dog1.getName()).isEqualTo(dog.getName());
        Assertions.assertThat(dog1.getBreed()).isEqualTo(dog.getBreed());
        Assertions.assertThat(dog1.getDescription()).isEqualTo(dog.getDescription());
        Assertions.assertThat(dog1.getAge()).isEqualTo(dog.getAge());
    }

    /**
     * Testing method for updates dogs
     */
    @Test
    public void updateDog() {
        Dog dog1 = new Dog();
        dog1.setName("Мартышка");
        dog1.setDescription("Description");
        dog1.setBreed("Африкансий");
        dog1.setAge(15);
        dog1.setId(1L);
        Mockito.when(dogRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(dog1));
        Mockito.when(dogRepositoryMock.save(any(Dog.class))).thenReturn(dog1);
        Dog dog2 = dogService.updateDog(dog1);
        Assertions.assertThat(dog2.getName()).isEqualTo(dog1.getName());
        Assertions.assertThat(dog2.getBreed()).isEqualTo(dog1.getBreed());
        Assertions.assertThat(dog2.getDescription()).isEqualTo(dog1.getDescription());
        Assertions.assertThat(dog2.getAge()).isEqualTo(dog1.getAge());
    }

    /**
     * Testing method for getting all dogs
     */
    @Test
    public void getAllDogs() {
        Mockito.when(dogRepositoryMock.findAll()).thenReturn(dogs);
        Collection<Dog> dog = dogService.getAllDog();
        Assertions.assertThat(dog.size()).isEqualTo(dogs.size());
        Assertions.assertThat(dog).isEqualTo(dogs);
    }
}
