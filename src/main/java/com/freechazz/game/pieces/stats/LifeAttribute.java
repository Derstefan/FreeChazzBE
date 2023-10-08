package com.freechazz.game.pieces.stats;

public class LifeAttribute extends Attribute {

    private int life = 1;
    private int maxLife = 1;


    public LifeAttribute(int life) {
        this.life = life;
        this.maxLife = life;
    }

    public int getLife() {
        return life;
    }

    public void heal(int heal) {
        life += heal;
        if (life > maxLife) {
            life = maxLife;
        }
    }

    public void damage(int damage) {
        life -= damage;
    }

    public boolean isDead() {
        return life <= 0;
    }
}
