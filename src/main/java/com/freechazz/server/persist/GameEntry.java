package com.freechess.server.persist;

import com.freechess.game.board.ESize;
import com.freechess.game.player.EPlayer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document("gamedata")
public class GameEntry {

    @Id
    private String id;
    private String uuid;
    private String player1;
    private String player2;
    private boolean isSingleplayer;
    private long seed;
    private int width;
    private int height;
    private Date startDate;
    private Date endGame;
    private String winner = "";

    public GameEntry(String player1, String player2,UUID uuid ,long seed,boolean isSingleplayer, int width, int height) {
        this.player1 = player1;
        this.player2 = player2;
        this.seed = seed;
        this.uuid = uuid.toString();
        this.isSingleplayer = isSingleplayer;
        this.startDate = new Date();
        this.width = width;
        this.height = height;
    }

    public void winner(EPlayer p){
        endGame = new Date();
        if(EPlayer.P1.equals(p)){
            winner=player1;
        } else {
            winner=player2;
        }
    }
}
