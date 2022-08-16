package ru.northcapybara.HomelessDude.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Characters")
@Data
public class Character {

    @Id
    @Column(name = "character_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int characterId;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "is_selected")
    private boolean isSelected;

    @OneToMany(mappedBy = "character")
    @JsonManagedReference
    private List<CharacterMeshConfig> characterMeshConfigs;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @JsonBackReference
    private Person owner;
}
