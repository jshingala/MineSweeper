package pkgSIRenderEngine;

import pkgMinesweeperBackend.MinesweeperGrid;
import pkgMinesweeperBackend.Cell;
import pkgDriver.SlSPOT;
import static org.lwjgl.opengl.GL11.*;

public class SIRenderer {
    private SITexture pikachuTexture;
    private SITexture mineTexture;

    public SIRenderer() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(SlSPOT.FRUSTUM_LEFT, SlSPOT.FRUSTUM_RIGHT,
                SlSPOT.FRUSTUM_BOTTOM, SlSPOT.FRUSTUM_TOP,
                SlSPOT.Z_NEAR, SlSPOT.Z_FAR);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        pikachuTexture = new SITexture("src/main/java/assets/images/Pikachu1.png");
        mineTexture = new SITexture("src/main/java/assets/images/MineBomb_2.PNG");

        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
    }

    public void render(MinesweeperGrid grid) {
        glClear(GL_COLOR_BUFFER_BIT);

        float cellSize = SlSPOT.POLYGON_LENGTH;
        Cell[][] cells = grid.getGrid();
        int rows = cells.length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                float x = SlSPOT.POLY_OFFSET + j * (cellSize + SlSPOT.POLY_PADDING);
                float y = SlSPOT.POLY_OFFSET + (rows - 1 - i) * (cellSize + SlSPOT.POLY_PADDING);

                Cell cell = cells[i][j];

                if (cell.isRevealed() || grid.isGameOver()) {
                    // Enable texturing for revealed cells
                    glEnable(GL_TEXTURE_2D);
                    if (cell.isMine()) {
                        mineTexture.bind();
                    } else {
                        pikachuTexture.bind();
                    }

                    glBegin(GL_QUADS);
                    glTexCoord2f(0, 1); glVertex2f(x, y);
                    glTexCoord2f(1, 1); glVertex2f(x + cellSize, y);
                    glTexCoord2f(1, 0); glVertex2f(x + cellSize, y + cellSize);
                    glTexCoord2f(0, 0); glVertex2f(x, y + cellSize);
                    glEnd();
                } else {
                    // Disable texturing for unrevealed cells
                    glDisable(GL_TEXTURE_2D);
                    // Draw a gray square for unrevealed cells
                    glColor3f(0.5f, 0.5f, 0.5f);  // Gray color
                    glBegin(GL_QUADS);
                    glVertex2f(x, y);
                    glVertex2f(x + cellSize, y);
                    glVertex2f(x + cellSize, y + cellSize);
                    glVertex2f(x, y + cellSize);
                    glEnd();

                    // Draw darker border
                    glColor3f(0.3f, 0.3f, 0.3f);  // Darker gray for border
                    glBegin(GL_LINE_LOOP);
                    glVertex2f(x, y);
                    glVertex2f(x + cellSize, y);
                    glVertex2f(x + cellSize, y + cellSize);
                    glVertex2f(x, y + cellSize);
                    glEnd();
                }

                // Reset color and re-enable texturing
                glColor3f(1.0f, 1.0f, 1.0f);
                glEnable(GL_TEXTURE_2D);
            }
        }
    }

    public void cleanup() {
        if (pikachuTexture != null) pikachuTexture.cleanup();
        if (mineTexture != null) mineTexture.cleanup();
    }
}