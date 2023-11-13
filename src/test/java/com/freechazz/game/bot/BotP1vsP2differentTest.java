package com.freechazz.game.bot;

import com.freechazz.bots.impl.BetterBot2;
import com.freechazz.game.Game;
import com.freechazz.game.GameBuilder;
import com.freechazz.game.core.EPlayer;
import com.freechazz.game.core.ESize;
import com.freechazz.game.formation.Formation;
import com.freechazz.game.player.User;
import com.freechazz.generators.formation.FormationGenerator;
import lombok.extern.slf4j.Slf4j;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class BotP1vsP2differentTest {

    private int winnerp1 = 0;
    private int winnerp2 = 0;

    private int games = 0;
    private int endedGames = 0;


    //@Test
    public void testP1vsP2Different() {

        for (int i = 0; i < 5; i++) {
            long gameSeed = (long) (Math.random() * 1000000000);
            long gameSeed2 = (long) (Math.random() * 1000000000);
            for (int j = 0; j < 30; j++) {
                log.info(runGame(gameSeed, gameSeed2, 2, 2, EPlayer.P1));
                log.info(runGame(gameSeed, gameSeed2, 2, 2, EPlayer.P2));
                log.info(runGame(gameSeed2, gameSeed, 2, 2, EPlayer.P1));
                log.info(runGame(gameSeed2, gameSeed, 2, 2, EPlayer.P2));
            }
        }
        assertThat((double) winnerp1 / (double) winnerp2 > 0.6 && (double) winnerp1 / (double) winnerp2 < 1.4).isTrue();
        assertThat((double) endedGames / (double) games > 0.65).isTrue();
    }

    private String runGame(long formationSeed1, long formationSeed2, int depth1, int depth2, EPlayer firstPlayer) {
        games++;
        User user1 = new User("Tili");
        User user2 = new User("Ludo");


        Formation formation1 = new FormationGenerator(formationSeed1, ESize.tiny, user1).generate();
        Formation formation2 = new FormationGenerator(formationSeed2, ESize.tiny, user2).generate();

        Game game = new GameBuilder(formation1, formation2)
                .botP1(new BetterBot2(EPlayer.P1, depth1, (long) (Math.random() * 23312)))
                .botP2(new BetterBot2(EPlayer.P2, depth2, (long) (Math.random() * 351241)))
                .firstPlayer(firstPlayer)
                .build();


        for (int i = 0; i < 300; i++) {
            game.botAction();
            if (game.getWinner().isPresent()) {
                if (EPlayer.P1.equals(game.getWinner().get().getPlayerType())) {
                    winnerp1++;
                } else {
                    winnerp2++;
                }
                endedGames++;
                return "Player " + game.getWinner().get().getPlayerType() + " won the game!  " + winnerp1 + ":" + winnerp2 + " | endedGames " + (100 * endedGames / games) + "%";
            }
        }
        return "no winner ... rest: " + game.getState().getAllPiecesFrom(EPlayer.P1).size() + " : " + game.getState().getAllPiecesFrom(EPlayer.P2).size();
    }

}
