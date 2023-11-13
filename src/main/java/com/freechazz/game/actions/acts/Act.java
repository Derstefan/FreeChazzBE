package com.freechazz.game.actions.acts;

import com.freechazz.game.actions.connector.Connector;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;

public abstract class Act {

    private Connector connector;

    public Connector getConnector() {
        return connector;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }



    public String toString(){
        if(getConnector()!=null){
            return this.getClass().getSimpleName() + " " + this.getConnector().toString();
        }
        return this.getClass().getSimpleName();
    }

    public Connector createConnector(){
        connector = new Connector(this);
        return connector;
    }
}
