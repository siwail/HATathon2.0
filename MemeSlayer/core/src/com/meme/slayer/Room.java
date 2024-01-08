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
        g.set_entity((exitx+5)*g.stepb,(exity+5)*g.stepb);
        if(t==0){
            g.frect(x+10, y+30, x+15, y+32, 0);
            g.frect(x+30, y+30, x+45, y+32, 0);
        }
        if(t==1){
            g.frect(x+20, y+20, x+30, y+30, 0);
        }

        if(g.q>2) {
            if (g.random.nextInt(2) == 0 && g.pils < g.pilq) {
                g.pilx[g.pils] = (x + 20 + g.random.nextInt(13) - 6) * g.stepb;
                g.pily[g.pils] = (y + 20 + g.random.nextInt(13) - 6) * g.stepb;
                g.pilse[g.pils] = (g.random.nextInt(50) + 10) * g.step;
                g.pils++;
            }

            g.set_entity((x + 20) * g.stepb, (y + 10) * g.stepb);
        }
    }
    public void generate_exit(){
        if(exit==0){
            g.frect(exitx-20+3, exity-20+3, exitx+20-3, exity+20-3, -1);
        }
        if(exit==1){
            g.frect(exitx-20+3, exity-10+s+3, exitx+20-3, exity+20+s-3, -1);
        }
        if(exit==2){
            g.frect(exitx-20+s+3, exity-20+3, exitx+20+s-3, exity+20-3, -1);
        }
        if(exit==3){
            g.frect(exitx-20+s+3, exity-20+s+3, exitx+20+s-3, exity+20+s-3, -1);
        }
    }
}
