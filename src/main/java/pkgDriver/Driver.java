package pkgDriver;

import pkgMinesweeperGrid.MinesweeperGrid;
import pkgSIRenderEngine.SIRenderer;
import pkgUtils.SIWindowManager;
import pkgUtils.SITime;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    private SIRenderer renderer;
    private SIWindowManager window;
    private MinesweeperGrid grid;
    private SITime timer;

    public static void main(String[] args) {
        if (System.getProperty("os.name").toLowerCase().contains("mac") &&
                !Boolean.getBoolean("java.awt.headless")) {

            // Only attempt restart once
            if (!Boolean.getBoolean("restartedOnce")) {
                System.setProperty("restartedOnce", "true");
                try {
                    List<String> command = new ArrayList<>();
                    command.add("java");  // Use 'java' command directly
                    command.add("-XstartOnFirstThread");
                    command.add("-cp");
                    command.add(System.getProperty("java.class.path"));
                    command.add(Driver.class.getName());

                    ProcessBuilder pb = new ProcessBuilder(command);
                    pb.inheritIO();
                    Process process = pb.start();
                    System.exit(process.waitFor());
                } catch (Exception e) {
                    System.err.println("Failed to restart JVM: " + e.getMessage());
                    e.printStackTrace();
                    System.exit(1);
                }
                return;
            }
        }

        Driver driver = new Driver();
        driver.run(SlSPOT.WIN_WIDTH, SlSPOT.WIN_HEIGHT);
    }

    void run(int winWidth, int winHeight) {
        try {
            window = new SIWindowManager(winWidth, winHeight, SlSPOT.WINDOW_TITLE);
            grid = new MinesweeperGrid();
            renderer = new SIRenderer();
            timer = new SITime();

            while (!window.shouldClose()) {
                window.pollEvents();

                if (window.isLeftMousePressed()) {
                    int[] gridPos = window.getGridCoordinates();
                    if (gridPos[0] >= 0 && gridPos[1] >= 0 &&
                            gridPos[0] < SlSPOT.NUM_POLY_ROWS &&
                            gridPos[1] < SlSPOT.NUM_POLY_COLS) {
                        grid.handleClick(gridPos[0], gridPos[1]);
                    }
                    window.resetMousePress();
                }

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
            if (renderer != null) renderer.cleanup();
            if (window != null) window.cleanup();
        } catch (Exception e) {
            System.err.println("Error during cleanup: " + e.getMessage());
            e.printStackTrace();
        }
    }
}