package ru.northcapybara.HomelessDude.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.northcapybara.HomelessDude.models.Character;
import ru.northcapybara.HomelessDude.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Integer> {
    List<Optional<Character>> findCharactersByOwner(Person person);
}
