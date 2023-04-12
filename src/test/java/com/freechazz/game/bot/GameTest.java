package com.freechazz.game.bot;

import com.freechazz.game.Game;
import com.freechazz.game.formation.Formation;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.core.EPlayer;
import com.freechazz.game.player.User;
import com.freechazz.bots.impl.BetterBot2;
import com.freechazz.generators.formation.FormationGenerator;
import com.freechazz.generators.game.ESize;
import com.freechazz.generators.game.GameBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class GameTest {

    private int winnerp1 = 0;
    private int winnerp2 = 0;

    private int games = 0;
    private int endedGames = 0;
    @Test
    public void randomRunner() {

        for(int i = 0; i < 10000; i++){
            long gameSeed = (long)(Math.random()*1000000000);
            runGame(gameSeed,gameSeed,2,2,EPlayer.P1);
            runGame(gameSeed,gameSeed,2,2,EPlayer.P2);
        }
    }

    @Test
    public void randomRunner2() {

        for(int i = 0; i < 10000; i++){
            log.info(runGame(123,123,2,2,EPlayer.P1));
            log.info(runGame(123,123,2,2,EPlayer.P2));
        }
    }







    private String runGame(long formationSeed1, long formationSeed2, int depth1, int depth2, EPlayer firstPlayer) {
        games++;
        User user1 = new User("Tili");
        User user2 = new User("Ludo");


        Formation formation1 = new FormationGenerator(formationSeed1, ESize.tiny,user1).generate();
        Formation formation2 = new FormationGenerator(formationSeed2,ESize.tiny,user2).generate();

        Game game = new GameBuilder(formation1, formation2)
                .botP1(new BetterBot2(EPlayer.P1,depth1,(long)(Math.random()*23312)))
                .botP2(new BetterBot2(EPlayer.P2,depth2,(long)(Math.random()*351241)))
                .firstPlayer(firstPlayer)
                .build();
//        log.info("Game " + game.getState().toString());
        int sumMovesP1 = 0;
        int sumMovesP2 = 0;
        for(Piece p: game.getState().getAllPiecesFrom(EPlayer.P1)){
            sumMovesP1 += p.getPossibleMoves().size();
        }
        for(Piece p: game.getState().getAllPiecesFrom(EPlayer.P2)){
            sumMovesP2 += p.getPossibleMoves().size();
        }
//        log.info("sumMovesP1: " + sumMovesP1 + " sumMovesP2: " + sumMovesP2);

        for(int i = 0; i < 200; i++){
            game.botAction();
//            log.info(" "  + game.getState().toString());
            game.endTurn();
            if(game.getWinner().isPresent()){
                if(EPlayer.P1.equals(game.getWinner().get().getPlayerType())){
                    winnerp1++;
                }else{
                    winnerp2++;
                }
                endedGames++;
                return "Player " + game.getWinner().get().getPlayerType() + " won the game!  " + winnerp1 + ":" + winnerp2 + " | endedGames " + (100*endedGames / games) + "%";
            }
        }
//        log.info("Game "  + game.getState().toString());

        return  "ended Game ... rest: " + game.getState().getAllPiecesFrom(EPlayer.P1).size() + " : " + game.getState().getAllPiecesFrom(EPlayer.P2).size() + "\n";
    }

}
