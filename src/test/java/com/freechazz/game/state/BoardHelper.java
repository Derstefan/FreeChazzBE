package com.freechazz.game.state;

import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.core.EPlayer;

import static com.freechazz.game.pieces.PieceTypeHelper.CROSS1;
import static com.freechazz.game.pieces.PieceTypeHelper.WALK1;

public class BoardHelper {

    public static GameOperator exampleBoard(){
            Piece king1 = new Piece(EPlayer.P1,CROSS1);
            Piece king2 = new Piece(EPlayer.P2,CROSS1);
            GameOperator board = new GameOperatorBuilder(15,15)
                    .putKing(EPlayer.P1,king1.getPieceType(),new Pos(0,6))
                    .putKing(EPlayer.P2, king2.getPieceType(),new Pos(14,6))
                    .build();
         return board;
    }

    public static GameOperator exampleBoard2(){
        Piece king1 = new Piece(EPlayer.P1,WALK1);
        Piece king2 = new Piece(EPlayer.P2,WALK1);
        GameOperator board = new GameOperatorBuilder(15,15)
                .putKing(EPlayer.P1,king1.getPieceType(),new Pos(4,6))
                .putKing(EPlayer.P2, king2.getPieceType(),new Pos(4,8))
                .build();
        return board;
    }
}
