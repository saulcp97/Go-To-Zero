package com.mygdx.game.Shaders;

import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.attributes.DirectionalLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.PointLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.SpotLightsAttribute;
import com.badlogic.gdx.graphics.g3d.environment.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class worldEnvironment extends Attributes {
    /** @deprecated Experimental, likely to change, do not use! */


    public ShadowMap shadowMap;

	public worldEnvironment () {

    }

    public worldEnvironment add (final BaseLight... lights) {
        for (final BaseLight light : lights)
            add(light);
        return this;
    }

    public worldEnvironment add (final Array<BaseLight> lights) {
        for (final BaseLight light : lights)
            add(light);
        return this;
    }

    public worldEnvironment add (BaseLight light) {
        if (light instanceof DirectionalLight)
            add((DirectionalLight)light);
        else if (light instanceof PointLight) {
            add((PointLight)light);
        } else if (light instanceof SpotLight)
            add((SpotLight)light);
        else
            throw new GdxRuntimeException("Unknown light type");
        return this;
    }

    public worldEnvironment add (DirectionalLight light) {
        DirectionalLightsAttribute dirLights = ((DirectionalLightsAttribute)get(DirectionalLightsAttribute.Type));
        if (dirLights == null) set(dirLights = new DirectionalLightsAttribute());
        dirLights.lights.add(light);
        return this;
    }

    public worldEnvironment add (PointLight light) {
        PointLightsAttribute pointLights = ((PointLightsAttribute)get(PointLightsAttribute.Type));
        if (pointLights == null) set(pointLights = new PointLightsAttribute());
        pointLights.lights.add(light);
        return this;
    }

    public worldEnvironment add (SpotLight light) {
        SpotLightsAttribute spotLights = ((SpotLightsAttribute)get(SpotLightsAttribute.Type));
        if (spotLights == null) set(spotLights = new SpotLightsAttribute());
        spotLights.lights.add(light);
        return this;
    }

    public worldEnvironment remove (final BaseLight... lights) {
        for (final BaseLight light : lights)
            remove(light);
        return this;
    }

    public worldEnvironment remove (final Array<BaseLight> lights) {
        for (final BaseLight light : lights)
            remove(light);
        return this;
    }

    public worldEnvironment remove (BaseLight light) {
        if (light instanceof DirectionalLight)
            remove((DirectionalLight)light);
        else if (light instanceof PointLight)
            remove((PointLight)light);
        else if (light instanceof SpotLight)
            remove((SpotLight)light);
        else
            throw new GdxRuntimeException("Unknown light type");
        return this;
    }

    public worldEnvironment remove (DirectionalLight light) {
        if (has(DirectionalLightsAttribute.Type)) {
            DirectionalLightsAttribute dirLights = ((DirectionalLightsAttribute)get(DirectionalLightsAttribute.Type));
            dirLights.lights.removeValue(light, false);
            if (dirLights.lights.size == 0)
                remove(DirectionalLightsAttribute.Type);
        }
        return this;
    }

    public worldEnvironment remove (PointLight light) {
        if (has(PointLightsAttribute.Type)) {
            PointLightsAttribute pointLights = ((PointLightsAttribute)get(PointLightsAttribute.Type));
            pointLights.lights.removeValue(light, false);
            if (pointLights.lights.size == 0)
                remove(PointLightsAttribute.Type);
        }
        return this;
    }

    public worldEnvironment remove (SpotLight light) {
        if (has(SpotLightsAttribute.Type)) {
            SpotLightsAttribute spotLights = ((SpotLightsAttribute)get(SpotLightsAttribute.Type));
            spotLights.lights.removeValue(light, false);
            if (spotLights.lights.size == 0)
                remove(SpotLightsAttribute.Type);
        }
        return this;
    }
}



