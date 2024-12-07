package pkgSIRenderEngine;

import org.lwjgl.opengl.GL20;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SIShaderObject {
    private int programID;

    public SIShaderObject(String vertexPath, String fragmentPath) {
        String vertexSource = loadShaderSource(vertexPath);
        String fragmentSource = loadShaderSource(fragmentPath);

        int vertexShader = compileShader(GL20.GL_VERTEX_SHADER, vertexSource);
        int fragmentShader = compileShader(GL20.GL_FRAGMENT_SHADER, fragmentSource);

        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShader);
        GL20.glAttachShader(programID, fragmentShader);
        GL20.glLinkProgram(programID);
    }

    private String loadShaderSource(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load shader: " + path);
        }
    }

    private int compileShader(int type, String source) {
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, source);
        GL20.glCompileShader(shaderID);
        return shaderID;
    }

    public void use() {
        GL20.glUseProgram(programID);
    }
}
