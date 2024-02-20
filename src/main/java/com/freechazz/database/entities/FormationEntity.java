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

    @Column(name = "king_x")
    private int kingX;

    @Column(name = "king_y")
    private int kingY;

    @OneToMany(mappedBy = "formation1")
    private Set<MatchEntity> matchesWithFormation1;

    @OneToMany(mappedBy = "formation2")
    private Set<MatchEntity> matchesWithFormation2;

    // Constructors, getters, and setters

    public FormationEntity() {
        this.id = UUID.randomUUID();
    }

    //getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Set<PieceFormationEntity> getPiecesFormations() {
        return piecesFormations;
    }

    public void setPiecesFormations(Set<PieceFormationEntity> piecesFormations) {
        this.piecesFormations = piecesFormations;
    }

    public ESize getSize() {
        return size;
    }

    public void setSize(ESize size) {
        this.size = size;
    }

    public int getKingX() {
        return kingX;
    }

    public void setKingX(int kingX) {
        this.kingX = kingX;
    }

    public int getKingY() {
        return kingY;
    }

    public void setKingY(int kingY) {
        this.kingY = kingY;
    }
}
