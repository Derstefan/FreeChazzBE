package com.freechazz.database.entities;

import com.freechazz.network.GameType;
import io.micrometer.core.lang.Nullable;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "matches")
public class MatchEntity {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user1_id")
    private UserEntity user1;

    @ManyToOne
    @JoinColumn(name = "user2_id")
    private UserEntity user2;

    @ManyToOne
    @JoinColumn(name = "watchUser_id")
    private UserEntity watchUser;

    @ManyToOne
    @JoinColumn(name = "formation1_id")
    private FormationEntity formation1;

    @ManyToOne
    @JoinColumn(name = "formation2_id")
    private FormationEntity formation2;

    @Column(name = "gameType")
    private GameType gameType;

    @Lob
    @Nullable
    private String gameData;


    // Constructors, getters, and setters
    public MatchEntity(UUID id, UserEntity user1, UserEntity user2, FormationEntity formation1, FormationEntity formation2, String gameData) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
        this.formation1 = formation1;
        this.formation2 = formation2;
        this.gameData = gameData;
    }

    public MatchEntity(UserEntity user1) {
        // this.id = UUID.randomUUID();
        this.user1 = user1;
    }

    public MatchEntity(UserEntity user1, UserEntity user2) {
        // this.id = UUID.randomUUID();
        this.user1 = user1;
        this.user2 = user2;
    }


    public MatchEntity() {

    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getUser1() {
        return user1;
    }

    public void setUser1(UserEntity user1) {
        this.user1 = user1;
    }

    public UserEntity getUser2() {
        return user2;
    }

    public void setUser2(UserEntity user2) {
        this.user2 = user2;
    }

    public String getGameData() {
        return gameData;
    }

    public void setGameData(String gameData) {
        this.gameData = gameData;
    }

    public FormationEntity getFormation1() {
        return formation1;
    }

    public void setFormation1(FormationEntity formation1) {
        this.formation1 = formation1;
    }

    public FormationEntity getFormation2() {
        return formation2;
    }

    public void setFormation2(FormationEntity formation2) {
        this.formation2 = formation2;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public UserEntity getWatchUser() {
        return watchUser;
    }

    public void setWatchUser(UserEntity watchUser) {
        this.watchUser = watchUser;
    }
}
