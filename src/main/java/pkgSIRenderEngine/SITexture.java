package pkgSIRenderEngine;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

public class SITexture {
    private int textureId;

    public SITexture(String path) {
        textureId = loadTexture(path);
    }

    private int loadTexture(String path) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            int width = image.getWidth();
            int height = image.getHeight();

            int[] pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);

            ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);

            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    int pixel = pixels[y * width + x];
                    buffer.put((byte) ((pixel >> 16) & 0xFF));  // Red
                    buffer.put((byte) ((pixel >> 8) & 0xFF));   // Green
                    buffer.put((byte) (pixel & 0xFF));          // Blue
                    buffer.put((byte) ((pixel >> 24) & 0xFF));  // Alpha
                }
            }
            buffer.flip();

            int textureID = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

            // Set texture parameters
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0,
                    GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

            return textureID;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load texture: " + path, e);
        }
    }

    public void bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
    }

    public void cleanup() {
        GL11.glDeleteTextures(textureId);
    }
}