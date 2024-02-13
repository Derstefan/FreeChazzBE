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
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Slf4j
public class BestFormationTest {


    private int games = 0;
    private int endedGames = 0;


    HashMap<Long, HashMap<Long, Integer>> seeds = new HashMap<>();


    @Test
    public void testBestFormations() {

        // some seeds for formations
        ArrayList<Long> seedList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            long s = (long) (Math.random() * 1000);
            seedList.add(s);
            seeds.put(s, new HashMap<>());
        }
        for (long s : seeds.keySet()) {
            for (long t : seeds.keySet()) {
                seeds.get(s).put(t, 0);
            }
        }


        for (int i = 0; i < seedList.size(); i++) {
            System.out.print(seedList.get(i) + ": ");
            for (int j = 0; j < seedList.size(); j++) {
                if (seedList.get(i) != seedList.get(j)) {

                    for (int k = 0; k < 5; k++) {
                        runGame(seedList.get(i), seedList.get(j), 2, 2, EPlayer.P1);
                        runGame(seedList.get(i), seedList.get(j), 2, 2, EPlayer.P2);
                    }

                    log.info("Running game  i:" + i + " j:" + j + " with seeds " + seedList.get(i) + " and " + seedList.get(j) + " " + seeds.get(seedList.get(i)).get(seedList.get(j)) + " times");
                }

            }

        }
        System.out.print("Seeds: ");

        for (int i = 0; i < seedList.size(); i++) {
            System.out.print(" " + seedList.get(i) + " ");
        }
        System.out.println();

        for (int i = 0; i < seedList.size(); i++) {
            System.out.print(seedList.get(i) + ": ");
            for (int j = 0; j < seedList.size(); j++) {
                if (i != j) {
                    System.out.print(" " + seeds.get(seedList.get(i)).get(seedList.get(j)) + " ");
                } else {
                    System.out.print(" X  ");
                }
            }
            System.out.println();
        }

//        ArrayList<Long> sorted = new ArrayList<>(seeds.keySet());
//        sorted.sort((o1, o2) -> seeds.get(o2)-seeds.get(o1));
//        for(long seed: sorted){
//            log.info("Seed: " + seed + " won " + seeds.get(seed) + " times");
//        }


    }

    private String runGame(long formationSeed1, long formationSeed2, int depth1, int depth2, EPlayer firstPlayer) {
        games++;
        User user1 = new User(UUID.randomUUID(), "Tili");
        User user2 = new User(UUID.randomUUID(), "Ludo");

        Formation formation1 = new FormationGenerator(formationSeed1, ESize.tiny, user1).generate();
        Formation formation2 = new FormationGenerator(formationSeed2, ESize.tiny, user2).generate();

        Game game = new GameBuilder(formation1, formation2)
                .botP1(new BetterBot2(EPlayer.P1, depth1, (long) (Math.random() * 23312)))
                .botP2(new BetterBot2(EPlayer.P2, depth2, (long) (Math.random() * 351241)))
                .firstPlayer(firstPlayer)
                .build();

        for (int i = 0; i < 200; i++) {
            game.botAction();
            if (game.getWinner().isPresent()) {
                if (EPlayer.P1.equals(game.getWinner().get().getPlayerType())) {
                    seeds.get(formationSeed1).put(formationSeed2, seeds.get(formationSeed1).get(formationSeed2) + 1);
                    endedGames++;
                    return "Player " + game.getWinner().get().getPlayerType() + "(" + formationSeed1 + ") won the game!  " + (100 * endedGames / games) + "%";
                } else {
                    seeds.get(formationSeed2).put(formationSeed1, seeds.get(formationSeed2).get(formationSeed1) + 1);
                    endedGames++;
                    return "Player " + game.getWinner().get().getPlayerType() + "(" + formationSeed2 + ") won the game!  " + (100 * endedGames / games) + "%";
                }
            }
        }
        return "no winner ... rest: " + game.getState().getAllPiecesFrom(EPlayer.P1).size() + " : " + game.getState().getAllPiecesFrom(EPlayer.P2).size();
    }

}
