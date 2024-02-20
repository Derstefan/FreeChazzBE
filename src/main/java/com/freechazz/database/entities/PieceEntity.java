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

    @Column(name = "seed")
    private long seed;
    @Column(name = "lvl")
    private int lvl;
    @Column(name = "generatorVersion")
    private String generatorVersion;

    // Constructors, getters, and setters

    public PieceEntity() {
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

    public Set<PieceFormationEntity> getPieceFormations() {
        return pieceFormations;
    }

    public void setPieceFormations(Set<PieceFormationEntity> pieceFormations) {
        this.pieceFormations = pieceFormations;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public String getGeneratorVersion() {
        return generatorVersion;
    }

    public void setGeneratorVersion(String generatorVersion) {
        this.generatorVersion = generatorVersion;
    }

}
