package com.keyin.hello;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GreetingRepository extends CrudRepository<Greeting, Long> {
    List<Greeting> findByNameAndGreeting(String name, String greetingName);
}

