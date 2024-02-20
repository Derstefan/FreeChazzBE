package com.freechazz.game.formation;

import com.freechazz.database.entities.FormationEntity;
import com.freechazz.database.entities.UserEntity;
import com.freechazz.game.core.ESize;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.PieceType;
import com.freechazz.generators.piece.PieceTypeGenerator;

import java.util.HashMap;

public class Formation {


    private ESize size;
    private HashMap<Pos, PieceType> pieceTypes = new HashMap<>();
    private Pos kingPos;
    private UserEntity owner;


    public void put(PieceType pieceType, Pos pos) {
        pieceTypes.put(pos, pieceType);
    }

    public ESize getSize() {
        return size;
    }

    public void setSize(ESize size) {
        this.size = size;
    }


    public Pos getKingPos() {
        return kingPos;
    }

    public void setKingPos(Pos kingPos) {
        this.kingPos = kingPos;
    }


    public HashMap<Pos, PieceType> getPieceTypes() {
        return pieceTypes;
    }

    public void setPieces(HashMap<Pos, PieceType> pieceTypes) {
        this.pieceTypes = pieceTypes;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public PieceType getKing() {
        return pieceTypes.get(kingPos);
    }

    public Formation() {
    }

    public Formation(FormationEntity formationEntity) {
        this.size = formationEntity.getSize();
        this.kingPos = new Pos(formationEntity.getKingX(), formationEntity.getKingY());
        this.owner = formationEntity.getUser();
        formationEntity.getPiecesFormations().forEach(pieceFormationEntity -> {
            Pos pos = new Pos(pieceFormationEntity.getX(), pieceFormationEntity.getY());
            long seed = pieceFormationEntity.getPiece().getSeed();
            int lvl = pieceFormationEntity.getPiece().getLvl();
            String generatorVersion = pieceFormationEntity.getPiece().getGeneratorVersion();
            PieceType pieceType = PieceTypeGenerator.generate(lvl, seed, generatorVersion);
            pieceTypes.put(pos, pieceType);
        });
    }

    public Formation copy() {
        Formation formation = new Formation();
        formation.setKingPos(kingPos);
        formation.setOwner(owner);
        formation.setSize(size);
        formation.setPieces(pieceTypes);
        return formation;
    }

}
