package com.freechess.game;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GameTest {




    @Test
    public void GameTest1(){
        Game game = new Game();
        assertThat(game).isNotNull();
    }

    @Test
    public void GameCopyTest(){
        Game game = new Game();
        Game game2 = game.copy();
        assertThat(game2).isNotNull();
    }
}
