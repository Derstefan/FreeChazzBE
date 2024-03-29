package com.freechazz.generators.piece;

import com.freechazz.game.pieces.PieceType;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PieceTypeGeneratorTest {

    @Test
    public void shouldGeneratePieceType(){
        PieceTypeGenerator gen = new PieceTypeGenerator();
        Random rand = new Random();
        PieceType p1 = gen.generate(1,rand.nextLong(),"V1");


        assertThat(p1).isNotNull();
    }
}
