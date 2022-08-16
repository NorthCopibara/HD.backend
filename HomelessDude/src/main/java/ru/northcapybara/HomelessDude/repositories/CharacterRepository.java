package ru.northcapybara.HomelessDude.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.northcapybara.HomelessDude.models.Character;
import ru.northcapybara.HomelessDude.models.Person;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Integer> {
    List<Character> findCharactersByOwner(Person person);
}
