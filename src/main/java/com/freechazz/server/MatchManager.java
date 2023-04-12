package com.freechazz.server;

import com.freechazz.game.Game;
import com.freechazz.game.formation.Formation;
import com.freechazz.game.formation.FormationBuilder;
import com.freechazz.game.player.User;
import com.freechazz.generators.formation.FormationGenerator;
import com.freechazz.generators.game.ESize;
import com.freechazz.generators.game.GameBuilder;
import com.freechazz.generators.game.GameType;
import com.freechazz.server.DTO.DrawData;
import com.freechazz.server.DTO.GameData;
import com.freechazz.server.DTO.GameParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * The Server is managing all running games and controllerinputs.
 */
@Component
@Slf4j
public final class MatchManager {

    private final Map<UUID, Game> games = new HashMap<>();



    //TODO: eine methode mit builder
    public Game createGame() {
        checkLivingGames();


        User user1 = new User("Tili");
        User user2 = new User("Ludo");


        Formation formation1 = new FormationGenerator(1232312421,ESize.tiny,user1).generate();
        Formation formation2 = new FormationGenerator(32312421,ESize.tiny,user2).generate();

        Game game = new GameBuilder(formation1, formation2).randomStarter().build();
        this.games.put(game.getGameId(), game);

        return game;
    }

    /*
    public Game createGame(GameParams params) {
        checkLivingGames();
        Game game = new Game(params);
        this.games.put(game.getGameId(), game);
        return game;
    }
*/

    /**
     *
     * @param gameId
     * @param userID user already authenticated
     * @param draw
     */
    public void play(UUID gameId, UUID userID, DrawData draw){
        if (!games.containsKey(gameId)) {
            return;
        }
        Game game = getGameById(gameId);
        if(game.getPlayer(game.getPlayersTurn()).getPlayerId().equals(userID)){
            game.play(draw);
        } else {
            log.warn("User {} tried to play in game {} but it is not his turn or his game.", userID, gameId);
        }
    }

    public void checkLivingGames(){
        for(Game g: games.values()){
            if(System.currentTimeMillis()-g.getLastAction()>259200000){ // 3 days
                games.remove(g);
            }
        }
    }

    public Game getGameById(UUID gameId) {
        return this.games.get(gameId);
    }

    public int getGameNumbers(){
        return games.size();
    }

    public Set<UUID> getGameIDs(){
        return games.keySet();
    }

    public ArrayList<GameData> getGameDataList(){
        ArrayList<GameData> gameList = new ArrayList<>();
        for(Game g:games.values()){
            gameList.add(new GameData(g));
        }
        return gameList;
    }



}