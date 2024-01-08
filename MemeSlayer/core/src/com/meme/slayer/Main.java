package com.meme.slayer;
import static com.badlogic.gdx.graphics.Color.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
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
	float w, h;
	float time=1;
	float cx=0, cy=0;
	float ax=-1, ay=-1;
	float mx=-1, my=-1;
	int fx=32;
	int fy=32;
	int[][][] f;
	int eq=20, pq=1000, sq=100, bq=1000;
	float step=5;
	float stepb=20;
	float gow=0,gos=0,goa=0,god=0;
	int id=0;
	Entity[] e = new Entity[eq];
	Slice[] s = new Slice[sq];
	Part[] p = new Part[pq];
	Blood[] b = new Blood[bq];
	int FX=200;
	int FY=200;
	Frame[][] F = new Frame[FX][FY];
	@Override
	public void create () {
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		random = new Random();
		w=Gdx.graphics.getWidth();
		h=Gdx.graphics.getHeight();
		FileHandle textures = Gdx.files.internal("texture.txt");
		InputStream textures_stream = textures.read();
		f = new int[28][fx][fy];
		int j=0, maxj=28;
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
				F[ix][iy].t=-1;
				if(iy<5){
					F[ix][iy].t=0;
				}
			}
		}
		RoomGenerator generatorr = new RoomGenerator();
		List<Room> rooms = generatorr.generateRooms(10);
		for (int i=0;i<eq;i++){
			e[i]=new Entity(this);
			e[i].x=i*100;
			e[i].state=0;
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
			e[i].enemy=random.nextBoolean();
		}
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
				if(keycode==51){
					gow=1;
				}
				if(keycode==29){
					goa=-1;
				}
				if(keycode==47){
					gos=-1;
				}
				if(keycode==32){
					god=1;
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
				ax=screenX;
				ay=h-screenY;
				return false;
			}
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				slay(screenX, h-screenY);
				mx=-1;
				my=-1;
				ax=-1;
				ay=-1;
				return false;
			}
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				mx=screenX;
				my=h-screenY;
				return false;}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {

				return false;
			}

			@Override
			public boolean scrolled(float amountX, float amountY) {
				return false;
			}
		});
	}
	public void slay(float x, float y){
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
						if(access) {
							time/=20;
							int[][] fs = f[e[i].a];
							if(e[i].enemy){
								fs=f[e[i].a+23];
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
	@Override
	public void render () {
		time+=(1-time)/100;
		cx+=w/2;
		cy+=h/2;
		cx+=(e[id].x-cx)/20f;
		cy+=(e[id].y-cy)/20f;
		cx-=w/2;
		cy-=h/2;
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
		drawer.begin(ShapeRenderer.ShapeType.Filled);
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
		for(int ix=0;ix<FX;ix++){
			for(int iy=0;iy<FY;iy++){
				if(F[ix][iy].t!=-1) {
					drawer.setColor(0, 0, 0, 0);
					drawer.rect(-cx+ix*stepb, -cy+iy*stepb, stepb, stepb);
				}
			}
		}
		drawer.end();
		batch.begin();
		batch.end();
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
}
