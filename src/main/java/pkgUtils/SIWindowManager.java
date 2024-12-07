package pkgUtils;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import pkgDriver.SlSPOT;
import java.nio.DoubleBuffer;
import org.lwjgl.BufferUtils;

public class SIWindowManager {
    private long window;
    private boolean leftButtonPressed = false;
    private double lastClickX = 0, lastClickY = 0;

    public SIWindowManager(int width, int height, String title) {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create GLFW window");
        }

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        GLFW.glfwSetMouseButtonCallback(window, (windowHandle, button, action, mods) -> {
            if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                if (action == GLFW.GLFW_PRESS) {
                    leftButtonPressed = true;
                    DoubleBuffer xpos = BufferUtils.createDoubleBuffer(1);
                    DoubleBuffer ypos = BufferUtils.createDoubleBuffer(1);
                    GLFW.glfwGetCursorPos(window, xpos, ypos);
                    lastClickX = xpos.get(0);
                    lastClickY = ypos.get(0);
                }
            }
        });
    }

    public boolean isLeftMousePressed() {
        return leftButtonPressed;
    }

    public void resetMousePress() {
        leftButtonPressed = false;
    }

    public int[] getGridCoordinates() {
        int col = (int)((lastClickX - SlSPOT.POLY_OFFSET) / (SlSPOT.POLYGON_LENGTH + SlSPOT.POLY_PADDING));
        int row = (int)((lastClickY - SlSPOT.POLY_OFFSET) / (SlSPOT.POLYGON_LENGTH + SlSPOT.POLY_PADDING));
        // Flip the row coordinate
        row = 8 - row;  // 8 is ROWS - 1 (9 - 1)
        return new int[]{row, col};
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public void pollEvents() {
        GLFW.glfwPollEvents();
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public void cleanup() {
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    public long getWindowHandle() {
        return window;
    }
}