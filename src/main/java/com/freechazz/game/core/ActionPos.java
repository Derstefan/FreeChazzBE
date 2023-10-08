package com.freechazz.game.core;

public class ActionPos extends Pos{



    private String tag;

    public ActionPos(int x, int y, String tag) {
        super(x, y);
        this.tag = tag;
    }

    public ActionPos(Pos pos, String tag) {
        super(pos.getX(), pos.getY());
        this.tag = tag;
    }



    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    @Override
    public ActionPos copy(){
        return new ActionPos(getX(),getY(),getTag());
    }
}
