package com.freechazz.game.pieces;

import com.freechazz.game.core.EPlayer;
import org.junit.jupiter.api.Test;

import static com.freechazz.game.pieces.PieceTypeHelper.CROSS1;
import static org.assertj.core.api.Assertions.assertThat;

public class PieceTest {



    @Test
    void generalPieceTypeAndPieceTest(){
        Piece p = new Piece(EPlayer.P1,CROSS1);

        assertThat(CROSS1.getSymbol()).isEqualTo("M");
        assertThat(CROSS1.getActionMap().size()).isEqualTo(4);
        assertThat(p.getOwner()).isEqualTo(EPlayer.P1);
        assertThat(p.getSymbol()).isEqualTo("M");
        //as long as it's not refered to a board
        assertThat(p.getMoveSet().getPossibleMoves().isEmpty()).isTrue();
    }


}
