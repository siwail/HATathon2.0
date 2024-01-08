package com.meme.slayer;

public class Room {
    Main g;
    int x=0,y=0,t=0;
    int s;
    int exit=-1;
    int exitx=0;
    int exity=0;
    public Room(Main game,
     int x, int y, int s, int t){
        g = game;
        this.t=t;
        this.x=x;
        this.y=y;
        this.s=s;
        exit = g.random.nextInt(4);
        g.frect(x, y, x+s, y+s, 0);
        g.frect(x+3, y+3, x+s-3, y+s-3, -1);
        if(exit==0){
            exitx=x+s;
            exity=y+s;
        }
        if(exit==1){
            exitx=x+s;
            exity=y-s;
        }
        if(exit==2){
            exitx=x-s;
            exity=y+s;
        }
        if(exit==3){
            exitx=x-s;
            exity=y-s;
        }
        g.frect(exitx-5, exity-5, exitx+5, exity+5, -1);
        if(t==0){

        }
        if(t==1){

        }
    }
}
