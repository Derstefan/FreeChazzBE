package com.freechazz.serialisation;

import com.freechazz.bots.impl.BetterBot2;
import com.freechazz.game.Game;
import com.freechazz.game.GameBuilder;
import com.freechazz.game.core.EPlayer;
import com.freechazz.game.core.ESize;
import com.freechazz.game.formation.Formation;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.player.User;
import com.freechazz.generators.formation.FormationGenerator;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SerialisationTest {


    @Test
    public void testSerialisation() {
        User user1 = new User(UUID.randomUUID(), "Tili");
        User user2 = new User(UUID.randomUUID(), "Ludo");

        Formation formation1 = new FormationGenerator((long) (Math.random() * 1000), ESize.tiny, user1).generate();
        Formation formation2 = new FormationGenerator((long) (Math.random() * 1000), ESize.tiny, user2).generate();

        Game game = new GameBuilder(formation1, formation2)
                .botP1(new BetterBot2(EPlayer.P1, 2, (long) (Math.random() * 23312)))
                .botP2(new BetterBot2(EPlayer.P2, 2, (long) (Math.random() * 351241)))
                .randomStarter()
                .build();
        game.botAction();

        String json = game.toJson();

        Game gameSerialized = new Game(json);

        String pieces1 = "";
        for (Piece p : game.getState().getAllPieces()) {
            pieces1 += p.toString() + "";
        }

        String pieces2 = "";
        for (Piece p : gameSerialized.getState().getAllPieces()) {
            pieces2 += p.toString() + "";
        }


        assertThat(game.getState().getHistory().toString()).isEqualTo(gameSerialized.getState().getHistory().toString());
        assertThat(game.getTurns()).isEqualTo(gameSerialized.getTurns());
        assertThat(pieces1).isEqualTo(pieces2);
        assertThat(game.getState().getBoard().toString()).isEqualTo(gameSerialized.getState().getBoard().toString());
        assertThat(gameSerialized.getTurns()).isEqualTo(1);
        assertThat(gameSerialized.botAction()).isTrue();
        assertThat(gameSerialized.getTurns()).isEqualTo(2);
    }
}
