package com.freechazz.game;

import com.freechazz.game.pieces.Piece;
import com.freechazz.game.state.Board;
import com.freechazz.game.state.GameOperator;
import com.freechazz.network.DTO.game.server.PieceDTO;
import com.freechazz.network.DTO.game.server.PieceTypeDTO;

import java.util.ArrayList;

public class GameHelper {

    public static ArrayList<PieceDTO> getPieceDTOs(Game game, int turn) {
        int lastStateWithPiecesIndex = game.getState().getHistory().getLastMatchStateIndexWithPieces(turn);
        GameOperator tempOperator = GameOperator.getInstanceWithoutLogging(game.getState().getWidth(), game.getState().getHeight());
        tempOperator.setBoard(new Board(game.getState().getBoard().getWidth(), game.getState().getBoard().getHeight(), game.getState().getHistory().getHistoryState(lastStateWithPiecesIndex).getPieceDTOs()));

        for (int i = lastStateWithPiecesIndex + 1; i <= turn; i++) {
            tempOperator.performDrawEvent(game.getState().getHistory().getHistoryState(i).getDrawEvent());
        }
        tempOperator.computePossibleMoves();
        ArrayList<PieceDTO> pieceDTOs = new ArrayList<>();
        for (Piece piece : tempOperator.getBoard().getPieces()) {
            pieceDTOs.add(new PieceDTO(piece));
        }
        return pieceDTOs;
    }

    public static ArrayList<PieceTypeDTO> getPieceTypeDTOs(Game game, int turn) {
        int lastStateWithPiecesIndex = game.getState().getHistory().getLastMatchStateIndexWithPieces(turn);
        GameOperator tempOperator = GameOperator.getInstanceWithoutLogging(game.getState().getWidth(), game.getState().getHeight());
        tempOperator.setBoard(new Board(game.getState().getBoard().getWidth(), game.getState().getBoard().getHeight(), game.getState().getHistory().getHistoryState(lastStateWithPiecesIndex).getPieceDTOs()));

        for (int i = lastStateWithPiecesIndex + 1; i <= turn; i++) {
            tempOperator.performDrawEvent(game.getState().getHistory().getHistoryState(i).getDrawEvent());
        }
        tempOperator.computePossibleMoves();
        ArrayList<PieceTypeDTO> pieceTypeDTOs = new ArrayList<>();
        for (Piece piece : tempOperator.getBoard().getPieces()) {
            pieceTypeDTOs.add(new PieceTypeDTO(piece.getPieceType()));
        }
        return pieceTypeDTOs;
    }
}
