package ru.northcapybara.HomelessDude.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.northcapybara.HomelessDude.models.CharacterMeshConfig;

@Repository
public interface CharacterMeshConfigRepository extends JpaRepository<CharacterMeshConfig, Integer> {
}
