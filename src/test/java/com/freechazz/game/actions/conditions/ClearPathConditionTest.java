package com.freechazz.game.actions.conditions;

import com.freechazz.game.state.GameState;
import com.freechazz.game.state.GameStateBuilder;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.core.EPlayer;
import org.junit.jupiter.api.Test;

import static com.freechazz.game.pieces.PieceTypeHelper.WALK1;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ClearPathConditionTest {



    @Test
    public void simpleBoardTest(){
        Piece king1 = new Piece(EPlayer.P1,WALK1);
        Piece king2 = new Piece(EPlayer.P2,WALK1);
        GameState board = new GameStateBuilder(15,15)
                .putKing(EPlayer.P1,king1.getPieceType(),new Pos(4,8))
                .putKing(EPlayer.P2, king2.getPieceType(),new Pos(4,6))
                .build();
        assertThat(king1.isPossibleMove(new Pos(4,7))).isTrue();
        assertThat(king2.isPossibleMove(new Pos(4,7))).isTrue();

        assertThat(king1.isPossibleMove(new Pos(4,6))).isTrue();
        assertThat(king2.isPossibleMove(new Pos(4,8))).isTrue();

        assertThat(king1.isPossibleMove(new Pos(4,5))).isFalse();
        assertThat(king2.isPossibleMove(new Pos(4,9))).isFalse();
    }

    @Test
    public void simpleBoardTest2(){

        Piece king1 = new Piece(EPlayer.P1,WALK1);
        Piece p1 = new Piece(EPlayer.P1,WALK1);
        Piece king2 = new Piece(EPlayer.P2,WALK1);
        GameState board = new GameStateBuilder(15,15)
                .putKing(EPlayer.P1,king1.getPieceType(),new Pos(4,8))
                .putKing(EPlayer.P2, king2.getPieceType(),new Pos(4,6))
                .putPiece(p1,new Pos(4,7))
                .build();

        assertThat(king1.isPossibleMove(new Pos(4,7))).isFalse();
    }



}
