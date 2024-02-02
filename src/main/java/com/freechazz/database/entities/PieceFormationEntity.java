package com.freechazz.database.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "piece_formations")
public class PieceFormationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "piece_id")
    private PieceEntity piece;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private FormationEntity formation;

    @Column(name = "x")
    private int x;

    @Column(name = "y")
    private int y;

    // Constructors, getters, and setters

    // toString method (optional)
}
