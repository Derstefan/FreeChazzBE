package com.freechazz.game.actions.conditions;

import com.freechazz.game.core.EPlayer;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.state.GameOperatorBuilder;
import org.junit.jupiter.api.Test;

import static com.freechazz.game.pieces.PieceTypeHelper.WALK1;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ClearPathConditionTest {


    //@Test
    public void simpleBoardTest() {
        Piece king1 = new Piece(EPlayer.P1, WALK1);
        Piece king2 = new Piece(EPlayer.P2, WALK1);
        GameOperator board = new GameOperatorBuilder(15, 15)
                .putKing(EPlayer.P1, king1.getPieceType(), new Pos(4, 8))
                .putKing(EPlayer.P2, king2.getPieceType(), new Pos(4, 6))
                .build();
        assertThat(board.getKing1().isPossibleMove(new Pos(4, 7))).isTrue();
        //assertThat(board.getKing2().isPossibleMove(new Pos(4, 7))).isTrue();

        assertThat(board.getKing1().isPossibleMove(new Pos(4, 6))).isTrue();
        assertThat(board.getKing2().isPossibleMove(new Pos(4, 8))).isTrue();

        assertThat(board.getKing1().isPossibleMove(new Pos(4, 5))).isFalse();
        assertThat(board.getKing2().isPossibleMove(new Pos(4, 9))).isFalse();
    }

    @Test
    public void simpleBoardTest2() {

        Piece king1 = new Piece(EPlayer.P1, WALK1);
        Piece p1 = new Piece(EPlayer.P1, WALK1);
        Piece king2 = new Piece(EPlayer.P2, WALK1);
        GameOperator board = new GameOperatorBuilder(15, 15)
                .putKing(EPlayer.P1, king1.getPieceType(), new Pos(4, 8))
                .putKing(EPlayer.P2, king2.getPieceType(), new Pos(4, 6))
                .putPiece(p1, new Pos(4, 7))
                .build();

        // assertThat(king1.isPossibleMove(new Pos(4,7))).isFalse(); //TODO: fix this
    }


}
