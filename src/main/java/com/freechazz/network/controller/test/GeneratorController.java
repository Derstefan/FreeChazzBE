package com.freechazz.network.controller.test;


import com.freechazz.game.pieces.PieceType;
import com.freechazz.generators.piece.defaultGenerator.PieceTypeGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"https://free-chazz-fe.herokuapp.com","http://localhost:3000"})
@RestController
@RequestMapping("api/")
public class GeneratorController {

    // generate piece
    @GetMapping("generatepiece/{lvl}/{seed}/{generatorVersion}")
    public ResponseEntity<PieceType> getPiece(@PathVariable int lvl,@PathVariable long seed,@PathVariable String generatorVersion){

        PieceType pieceType = new PieceTypeGenerator().generate(lvl,seed,generatorVersion);
        return ResponseEntity.ok(pieceType);
    }

    @GetMapping("generatepiece/{lvl}/{seed}")
    public ResponseEntity<PieceType> getPiece(@PathVariable int lvl,@PathVariable long seed){

        PieceType pieceType = new PieceTypeGenerator().generate(lvl,seed,"v1");
        return ResponseEntity.ok(pieceType);
    }


}
