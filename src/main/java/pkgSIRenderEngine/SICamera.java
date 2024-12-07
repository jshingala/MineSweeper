package pkgSIRenderEngine;

import org.joml.Matrix4f;

public class SICamera {
    public Matrix4f projectionMatrix(int width, int height) {
        Matrix4f projection = new Matrix4f();
        projection.ortho(-width / 2f, width / 2f, -height / 2f, height / 2f, -1f, 1f);
        return projection;
    }
}
