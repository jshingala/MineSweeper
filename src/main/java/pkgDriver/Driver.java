package pkgDriver;

import pkgMinesweeperGrid.MinesweeperGrid;
import pkgSIRenderEngine.SIRenderer;
import pkgUtils.SIWindowManager;
import pkgUtils.SITime;

public class Driver {
    private SIRenderer renderer;
    private SIWindowManager window;
    private MinesweeperGrid grid;
    private SITime timer;

    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.run(SlSPOT.WIN_WIDTH, SlSPOT.WIN_HEIGHT);
    }

    void run(int winWidth, int winHeight) {
        try {
            // Initialize components
            window = new SIWindowManager(winWidth, winHeight, SlSPOT.WINDOW_TITLE);
            grid = new MinesweeperGrid();
            renderer = new SIRenderer();
            timer = new SITime();

            // Main game loop
            while (!window.shouldClose()) {
                // Handle window events
                window.pollEvents();

                // Handle mouse input
                if (window.isLeftMousePressed()) {
                    int[] gridPos = window.getGridCoordinates();
                    if (gridPos[0] >= 0 && gridPos[1] >= 0 &&
                            gridPos[0] < SlSPOT.NUM_POLY_ROWS &&
                            gridPos[1] < SlSPOT.NUM_POLY_COLS) {
                        grid.handleClick(gridPos[0], gridPos[1]);
                    }
                    window.resetMousePress();
                }

                // Update and render
                timer.update();
                renderer.render(grid);
                window.swapBuffers();
            }
        } catch (Exception e) {
            System.err.println("Error in game loop: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    private void cleanup() {
        try {
            if (renderer != null) {
                renderer.cleanup();
            }
            if (window != null) {
                window.cleanup();
            }
        } catch (Exception e) {
            System.err.println("Error during cleanup: " + e.getMessage());
            e.printStackTrace();
        }
    }
}