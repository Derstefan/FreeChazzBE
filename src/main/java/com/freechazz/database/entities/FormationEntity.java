package com.freechazz.database.entities;

import com.freechazz.game.core.ESize;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "formations")
public class FormationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // other fields

    @ManyToOne
    @JoinColumn(name = "user_uuid")
    private UserEntity user;

    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PieceFormationEntity> piecesFormations;

    @Column(name = "size")
    @Enumerated(EnumType.STRING)
    private ESize size;

    @OneToMany(mappedBy = "formation1")
    private Set<MatchEntity> matchesWithFormation1;

    @OneToMany(mappedBy = "formation2")
    private Set<MatchEntity> matchesWithFormation2;

    // Constructors, getters, and setters

    // toString method (optional)
}
