package com.mygdx.game.Shaders;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.FlushablePool;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class worldBatch implements Disposable {
    private Camera camera;
    private RenderContext context;
    private Array<Renderable> renderables = new Array<Renderable>();
    private final RenderableSorter sorter;
    private final ShaderProvider shaderProvider;
    private final RenderablePool renderPool = new RenderablePool();
    private Environment environment;
    private static final String VERTEX = DefaultShader.getDefaultVertexShader();
    private static final String FRAGMENT = DefaultShader.getDefaultFragmentShader();

    private static worldBatch main = new worldBatch(VERTEX, FRAGMENT);


    public worldBatch(String vertexShader, String fragmentShader) {
        this.sorter = new DefaultRenderableSorter();
        this.shaderProvider = new DefaultShaderProvider(vertexShader, fragmentShader);
        this.context = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1));
    }

    public static void begin(Camera cam) {
        if(main.camera != null) throw new GdxRuntimeException("Call end() first.");
        main.camera = cam;
        main.context.begin();
    }

    public void render(final Environment environment){

    }

    public static void setEnvironment(Environment en) { main.environment = en; }

    public static <T extends RenderableProvider> void render (final Iterable<T> renderableProviders) {
        for (final RenderableProvider renderableProvider : renderableProviders)
            main.render(renderableProvider);
    }

    public static void renderG(final RenderableProvider renderableProvider) {
        main.render(renderableProvider);
    }

    //Renderable provider es una interfaz.
    private void render (final RenderableProvider renderableProvider) {
        final int offset = renderables.size;
        renderableProvider.getRenderables(renderables, renderPool);
        for (int i = offset; i < renderables.size; i++) {
            Renderable renderable = renderables.get(i);
            renderable.environment = environment;
            renderable.shader = shaderProvider.getShader(renderable);
        }
    }

    private void flush() {
        this.sorter.sort(this.camera, this.renderables);
        Shader currentShader = null;

        for(int i = 0; i < this.renderables.size; ++i) {
            final Renderable renderable = renderables.get(i);
            if (currentShader != renderable.shader) {
                if (currentShader != null) currentShader.end();
                currentShader = renderable.shader;
                currentShader.begin(this.camera, this.context);
            }
            if(currentShader != null) currentShader.render(renderable);
        }

        this.renderPool.flush();
        this.renderables.clear();
    }

    public static void end () {
        main.flush();
        main.context.end();
        main.camera = null;
    }

    @Override
    public void dispose() {

    }

    protected static class RenderablePool extends FlushablePool<Renderable> {
        @Override
        protected Renderable newObject () {
            return new Renderable();
        }

        @Override
        public Renderable obtain () {
            Renderable renderable = super.obtain();
            renderable.environment = null;
            renderable.material = null;
            renderable.meshPart.set("", null, 0, 0, 0);
            renderable.shader = null;
            renderable.userData = null;
            return renderable;
        }
    }

}
