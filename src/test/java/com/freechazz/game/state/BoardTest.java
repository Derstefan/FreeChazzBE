package com.freechazz.game.state;

import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.core.EPlayer;
import org.junit.jupiter.api.Test;

import static com.freechazz.game.pieces.PieceTypeHelper.CROSS1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {



    @Test
    public void simpleBoardTest(){
        Piece king1 = new Piece(EPlayer.P1,CROSS1);
        Piece king2 = new Piece(EPlayer.P2,CROSS1);
        GameOperator board = new GameOperatorBuilder(15,15)
                .putKing(EPlayer.P1,king1.getPieceType(),new Pos(0,6))
                .putKing(EPlayer.P2, king2.getPieceType(),new Pos(14,6))
                .build();
        assertThat(board).isNotNull();
        assertThat(board.areEnemys(king1,king1)).isFalse();
        assertThat(board.areEnemys(king1,king2)).isTrue();
    }


    @Test
    public void whenBoardisGeneratedWithoutKings_thenBoardisNull(){
        GameOperator board = new GameOperatorBuilder(15,15)
                .build();
        assertThat(board).isNull();
    }

    @Test
    public void boardShouldtBeAbleToCopy(){
        Piece king1 = new Piece(EPlayer.P1,CROSS1);
        Piece king2 = new Piece(EPlayer.P2,CROSS1);
        GameOperator board = new GameOperatorBuilder(15,15)
                .putKing(EPlayer.P1,king1.getPieceType(),new Pos(0,6))
                .putKing(EPlayer.P2, king2.getPieceType(),new Pos(14,6))
                .build();
        GameOperator boardCopy = board.copy();
        assertThat(boardCopy).isNotNull();
        assertThat(boardCopy.pieceAt(new Pos(0,6))).isNotNull();
        assertThat(boardCopy.pieceAt(new Pos(14,6))).isNotNull();
    }






}
