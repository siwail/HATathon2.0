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
	float w, h;
	@Override
	public void create () {
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		Gdx.input.setCursorCatched(true);
		random = new Random();
		w=Gdx.graphics.getWidth();
		h=Gdx.graphics.getHeight();
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

				return false;
			}

			@Override
			public boolean keyUp(int keycode) {

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

				return false;
			}

			@Override
			public boolean scrolled(float amountX, float amountY) {
				return false;
			}
		});
	}

	@Override
	public void render () {
			ScreenUtils.clear(0, 0, 0, 1);
			drawer.begin(ShapeRenderer.ShapeType.Filled);
			drawer.end();
	}

}
