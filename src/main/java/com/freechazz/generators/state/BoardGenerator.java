package com.freechazz.generators.state;

import com.freechazz.GameState;
import com.freechazz.generators.game.ESize;

public interface BoardGenerator {

    public GameState generate();

    public GameState generate(ESize eSize);

}
