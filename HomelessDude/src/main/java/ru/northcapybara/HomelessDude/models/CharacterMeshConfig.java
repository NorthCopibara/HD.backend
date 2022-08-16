package ru.northcapybara.HomelessDude.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "CharacterMeshConfigs")
public class CharacterMeshConfig {
    @Id
    @Column(name = "character_mesh_config_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int configId;

    @Column(name = "custom_element")
    @NotEmpty(message = "Custom element should not be empty")
    String customElement;

    @Column(name = "active_element")
    int activeElement;

    @ManyToOne
    @JoinColumn(name="character_id")
    Character character;
}
