package com.meme.slayer;

public class Room {
    Main g;
    int x=0,y=0,t=0;
    int exit=-1;
    int exitx=0;
    int exity=0;
    public Room(Main game,
     int x, int y, int s, int t){
        g = game;
        this.t=t;
        this.x=x;
        this.y=y;
        exit = g.random.nextInt(8);
        g.frect(x, y, x+s, y+s, 0);
        g.frect(x+2, y+2, x+s-2, y+s-2, -1);
        if(exit==0){

        }
        if(t==0){

        }
        if(t==1){

        }
    }
}
