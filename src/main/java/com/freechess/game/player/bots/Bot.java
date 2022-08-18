package com.freechess.game.player.bots;

import com.freechess.game.Game;
import com.freechess.game.player.EPlayer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract  class Bot {

    public static long DRAW_DELAY = 200;

    public static final StupidBot STUPIDBOT_P2 = new StupidBot(EPlayer.P2);

    public static final BetterBot BETTERBOT_P2 = new BetterBot(EPlayer.P2);

    private final EPlayer player;

    private boolean isReady = true;

    public EPlayer getPlayer() {
        return player;
    }

    public Bot(EPlayer player){
        this.player = player;
    }

    public void doDrawOn(Game game){
        if(isReady){
            long startTime = System.currentTimeMillis();
            isReady=false;
            doDraw(game);
            log.info("Bot computed draw: "+ (System.currentTimeMillis()-startTime) + " ms in game:" +game.getGameId());
            isReady = true;
        }
    };

    public abstract void doDraw(Game game);
}
