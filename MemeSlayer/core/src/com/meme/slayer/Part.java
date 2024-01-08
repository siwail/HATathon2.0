package com.meme.slayer;

public class Part {
    Main g;
    float x=0, y=0, vx=0, vy=0, lx=0, ly=0, r=0, rd=0, lr=0, a=0;
    int state=3;
    int[][] f;
    boolean hit=false;
    public Part(Main game) {
        g = game;
    }
    public Part(Main game, int[][] f, float x, float y, float placex, float placey, float r, float pr, float vy){
        hit=true;
        g=game;
        this.f = new int[g.fx][g.fy];
        for(int ix=0;ix<g.fx;ix++){
            for(int iy=0;iy<g.fy;iy++){
                this.f[ix][iy]=f[ix][iy];
            }
        }
        for(float i=r-pr;i<r+180-pr;i+=0.5f){
            for(float i2=0;i2<g.fx;i2+=0.5f) {
                int px =(int)(x + g.sin(i) * i2);
                int py =(int)(y + g.cos(i) * i2);
                if(px<g.fx&&px>-1&&py<g.fy&&py>-1){
                    this.f[px][py]=-1;
                }
            }
        }
        this.x=placex;
        this.y=placey;
        for(float i=r-pr-180;i<r-pr;i+=15){
            for(float i2=0;i2<g.fx/4f;i2+=1) {
                int px =(int)(x + g.sin(i) * i2);
                int py =(int)(y + g.cos(i) * i2);
                if(px<g.fx&&px>-1&&py<g.fy&&py>-1&&this.f[px][py]!=-1){
                    this.f[px][py]=3;
                    g.set_blood(this.x+g.sin(pr)*px*g.step+g.sin(pr+90)*py*g.step, this.y+g.cos(pr)*px*g.step+g.cos(pr+90)*py*g.step, g.sin(i)*10, g.cos(i)*(g.random.nextInt(10)+5), g.random.nextInt(10)+5);
                }
            }
        }


        this.r=pr;
        this.vx+=g.random.nextInt(3)-1+g.sin(-r)*3;
        this.vy+=g.random.nextInt(3)-1+g.cos(-r)*3;
        this.rd=g.random.nextInt(5)-2;
        state=0;
    }
    public void math(){
        if(state!=3) {
            r+=rd*g.time;
            if(r>360){
                r=1;
            }
            if(r<0){
                r=359;
            }
            vy -= 0.5f*g.time;
            x += vx*g.time;
            y += vy*g.time;
            if (y < -100) {
                y=-100;
                vy=-vy/2;
                vx/=2;
                rd/=2;
            }

        }
    }
    public void draw(){
        if(state!=3) {
            for (int ix = 0; ix < g.fx; ix++) {
                for (int iy = 0; iy < g.fy; iy++) {
                    if (f[ix][iy] != -1) {
                        if (f[ix][iy] == 0) {
                            g.drawer.setColor(0, 0, 0, 1);
                        }
                        if (f[ix][iy] == 1) {
                            g.drawer.setColor(0.2f, 0.2f, 0.2f, 1);
                        }
                        if (f[ix][iy] == 2) {
                            g.drawer.setColor(1, 1, 1, 1);
                        }
                        if (f[ix][iy] == 3) {
                            g.drawer.setColor(0.5f, 0, 0, 1);
                        }
                        float p1x = -g.cx + x + g.sin(r + 45) * g.step + g.sin(r) * g.step * ix + g.sin(r + 90) * g.step * iy;
                        float p1y = -g.cy + y + g.cos(r + 45) * g.step + g.cos(r) * g.step * ix + g.cos(r + 90) * g.step * iy;
                        float p2x = -g.cx + x + g.sin(r + 90 + 45) * g.step + g.sin(r) * g.step * ix + g.sin(r + 90) * g.step * iy;
                        float p2y = -g.cy + y + g.cos(r + 90 + 45) * g.step + g.cos(r) * g.step * ix + g.cos(r + 90) * g.step * iy;
                        float p3x = -g.cx + x + g.sin(r + 180 + 45) * g.step + g.sin(r) * g.step * ix + g.sin(r + 90) * g.step * iy;
                        float p3y = -g.cy + y + g.cos(r + 180 + 45) * g.step + g.cos(r) * g.step * ix + g.cos(r + 90) * g.step * iy;
                        float p4x = -g.cx + x + g.sin(r + 270 + 45) * g.step + g.sin(r) * g.step * ix + g.sin(r + 90) * g.step * iy;
                        float p4y = -g.cy + y + g.cos(r + 270 + 45) * g.step + g.cos(r) * g.step * ix + g.cos(r + 90) * g.step * iy;
                        g.drawer.triangle(p1x, p1y, p2x, p2y, p3x, p3y);
                        g.drawer.triangle(p1x, p1y, p4x, p4y, p3x, p3y);

                    }
                }
            }
        }
    }
}
