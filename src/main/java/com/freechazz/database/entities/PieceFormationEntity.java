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

    public PieceFormationEntity() {
        this.id = UUID.randomUUID();
    }

    //getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PieceEntity getPiece() {
        return piece;
    }

    public void setPiece(PieceEntity piece) {
        this.piece = piece;
    }

    public FormationEntity getFormation() {
        return formation;
    }

    public void setFormation(FormationEntity formation) {
        this.formation = formation;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
