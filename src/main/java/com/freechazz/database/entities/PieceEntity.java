package com.freechazz.database.entities;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "pieces")
public class PieceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_uuid")
    private UserEntity user;

    @OneToMany(mappedBy = "piece", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PieceFormationEntity> pieceFormations;

    // Constructors, getters, and setters

    // toString method (optional)
}
