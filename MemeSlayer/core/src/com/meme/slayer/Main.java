package com.meme.slayer;
import static com.badlogic.gdx.graphics.Color.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.Random;
public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer drawer;
	Random random;
	FreeTypeFontGenerator generator;
	FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	BitmapFont font;
	int FX=500;
	int FY=500;
	int FZ=51;
	float step=1f;
	float q=0.5f;
	float d=0.5f;
	int m = 200;
	float mx=0, my=0;
	float lx=0, ly=0;
	float lr=0, lrz=0;
	Block[][][] F = new Block[FX][FY][FZ];
	float w, h;
	float dif=3;
	float loading_state = 0;
	Person[] p = new Person[2];

	@Override
	public void create () {
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		Gdx.input.setCursorCatched(true);
		random = new Random();
		w=Gdx.graphics.getWidth();
		h=Gdx.graphics.getHeight();
		Thread loading = new Thread() {
			@Override
			public void run() {
				for (int ix = 0; ix < FX; ix++) {
					loading_state = ix / FX;
					for (int iy = 0; iy < FY; iy++) {
						for (int iz = 0; iz < FZ; iz++) {
							int t = 0;
							int r = 100 + random.nextInt(3) - 1;
							int g = 120 + random.nextInt(3) - 1;
							int b = 150 + random.nextInt(3) - 1;
							if (ix == 0 || iy == 0 || ix == FX - 1 || iy == FY - 1) {
								t = 1;
							}
							if (iz == 0) {
								t = 1;
								r = 80 + random.nextInt(3) - 1;
								g = 75 + random.nextInt(3) - 1;
								b = 50 + random.nextInt(3) - 1;
							}
							if (iz == FZ - 1) {
								t = 1;
								r = 220;
								g = 220;
								b = 250;
							}
							F[ix][iy][iz] = new Block(t, r, g, b);
						}
					}
				}
				loading_state=1;
			}

		};
		loading.start();
		p[0]=new Person();
		p[0].x=FX/2f+25;
		p[0].y=FX/2f+25;
		p[0].z=5*step*7;
		p[0].lx=p[0].x;
		p[0].ly=p[0].y;
		p[0].lz=p[0].z;
		lr=p[0].r;
		lrz=p[0].rz;
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
				Gdx.app.log("", ""+keycode);
				if(keycode==51){
					p[0].gow=1;
					p[0].lastbreath_dir*=-1;
					p[0].breath_dir=1*p[0].speed*p[0].lastbreath_dir;
				}
				if(keycode==29){
					p[0].goa=-1;
				}
				if(keycode==47){
					p[0].gos=-1;
					p[0].lastbreath_dir*=-1;
					p[0].breath_dir=1*p[0].speed*p[0].lastbreath_dir;
				}
				if(keycode==32){
					p[0].god=1;
				}
				if(keycode==59){
					p[0].speed=5;
				}
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				if(keycode==51){
					p[0].gow=0;
				}
				if(keycode==29){
					p[0].goa=0;
				}
				if(keycode==47){
					p[0].gos=0;
				}
				if(keycode==32){
					p[0].god=0;
				}
				if(keycode==59){
					p[0].speed=2;
				}
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				//p[0].rz=lrz+(my-h/2)/10f;
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				if(!unmove) {
					lx = mx;
					ly = my;
					mx = screenX;
					my = screenY;
					p[0].r = lr + (mx - w / 2) / 10f;
					p[0].rz = lrz + (my - h / 2) / 10f;
					dif += 1.1f;

					if (p[0].rz > 120) {
						p[0].rz = 120;

						Gdx.input.setCursorPosition((int)mx, (int)h-200);

					}

					if (p[0].rz < 60) {
						p[0].rz = 60;

						Gdx.input.setCursorPosition((int)mx, (int)200);

					}
				}else{
					unmove=!unmove;
				}
				return false;
			}

			@Override
			public boolean scrolled(float amountX, float amountY) {
				return false;
			}
		});
	}
	public void fline(int x1, int y1, int z1, int x2, int y2, int z2, int t, int r, int g, int b){
		int x=x2, y=y2, z=z2;
		if(x1>x2){
			x=x1;
			x1=x2;
		}
		if(y1>y2){
			y=y1;
			y1=y2;
		}
		if(z1>z2){
			z=z1;
			z1=z2;
		}
		for(int ix=x1;ix<x;ix++){
			for(int iy=y1;iy<y;iy++){
				for(int iz=z1;iz<z;iz++){
					if(act(ix, iy, iz)){
						F[ix][iy][iz].t=t;
						F[ix][iy][iz].r=r+random.nextInt(11)-5;
						F[ix][iy][iz].g=g+random.nextInt(11)-5;
						F[ix][iy][iz].b=b+random.nextInt(11)-5;
					}
				}
			}
		}
	}
	public void light(float q, float x, float y, float z, float l, float f){
		for (float i=0;i<360;i+=0.5f){
			for (float i2=-f/2;i2<f/2;i2+=0.5f){
				float s=0;
				float ix=x;
				float iz=z;
				float iy=y;
				float dx=(float)Math.sin(i*Math.PI/180f)*i2/f;
				float dy=(float)Math.cos(i*Math.PI/180f)*i2/f;
				float dz=-1f/q;
				while (s<step*FX*FY){
					s+=1/q;
					ix+=dx;
					iy+=dy;
					iz+=dz;
					int px=(int)Math.floor(ix/step);
					int py=(int)Math.floor(iy/step);
					int pz=(int)Math.floor(iz/step);
					if(!act(px,py,pz)){
						break;
					}
					if (F[px][py][pz].t==1||F[px][py][pz].t==3||F[px][py][pz].t==4){
						F[px][py][pz].r+=l;
						F[px][py][pz].g+=l;
						F[px][py][pz].b+=l;
						break;
					}
					if (F[px][py][pz].t==0){
						F[px][py][pz].l+=l;
						F[px][py][pz].t=2;
					}
				}
			}


		}
	}

	@Override
	public void render () {
		if(loading_state>=1) {
			if (dif > 3) {
				dif = 3;
			}
			if (dif > 1) {
				dif -= 1;
			}
			if (p[0].gow == 1) {
				p[0].vx = (float) Math.sin(p[0].r * Math.PI / 180) * p[0].speed/2f;
				p[0].vy = (float) Math.cos(p[0].r * Math.PI / 180) * p[0].speed/2f;
				p[0].breath += p[0].breath_dir;
				if (p[0].breath < -10 || p[0].breath > 10) {
					p[0].breath_dir *= -1;
				}
				dif += p[0].speed / 3.5f;

			} else {
				if (p[0].gos == -1) {
					p[0].vx = -(float) Math.sin(p[0].r * Math.PI / 180) * p[0].speed / 2f/2f;
					p[0].vy = -(float) Math.cos(p[0].r * Math.PI / 180) * p[0].speed / 2f/2f;
					p[0].breath += p[0].breath_dir;
					if (p[0].breath < -10 || p[0].breath > 10) {
						p[0].breath_dir *= -1;
					}
					dif += p[0].speed / 3.5f;
				}
			}
			if (p[0].gow == 0 && p[0].gos == 0) {
				p[0].vx = 0;
				p[0].vy = 0;
				p[0].breath_dir = 0;
				p[0].breath += (-p[0].breath) / 5;
				//p[0].rz += (90 - p[0].rz) / 5;
			}
			p[0].lx=p[0].x;
			p[0].ly=p[0].y;
			p[0].x += p[0].vx;
			p[0].y += p[0].vy;
			p[0].z += p[0].vz + p[0].breath_dir / 5f;
			p[0].rz += p[0].breath_dir / 10;
			if(!act((int)(p[0].x),(int)(p[0].y),(int)(p[0].z))||F[(int)(p[0].x)][(int)(p[0].y)][(int)(p[0].z)].t==1||F[(int)(p[0].x)][(int)(p[0].y)][(int)(p[0].z)].t==3){
				p[0].x=p[0].lx;
				p[0].y=p[0].ly;
				p[0].z=p[0].lz;
			}


			ScreenUtils.clear(0, 0, 0, 1);
			drawer.begin(ShapeRenderer.ShapeType.Filled);
			float s, ix, iy, iz;
			float x;
			float y;
			float z;
			int px=0, py=0, pz=0, lpx=0, lpy=0, lpz=0;
			float vx, vy, vz;
			float posx=0, posy=0;
			float i, i3, iz2, i2 = 0, r, g, b, l;
			for (i = p[0].r - p[0].f / 2; i < p[0].r + p[0].f / 2; i += q) {
				posx=i2;
				i3 = 0;
				for (iz2 = p[0].rz - p[0].f / 2; iz2 < p[0].rz + p[0].f / 2; iz2 += q) {
					posy=i3;
					s = 0;
					l=0;
					r = 1 + random.nextInt(Math.max((int) dif * 5, 1)) - dif * 5 / 2f;
					g = 1 + random.nextInt(Math.max((int) dif * 5, 1)) - dif * 5 / 2f;
					b = 1 + random.nextInt(Math.max((int) dif * 5, 1)) - dif * 5 / 2f;
					x = p[0].x + (random.nextInt(Math.max((int) dif, 1)) - dif / 2f)/5f;
					y = p[0].y + (random.nextInt(Math.max((int) dif, 1)) - dif / 2f)/5f;
					z = p[0].z + (random.nextInt(Math.max((int) dif / 2, 1)) - dif / 4f)/5f;
					vx = (float) Math.sin(i * Math.PI / 180)*d;
					vy = (float) Math.cos(i * Math.PI / 180)*d;
					vz =((float) Math.cos(iz2 * Math.PI / 180) - ((i2 - 45) * p[0].breath) / 10000f)*d-0.25f;
					ix=x;
					iy=y;
					iz=z;
					while (s<m){
						s+=d;
						ix += vx;
						iy += vy;
						iz += vz+0.0075f*s;
						px = (int) ix;
						py = (int) iy;
						pz = (int) iz;
						if(act(px,py,pz)) {
							if (F[px][py][pz].t == 2) {
								l += F[px][py][pz].l * d;
								continue;
							}
							if (F[px][py][pz].t != 0) {
								r += F[px][py][pz].r;
								g += F[px][py][pz].g;
								b += F[px][py][pz].b;
								break;
							}
						}else{
							break;
						}
					}
					r *= (1f - (s) / m);
					g *= (1f - (s) / m);
					b *= (1f - (s) / m);
					r+=l;
					g+=l;
					b+=l;
					drawer.setColor(r / 255f, g / 255f, b / 255f, 1);
					drawer.rect(w / p[0].f * posx, h - h / p[0].f * posy, w / p[0].f * q, h / p[0].f * q);

					i3 += q;
				}
				i2 += q;
			}
			drawer.end();
		}else{
			ScreenUtils.clear(0, 0, 0, 1);
			drawer.begin(ShapeRenderer.ShapeType.Filled);
			drawer.setColor(WHITE);
			drawer.rect(25, h/2-25, (w-50), 50);
			drawer.setColor(BLACK);
			drawer.rect(27, h/2-23, (w-54), 46);
			drawer.setColor(WHITE);
			drawer.rect(30, h/2-20, (w-60)*loading_state, 40);
			drawer.end();
			batch.begin();
			font.draw(batch, (loading_state*100)+"%", w/2-50, h/2+75);
			batch.end();
		}
	}
	public boolean act(int x, int y, int z){
		return (x<FX&&y<FY&&z<FZ&&x>-1&&y>-1&&z>-1);
	}
}
