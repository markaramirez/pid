package com.leerez.pid;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Block {

    boolean killer;
    boolean horizontal;
    int xspeed;
    int yspeed;
    int color;
    Rectangle hitbox;

    public Block() {
        killer = MathUtils.random(1, 40)==1;
        horizontal = MathUtils.random(1, 5)==1;
        if(horizontal) {
            xspeed = MathUtils.random(900, 1500);
            if(MathUtils.random(1, 2) == 1) xspeed *= -1;
        }
        yspeed = MathUtils.random(900, 1300);
        color = MathUtils.random(1, 3);
        if(killer) color = 4;
        hitbox = new Rectangle();
    }
}
