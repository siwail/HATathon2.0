package com.meme.slayer;
import static com.badlogic.gdx.graphics.Color.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer drawer;
	Random random;
	FreeTypeFontGenerator generator;
	FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	BitmapFont font;
	Texture[] office = new Texture[5];
	Texture[] meme = new Texture[5];
	Texture u, he;
	Texture[] pila = new Texture[3];
	Texture cat;
	float w, h;
	float time=1;
	float cx=0, cy=0;
	float ax=-1, ay=-1;
	float mx=-1, my=-1;
	int fx=32;
	int fy=32;
	int[][][] f;
	int eq=100, pq=1000, sq=100, bq=1000;
	float step=5;
	float stepb=20;
	float gow=0,gos=0,goa=0,god=0;
	int id=0;
	int heal=6;
	Entity[] e = new Entity[eq];
	Slice[] s = new Slice[sq];
	Part[] p = new Part[pq];
	Blood[] b = new Blood[bq];
	int FX=500;
	int FY=500;
	Frame[][] F = new Frame[FX][FY];
	Frame[][] FB = new Frame[FX][FY];
	int roomq=50;
	Room[] rooms = new Room[roomq];
	int pilq=50;
	int pils=0;
	float[] pilx=new float[pilq];
	float[] pily=new float[pilq];
	float[] pilse=new float[pilq];
	int an=0;
	int and=0;
	int q=0;
	int mode=0;
	Sound sound;
	@Override
	public void create () {
		sound = Gdx.audio.newSound(Gdx.files.internal("play.mp3"));
		for(int i=0;i<5;i++) {
			office[i] = new Texture("office_"+(i+1)+".png");
			meme[i] = new Texture((i+1)+".png");
		}
		for(int i=0;i<3;i++) {
			pila[i] = new Texture("New"+(i+1)+".png");
		}
		cat = new Texture("cat.png");
		u = new Texture("u.png");
		he = new Texture("h.png");
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		random = new Random();
		w=Gdx.graphics.getWidth();
		h=Gdx.graphics.getHeight();
		FileHandle textures = Gdx.files.internal("texture.txt");
		InputStream textures_stream = textures.read();
		f = new int[52][fx][fy];
		int j=0, maxj=52;
		while (j<maxj){
			for (int ix = 0; ix < fx; ix++) {
				for (int iy = 0; iy < fy; iy++) {
					int c=-1;
					try {
						c = textures_stream.read();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					String value="";
					while((char)c!=','&&c!=-1){
						value+=""+(char)c;
						try {
							c = textures_stream.read();
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}

					f[j][ix][iy]=Integer.parseInt(value);
				}
			}
			j++;
		}
		for(int ix=0;ix<FX;ix++){
			for(int iy=0;iy<FY;iy++){
				F[ix][iy] = new Frame();
				FB[ix][iy] = new Frame();
				F[ix][iy].t=random.nextInt(13);
				FB[ix][iy].t=random.nextInt(13)+45;
			}
		}
		for (int i=0;i<eq;i++){
			e[i] = new Entity(this);
			e[i].x=i*100+100;
			int m = random.nextInt(3);
			if(m==0){
				e[i].state1=1;
			}
			if(m==1){
				e[i].state2=1;
			}
			if(m==2){
				e[i].state3=1;
			}
			e[i].enemy=true;
			e[i].id=i;
			e[i].state=3;
			e[i].t=random.nextInt(5);
		}
		int scale=50;
		int ix=200;
		int iy=200;
		for(int i=0;i<roomq;i++){
			rooms[i]= new Room(this, ix, iy, scale, random.nextInt(2));
			ix=rooms[i].exitx;
			iy=rooms[i].exity;
			q++;
		}

		for(int i=0;i<roomq;i++){
			rooms[i].generate_exit();
		}

		e[id].state=0;
		e[id].enemy=false;
		e[id].x=rooms[0].x*stepb+200;
		e[id].y=rooms[0].y*stepb+220;
		for(ix=0;ix<FX;ix++){
			F[ix][0].t=0;
		}

		//RoomGenerator generatorr = new RoomGenerator();
		//List<Room> rooms = generatorr.generateRooms(10);

		for (int i=0;i<sq;i++){
			s[i]=new Slice(this);
		}
		for (int i=0;i<bq;i++){
			b[i]=new Blood(this);
		}
		for (int i=0;i<pq;i++){
			p[i]=new Part(this);
		}

		drawer = new ShapeRenderer();
		batch = new SpriteBatch();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		parameter.size = (int)(50.0);
		font = generator.generateFont(parameter);
		font.setColor(WHITE);
		Gdx.input.setInputProcessor(new InputProcessor() {
			boolean unmove=false;
			@Override
			public boolean keyDown(int keycode) {
				Gdx.app.log(""+keycode, "");
				if(keycode==62){
					if(e[id].spaced){
						e[id].vy+=30;
					}
				}
				if(keycode==51){
					gow=3;
				}
				if(keycode==29){
					goa=-3;
				}
				if(keycode==47){
					gos=-3;
				}
				if(keycode==32){
					god=3;
				}
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				if(keycode==51){
					gow=0;
				}
				if(keycode==29){
					goa=0;
				}
				if(keycode==47){
					gos=0;
				}
				if(keycode==32){
					god=0;
				}
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				ax=screenX+cx;
				ay=h-screenY+cy;
				if(hit(screenX, h-screenY, 100, 100, 200, 100)){
					goa=-3;
					e[id].direct=-1;
				}
				if(hit(screenX, h-screenY, 100, 300, 200, 100)){
					god=3;
					e[id].direct=1;
				}
				if(hit(screenX, h-screenY, 100, 200, 300, 100)){
					if(e[id].spaced){
						e[id].vy+=30;
					}
				}



				return false;
			}
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				slay(screenX+cx, h-screenY+cy, ax, ay);
				mx=-1;
				my=-1;
				ax=-1;
				ay=-1;
				gow=0;
				goa=0;
				gos=0;
				god=0;
				return false;
			}
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				mx=screenX;
				my=h-screenY;
				gow=0;
				goa=0;
				gos=0;
				god=0;
				if(hit(screenX, h-screenY, 100, 100, 200, 100)){
					goa=-3;
					e[id].direct=-1;
				}
				if(hit(screenX, h-screenY, 100, 300, 200, 100)){
					god=3;
					e[id].direct=1;
				}
				if(hit(screenX, h-screenY, 100, 200, 300, 100)){
					if(e[id].spaced){
						e[id].vy+=30;
					}
				}
				if(hit(screenX, h-screenY, 100, 200, 100, 100)){
					gos=-3;
				}
				return false;}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				mx=screenX;
				my=h-screenY;
				if(e[id].x-cx<mx){
					e[id].direct=1;
				}else{
					e[id].direct=-1;
				}

				return false;
			}

			@Override
			public boolean scrolled(float amountX, float amountY) {
				return false;
			}
		});
	}
	public void slay(float x, float y, float ax, float ay){
		float r=(90+(float)(Math.atan2(-(y-ay), (x-ax))/Math.PI*180))%360;
		float vx=sin(r);
		float vy=cos(r);
		float ix=ax;
		float iy=ay;
		while(!hit(ix, iy, 5, x, y, 5)){
			ix+=vx;
			iy+=vy;
			for(int i=0;i<eq;i++){
				if(e[i].state!=3){
					if(hit(ix, iy, 5, e[i].x, e[i].y,  fx*step)){
						boolean access2=true;
						if(i==id) {
							access2=false;
							if(e[id].damaged<=0) {
								e[id].damaged = 50;
								heal--;
								if (heal <= 0) {
									exit();
								}
							}
						}

						boolean access=false;
						int px=0, py=0;
						for (;px < fx&&!access; px++) {
							py=0;
							for (;py < fy&&!access; py++) {
								if (f[e[i].a][px][py] != -1) {
									float p1x = e[i].x + sin(e[i].r + 45) * step/2 + sin(e[i].r) * step * px + sin(e[i].r + 90) * step * py;
									float p1y = e[i].y + cos(e[i].r + 45) * step/2 + cos(e[i].r) * step * px + cos(e[i].r + 90) * step * py;
									if(hit(p1x, p1y, step, ix, iy, 5)){
										access=true;
									}
								}
							}
						}
						if(access&&access2) {
							time/=20;
							int[][] fs = f[e[i].a];
							if(e[i].enemy){
								if(e[i].t==0) {
									fs = f[e[i].a + 23];
								}else{
									fs = f[e[i].a];
								}
							}
							set_part(px, py, i, -r+90, fs, false, 0);
							set_part(px, py, i, -r - 180+90, fs, false, 0);
							set_slice(ix, iy, r);
						}
					}
				}
			}
			for(int i=0;i<pq;i++){
				if(p[i].state!=3&&!p[i].hit){
					if(hit(ix, iy, 5, p[i].x, p[i].y,  fx*step)){
						boolean access=false;
						int px=0, py=0;
						for (;px < fx&&!access; px++) {
							py=0;
							for (;py < fy&&!access; py++) {
								if (p[i].f[px][py] != -1) {
									float p1x = p[i].x + sin(p[i].r + 45) * step/2 + sin(p[i].r) * step * px + sin(p[i].r + 90) * step * py;
									float p1y =  p[i].y + cos(p[i].r + 45) * step/2 + cos(p[i].r) * step * px + cos(p[i].r + 90) * step * py;
									if(hit(p1x, p1y, step, ix, iy, 5)){
										access=true;
									}
								}
							}
						}
						if(access) {
							time/=20;
							set_part(px, py, i, -r+90, p[i].f, true, 0);
							set_slice(ix, iy, r);
						}
					}
				}
			}
		}
		for(int i=0;i<pq;i++) {
			if (p[i].state != 3) {
				p[i].hit=false;
			}
		}
	}
	public void set_entity(float x, float y) {
		boolean access = false;
		int index = 0;
		for (int i2 = 0; i2 < eq; i2++) {
			if (e[i2].state == 3) {
				index = i2;
				access = true;
				break;
			}
		}
		if (access) {
			e[index].state=0;
			e[index].x=x;
			e[index].y=y;
		}
	}
	public void set_part(int x, int y, int i, float r, int[][] f, boolean part, float vy){
		boolean access=false;
		int index=0;
		for(int i2=0;i2<pq;i2++){
			if(p[i2].state==3){
				index=i2;
				access=true;
				break;
			}
		}
		if(access){
			if(!part) {
				p[index] = new Part(this, f, x, y, e[i].x, e[i].y, r, e[i].r, vy);
				e[i].state=3;
			}else{

				p[index] = new Part(this, f, x, y, p[i].x,p[i].y, r, p[i].r, vy);
				p[i] = new Part(this, f, x, y, p[i].x,p[i].y, r-180, p[i].r, vy);
			}


		}
	}
	public void set_slice(float x, float y, float r){
		boolean access=false;
		int index=0;
		for(int i2=0;i2<sq;i2++){
			if(s[i2].state==3){
				index=i2;
				access=true;
				break;
			}
		}
		if(access){
			s[index].x=x;
			s[index].y=y;
			s[index].s=10;
			s[index].r=r;
			s[index].state=0;
		}
	}
	public void set_blood(float x, float y, float vx, float vy, float s){
		boolean access=false;
		int index=0;
		for(int i2=0;i2<bq;i2++){
			if(b[i2].state==3){
				index=i2;
				access=true;
				break;
			}
		}
		if(access){
			b[index].x=x;
			b[index].y=y;
			b[index].vx=vx;
			b[index].vy=vy;
			b[index].s=s;
			b[index].state=0;
		}
	}
	public void exit(){
		mode=1;
		sound.play();
	}
	@Override
	public void render () {
		if (mode==0) {
			if (and == 0) {
				for (int i = 0; i < pils; i++) {
					if (hit(pilx[i], pily[i], h / 2, e[id].x, e[id].y, h / 2)) {
						float r = random.nextInt(360);
						slay(pilx[i] + sin(r) * pilse[i] / 5, pily[i] + cos(r) * pilse[i] / 5, pilx[i] - sin(r) * pilse[i] / 5, pily[i] - cos(r) * pilse[i] / 5);
					}
				}
			}
			and++;
			if (and > 4) {
				and = 0;
			}

			an++;
			if (an > 2) {
				an = 0;
			}
			time += (1 - time) / 100;
			cx += w / 2;
			cy += h / 2;
			cx += (e[id].x - cx) / 2f;
			cy += (e[id].y - cy) / 2f;
			cx -= w / 2;
			cy -= h / 2;
			for (int i = 0; i < eq; i++) {
				e[i].math();
			}
			for (int i = 0; i < pq; i++) {
				p[i].math();
			}
			for (int i = 0; i < sq; i++) {
				s[i].math();
			}
			for (int i = 0; i < bq; i++) {
				b[i].math();
			}
			ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);
			batch.begin();
			for (int i = 0; i < roomq; i++) {
				//	batch.draw(office[rooms[i].t], -cx+rooms[i].x*stepb, -cy+rooms[i].y*stepb, stepb*rooms[i].s, stepb*rooms[i].s);
			}
			batch.end();
			drawer.begin(ShapeRenderer.ShapeType.Filled);

			int minx = Math.min(Math.max((int) (cx / stepb - 1), 0), FX);
			int miny = Math.min(Math.max((int) (cy / stepb - 1), 0), FY);
			int maxx = Math.min(Math.max((int) ((cx + w) / stepb + 1), 0), FX);
			int maxy = Math.min(Math.max((int) ((cy + h) / stepb + 1), 0), FY);
			for (int ix = minx; ix < maxx; ix++) {
				for (int iy = miny; iy < maxy; iy++) {
					if (F[ix][iy].t == -1) {
						drawer.setColor(FB[ix][iy].t / 255f, FB[ix][iy].t / 255f, FB[ix][iy].t / 255f, 0);
						drawer.rect(-cx + ix * stepb, -cy + iy * stepb, stepb, stepb);
					}
				}
			}

			for (int i = 0; i < eq; i++) {
				e[i].draw();
			}
			for (int i = 0; i < bq; i++) {
				b[i].draw();
			}
			for (int i = 0; i < pq; i++) {
				p[i].draw();
			}
			for (int i = 0; i < sq; i++) {
				s[i].draw();
			}
			for (int ix = minx; ix < maxx; ix++) {
				for (int iy = miny; iy < maxy; iy++) {
					if (F[ix][iy].t != -1) {
						drawer.setColor(F[ix][iy].t/255f, F[ix][iy].t/255f, F[ix][iy].t/255f, 0);
						drawer.rect(-cx + ix * stepb, -cy + iy * stepb, stepb, stepb);
					}
				}
			}
			drawer.end();
			batch.begin();
			for (int i = 0; i < pils; i++) {
				batch.draw(pila[an], -cx + pilx[i] - pilse[i] / 2, -cy + pily[i] - pilse[i] / 2, pilse[i], pilse[i]);
			}
			batch.draw(u, 0, 0, 400, 400);
			for (int i = 0; i < heal; i++) {
				batch.draw(he, i * 100, h - 100, 100, 50);
			}
			batch.end();
		}else{
			batch.begin();
			batch.draw(cat, 0, 0, w, h);
			batch.end();
		}
	}
	public float sin(float v){
		return (float)Math.sin(v*Math.PI/180);
	}
	public float cos(float v){
		return (float)Math.cos(v*Math.PI/180);
	}
	public boolean hit(float x1, float y1, float r1, float x2, float y2, float r2) {
		float dx = Math.abs(x1 - x2);
		float dy = Math.abs(y1 - y2);
		return Math.sqrt(dx*dx+dy*dy)<=r1+r2;
	}
	public float GetVX(float x, float y, float aimx, float aimy) {
		float dx = (x - aimx);
		float dy = (y - aimy);
		float gx = 0;
		if (dy != 0 && dx != 0) {
			if (Math.abs(dx) > Math.abs(dy)) {
				gx = dx / Math.abs(dx);
			} else {
				gx = dx / Math.abs(dy);
			}
		}
		return gx;
	}

	public float GetVY(float x, float y, float aimx, float aimy) {
		float dx = (x - aimx);
		float dy = (y - aimy);
		float gy = 0;
		if (dy != 0 && dx != 0) {
			if (Math.abs(dx) > Math.abs(dy)) {
				gy = dy / Math.abs(dx);
			} else {
				gy = dy / Math.abs(dy);
			}
		}
		return gy;
	}
	public boolean act(int x, int y){
		return x<FX&&y<FY&&x>-1&&y>-1;
	}
	public void frect(int x1, int y1, int x2, int y2, int t){
		for(int ix=x1;ix<x2+1;ix++){
			for(int iy=y1;iy<y2+1;iy++){
				if(act(ix, iy)){
					F[ix][iy].t=t;
				}
			}
		}
	}

}
