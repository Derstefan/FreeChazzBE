package com.freechazz.server;

import com.freechazz.bots.impl.BetterBotClean;
import com.freechazz.game.Game;
import com.freechazz.game.core.EPlayer;
import com.freechazz.game.eventManager.DrawEvents;
import com.freechazz.game.formation.Formation;
import com.freechazz.game.GameBuilder;
import com.freechazz.server.DTO.game.client.DrawData;
import com.freechazz.server.DTO.GameData;
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


    public Game createGame(Formation f1,EPlayerType playerType1,EPlayerType playerType2, Formation f2) {
        checkLivingGames();
        if(checkFormationMatch(f1,f2))return null;


        GameBuilder gameBuilder = new GameBuilder(f1, f2).randomStarter();
        if(EPlayerType.Bot.equals(playerType1)){
            gameBuilder.botP1(new BetterBotClean(EPlayer.P1,2));
        }
        if(EPlayerType.Bot.equals(playerType2)){
            gameBuilder.botP2(new BetterBotClean(EPlayer.P2,2));
        }
        Game game =gameBuilder.build();
        this.games.put(game.getGameId(), game);
        return game;
    }



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

    /**
     *
     * @param gameId
     * @param userID user already authenticated
     */
    public void surrender(UUID gameId, UUID userID){
        if (!games.containsKey(gameId)) {
            return;
        }
        Game game = getGameById(gameId);
        if(game.getPlayer(game.getPlayersTurn()).getPlayerId().equals(userID)){
            game.surrender();
        } else {
            log.warn("User {} tried to surrender in game {} but it is not his turn or his game.", userID, gameId);
        }
    }
/*
    public ArrayList<DrawEvents> getDrawEventsSince(UUID gameId, int turn){
        if (!games.containsKey(gameId)) {
            return null;
        }
        Game game = getGameById(gameId);
        return game.getDrawsSince(turn);
    }
*/


    /**
     * remove old games
     */
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

    private boolean checkFormationMatch(Formation f1, Formation f2){
        if(!f1.getSize().equals(f2.getSize())){
            log.warn("Formations have not same size, match is not possible.");
        }

        return true;
    }



}