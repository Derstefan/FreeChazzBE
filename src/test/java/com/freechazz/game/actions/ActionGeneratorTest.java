package com.freechazz.game.actions;

import com.freechazz.generators.action.ActionGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Random;

@Slf4j
public class ActionGeneratorTest {

    @Test
    public void test1() {
        for (int i = 0; i < 100; i++) {
            ActionGenerator gen = new ActionGenerator(new Random().nextLong());
            Action action = gen.generate(3);
            System.out.println(action.getAct().toString());
        }
    }
}
