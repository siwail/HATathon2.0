package com.meme.slayer;

import com.badlogic.gdx.Gdx;

public class Entity {
    Main g;
    float x=520, y=800, lx=0, ly=0, vx=0, vy=0, r=90, lr=0;
    int state=3, a=0, a1=4, a2=11, a3=18;
    int next=10, next1=3, next2=3, next3=3;
    int dir=1, dir1=1, dir2=1, dir3=1;
    int state1=0, state2=0, state3=0;
    int id=0;
    boolean enemy=true;
    boolean spaced=false;
    public Entity(Main game){
        g=game;

    }
    public void math(){
        if(state!=3) {
            if (!enemy) {

                next--;
                if (next == 0) {
                    next = 10;
                    a += 1;
                    if (a > 2) {
                        a = 0;
                    }
                }
                if (state1 == 1) {
                    next1--;
                    if (next1 == 0) {
                        next1 = 3;
                        a1 += dir1;
                        if (a1 > 7 || a1 < 5) {
                            dir1 = -dir1;
                        }
                    }
                }
                if (state2 == 1) {
                    next2--;
                    if (next2 <= 0) {
                        next2 = 3;
                        a2 += 1;
                        if (a2 > 17) {
                            a2 = 10;
                        }
                    }
                }
                if (state3 == 1) {
                    next3--;
                    if (next3 <= 0) {
                        next3 = 5;
                        a3 += dir3;
                        if (a3 > 21 || a3 < 19) {
                            dir3 = -dir3;
                        }
                    }
                }
            }else{
                next--;
                if (next == 0) {
                    next = 10;
                    a += 1;
                    if (a > 2) {
                        a = 0;
                    }
                }
            }
            vy-=0.5f;
            if(id==g.id) {
                vx += g.goa + g.god;
                vy += g.gow + g.gos;
            }
            vx+=(-vx)/10;
            lx=x;
            ly=y;
            x+=vx;
            y+=vy;
            spaced=false;

            int px1 = (int) (x / g.stepb)+6;
            int py1 = (int) (y / g.stepb);
            if (g.act(px1, py1) && g.F[px1][py1].t != -1) {
                x = lx;
                vx = -vx / 2;
                spaced=true;
            }
            int px2 = (int) (x / g.stepb);
            int py2 = (int) (y / g.stepb);
            if (g.act(px2, py2) && g.F[px2][py2].t != -1) {
                x = lx;
                vx = -vx / 2;
                spaced=true;
            }
            int px3 = (int) (x / g.stepb);
            int py3 = (int) (y / g.stepb)-8;
            if (g.act(px3, py3) && g.F[px3][py3].t != -1) {
                y = ly;
                vy = -vy / 2;
                spaced=true;
            }
            int px4 = (int) (x / g.stepb);
            int py4 = (int) (y / g.stepb)+8;
            if (g.act(px4, py4) && g.F[px4][py4].t != -1) {
                y = ly;
                vy = -vy / 2;

            }
        }
    }
    public void draw(){
        if(state!=3) {
            if (!enemy) {
                for (int ix = 0; ix < g.fx; ix++) {
                    for (int iy = 0; iy < g.fy; iy++) {
                        if (g.f[a][ix][iy] != -1) {
                            if (g.f[a][ix][iy] == 0) {
                                g.drawer.setColor(0, 0, 0, 1);
                            }
                            if (g.f[a][ix][iy] == 1) {
                                g.drawer.setColor(0.2f, 0.2f, 0.2f, 1);
                            }
                            if (g.f[a][ix][iy] == 2) {
                                g.drawer.setColor(1, 1, 1, 1);
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
                if (state3 == 1) {
                    for (int ix = 0; ix < g.fx; ix++) {
                        for (int iy = 0; iy < g.fy; iy++) {
                            if (g.f[a3][ix][iy] != -1) {
                                if (g.f[a3][ix][iy] == 0) {
                                    g.drawer.setColor(0, 0, 0, 1);
                                }
                                if (g.f[a3][ix][iy] == 1) {
                                    g.drawer.setColor(0.2f, 0.2f, 0.2f, 1);
                                }
                                if (g.f[a3][ix][iy] == 2) {
                                    g.drawer.setColor(1, 1, 1, 1);
                                }
                                float p1x = -g.cx + x + g.sin(r + 45) * g.step + g.sin(r) * g.step * ix + g.sin(r + 90) * g.step * iy;
                                float p1y = -g.cy + y - g.step * 3 + g.cos(r + 45) * g.step + g.cos(r) * g.step * ix + g.cos(r + 90) * g.step * iy;
                                float p2x = -g.cx + x + g.sin(r + 90 + 45) * g.step + g.sin(r) * g.step * ix + g.sin(r + 90) * g.step * iy;
                                float p2y = -g.cy + y - g.step * 3 + g.cos(r + 90 + 45) * g.step + g.cos(r) * g.step * ix + g.cos(r + 90) * g.step * iy;
                                float p3x = -g.cx + x + g.sin(r + 180 + 45) * g.step + g.sin(r) * g.step * ix + g.sin(r + 90) * g.step * iy;
                                float p3y = -g.cy + y - g.step * 3 + g.cos(r + 180 + 45) * g.step + g.cos(r) * g.step * ix + g.cos(r + 90) * g.step * iy;
                                float p4x = -g.cx + x + g.sin(r + 270 + 45) * g.step + g.sin(r) * g.step * ix + g.sin(r + 90) * g.step * iy;
                                float p4y = -g.cy + y - g.step * 3 + g.cos(r + 270 + 45) * g.step + g.cos(r) * g.step * ix + g.cos(r + 90) * g.step * iy;
                                g.drawer.triangle(p1x, p1y, p2x, p2y, p3x, p3y);
                                g.drawer.triangle(p1x, p1y, p4x, p4y, p3x, p3y);

                            }
                        }
                    }
                }
                if (state2 == 1) {
                    for (int ix = 0; ix < g.fx; ix++) {
                        for (int iy = 0; iy < g.fy; iy++) {
                            if (g.f[a2][ix][iy] != -1) {
                                if (g.f[a2][ix][iy] == 0) {
                                    g.drawer.setColor(0, 0, 0, 1);
                                }
                                if (g.f[a2][ix][iy] == 1) {
                                    g.drawer.setColor(0.2f, 0.2f, 0.2f, 1);
                                }
                                if (g.f[a2][ix][iy] == 2) {
                                    g.drawer.setColor(1, 1, 1, 1);
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
                if (state1 == 1) {
                    for (int ix = 0; ix < g.fx; ix++) {
                        for (int iy = 0; iy < g.fy; iy++) {
                            if (g.f[a1][ix][iy] != -1) {
                                if (g.f[a1][ix][iy] == 0) {
                                    g.drawer.setColor(0, 0, 0, 1);
                                }
                                if (g.f[a1][ix][iy] == 1) {
                                    g.drawer.setColor(0.2f, 0.2f, 0.2f, 1);
                                }
                                if (g.f[a1][ix][iy] == 2) {
                                    g.drawer.setColor(1, 1, 1, 1);
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
            }else{
                for (int ix = 0; ix < g.fx; ix++) {
                    for (int iy = 0; iy < g.fy; iy++) {
                        if (g.f[a+23][ix][iy] != -1) {
                            if (g.f[a+23][ix][iy] == 0) {
                                g.drawer.setColor(0, 0, 0, 1);
                            }
                            if (g.f[a+23][ix][iy] == 1) {
                                g.drawer.setColor(0.2f, 0.2f, 0.2f, 1);
                            }
                            if (g.f[a+23][ix][iy] == 2) {
                                g.drawer.setColor(1, 1, 1, 1);
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
                for (int ix = 0; ix < g.fx; ix++) {
                    for (int iy = 0; iy < g.fy; iy++) {
                        if (g.f[26][ix][iy] != -1) {
                            if (g.f[26][ix][iy] == 0) {
                                g.drawer.setColor(0, 0, 0, 1);
                            }
                            if (g.f[26][ix][iy] == 1) {
                                g.drawer.setColor(0.2f, 0.2f, 0.2f, 1);
                            }
                            if (g.f[26][ix][iy] == 2) {
                                g.drawer.setColor(1, 1, 1, 1);
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

}
