package com.example.demo;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll(Specification<User> specification);
    List<User> findAll();

    User getUserByFirstname(String name);
}
