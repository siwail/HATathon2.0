package com.meme.slayer;

public class Slice {
    Main g;
    float x=0, y=0, r=0, s=10;
    int state=3;
    public Slice(Main game) {
        g = game;
    }
    public void math(){
        if(state!=3) {
            s+=(-s)/3*g.time;
            if(s<1){
                state=3;
            }
        }
    }
    public void draw() {
        if (state != 3) {
            g.drawer.setColor(1, 1, 1, 1);
            g.drawer.rectLine(-g.cx+x, -g.cy+y, -g.cx+x+g.sin(r)*g.step*32, -g.cy+y+g.cos(r)*g.step*32, s/2);
            g.drawer.rectLine(-g.cx+x+g.sin(r)*g.step*7, -g.cy+y+g.cos(r)*g.step*7, -g.cx+x+g.sin(r)*g.step*25, -g.cy+y+g.cos(r)*g.step*25, s);
        }
    }
}
