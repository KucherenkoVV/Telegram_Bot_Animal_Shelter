package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatService {

    @Autowired
    private CatRepository catRepository;

    public void addCat(Cat cat) {
        catRepository.save(cat);
    }

}

