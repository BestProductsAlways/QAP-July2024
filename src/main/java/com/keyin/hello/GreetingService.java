package com.keyin.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GreetingService {

    @Autowired
    private GreetingRepository greetingRepository;

    @Autowired
    private LanguageRepository languageRepository;

    public Greeting getGreeting(long index) {
        Optional<Greeting> result = greetingRepository.findById(index);
        return result.orElse(null);
    }

    public Greeting createGreeting(Greeting newGreeting) {
        if (newGreeting.getLanguages() == null) {
            Language english = languageRepository.findByName("English");

            if (english == null) {
                english = new Language();
                english.setName("English");
                languageRepository.save(english);
            }

            ArrayList<Language> languageArrayList = new ArrayList<>();
            languageArrayList.add(english);

            newGreeting.setLanguages(languageArrayList);
        } else {
            for (Language language : newGreeting.getLanguages()) {
                Language langInDB = languageRepository.findByName(language.getName());

                if (langInDB == null) {
                    language = languageRepository.save(language);
                }
            }
        }

        return greetingRepository.save(newGreeting);
    }

    public List<Greeting> getAllGreetings() {
        return (List<Greeting>) greetingRepository.findAll();
    }

    public Greeting updateGreeting(Integer index, Greeting updatedGreeting) {
        Greeting greetingToUpdate = getGreeting(index);

        if (greetingToUpdate != null) {
            greetingToUpdate.setName(updatedGreeting.getName());
            greetingToUpdate.setGreeting(updatedGreeting.getGreeting());

            List<Language> updatedLanguages = updatedGreeting.getLanguages();
            if (updatedLanguages != null) {
                for (int i = 0; i < updatedLanguages.size(); i++) {
                    Language language = updatedLanguages.get(i);
                    Language langInDB = languageRepository.findByName(language.getName());
                    if (langInDB == null) {
                        langInDB = languageRepository.save(language);
                    }
                    updatedLanguages.set(i, langInDB);
                }
            }
            greetingToUpdate.setLanguages(updatedLanguages);

            return greetingRepository.save(greetingToUpdate);
        }

        return null;
    }

    public void deleteGreeting(long index) {
        greetingRepository.delete(getGreeting(index));
    }

    public List<Greeting> findGreetingsByNameAndGreeting(String name, String greetingName) {
        return greetingRepository.findByNameAndGreeting(name, greetingName);
    }
}

