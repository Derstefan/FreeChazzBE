package com.freechazz.game.state;

import com.freechazz.game.core.Pos;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PositionTest {

    public static Set<Pos> positions = new HashSet<Pos>(Arrays.asList(new Pos(0,1),new Pos(-2,0),new Pos(0,-2),new Pos(1,2)));
    public static Pos posIn = new Pos(0,1);
    public static Pos posNotIn = new Pos(0,6);
    public static Pos pos = new Pos(-1,5);
    public static Pos posEquals = new Pos(-1,5);

    @Test
    public void testPositionFunctionalities(){
        assertThat(pos.equals(posEquals)).isTrue();
        assertThat(pos.equals(posIn)).isFalse();
        assertThat(posIn.isIn(positions)).isTrue();
        assertThat(posNotIn.isIn(positions)).isFalse();

    }
}
