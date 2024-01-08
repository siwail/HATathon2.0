package com.meme.slayer;

public class Blood {
    Main g;
    float x=0, y=0, vx=0, vy=0, s=10;
    int state=3;
    public Blood(Main game) {
        g = game;
    }
    public void math(){
        if(state!=3) {
            s+=(-s)/50*g.time;
            if(s<1){
                state=3;
            }
            vy-=0.5f*g.time;
            x+=vx*g.time;
            y+=vy*g.time;
            if(y<0){
                state=3;
            }
        }
    }
    public void draw() {
        if (state != 3) {
            g.drawer.setColor(0.1f, 0.5f, 0, 1);
            g.drawer.rect(-g.cx+x, -g.cy+y, s, s);
        }
    }
}
