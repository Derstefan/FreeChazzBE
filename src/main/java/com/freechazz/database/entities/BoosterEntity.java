package com.freechazz.database.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "boosters")
public class BoosterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_uuid")
    private UserEntity user;
}
