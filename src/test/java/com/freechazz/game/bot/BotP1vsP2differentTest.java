package com.freechazz.game.bot;

import com.freechazz.bots.impl.BetterBot2;
import com.freechazz.database.entities.UserEntity;
import com.freechazz.game.Game;
import com.freechazz.game.GameBuilder;
import com.freechazz.game.core.EPlayer;
import com.freechazz.game.core.ESize;
import com.freechazz.game.formation.Formation;
import com.freechazz.generators.formation.FormationGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class BotP1vsP2differentTest {

    private int winnerp1 = 0;
    private int winnerp2 = 0;

    private int games = 0;
    private int endedGames = 0;

    private double averageDurationForGame = 0;


    @Test
    public void testP1vsP2Different() {
        runTestWithParameters(false, ESize.tiny);
        runTestWithParameters(false, ESize.small);
        runTestWithParameters(false, ESize.medium);
        runTestWithParameters(false, ESize.big);

    }

    @Test
    public void testP1vsP2DifferentWithSerialisation() {
        runTestWithParameters(true, ESize.tiny);
        runTestWithParameters(true, ESize.small);
        runTestWithParameters(true, ESize.medium);
        runTestWithParameters(true, ESize.big);
    }

    private void runTestWithParameters(boolean isSerialized, ESize size) {
        averageDurationForGame = 0;


        for (int i = 0; i < 5; i++) {
            long gameSeed = (long) (Math.random() * 1000000000);
            long gameSeed2 = (long) (Math.random() * 1000000000);

            for (int j = 0; j < 3; j++) {
                long t = System.currentTimeMillis();
                log.info(runGame(isSerialized, gameSeed, gameSeed2, 2, 2, EPlayer.P1, size) + " " + (System.currentTimeMillis() - t) + " ms");
                averageDurationForGame += (System.currentTimeMillis() - t);

                t = System.currentTimeMillis();
                log.info(runGame(isSerialized, gameSeed, gameSeed2, 2, 2, EPlayer.P2, size) + " " + (System.currentTimeMillis() - t) + " ms");
                averageDurationForGame += (System.currentTimeMillis() - t);

                t = System.currentTimeMillis();
                log.info(runGame(isSerialized, gameSeed2, gameSeed, 2, 2, EPlayer.P1, size) + " " + (System.currentTimeMillis() - t) + " ms");
                averageDurationForGame += (System.currentTimeMillis() - t);

                t = System.currentTimeMillis();
                log.info(runGame(isSerialized, gameSeed2, gameSeed, 2, 2, EPlayer.P2, size) + " " + (System.currentTimeMillis() - t) + " ms");
                averageDurationForGame += (System.currentTimeMillis() - t);
            }
        }

        log.info("averageDurationForGame " + averageDurationForGame / (3 * 4 * 5));
        assertThat(isSerialized ? 10000.0 : 4000.0).isGreaterThan(averageDurationForGame / (3 * 4 * 5));
        assertThat((double) winnerp1 / (double) winnerp2 > 0.5 && (double) winnerp1 / (double) winnerp2 < 1.5).isTrue();
        assertThat((double) endedGames / (double) games > 0.65).isTrue();
    }

    private String runGame(boolean isSerialized, long gameSeed, long gameSeed2, int param1, int param2, EPlayer player, ESize size) {
        if (isSerialized) {
            return runGameWithSerialisation(gameSeed, gameSeed2, param1, param2, player, size);
        } else {
            return runGame(gameSeed, gameSeed2, param1, param2, player, size);
        }
    }

// Define runGame and runGameWithSerialisation methods here...


    private String runGame(long formationSeed1, long formationSeed2, int depth1, int depth2, EPlayer firstPlayer, ESize size) {
        games++;
        UserEntity user1 = new UserEntity("Tili");
        UserEntity user2 = new UserEntity("Ludo");

        Formation formation1 = new FormationGenerator(formationSeed1, size, user1).generate();
        Formation formation2 = new FormationGenerator(formationSeed2, size, user2).generate();

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


    private String runGameWithSerialisation(long formationSeed1, long formationSeed2, int depth1, int depth2, EPlayer firstPlayer, ESize size) {
        games++;
        UserEntity user1 = new UserEntity("Tili");
        UserEntity user2 = new UserEntity("Ludo");

        Formation formation1 = new FormationGenerator(formationSeed1, size, user1).generate();
        Formation formation2 = new FormationGenerator(formationSeed2, size, user2).generate();

        Game game = new GameBuilder(formation1, formation2)
                .botP1(new BetterBot2(EPlayer.P1, depth1, (long) (Math.random() * 23312)))
                .botP2(new BetterBot2(EPlayer.P2, depth2, (long) (Math.random() * 351241)))
                .firstPlayer(firstPlayer)
                .build();


        for (int i = 0; i < 300; i++) {
            //serialisation
            game = new Game(game.toJson());

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
