package pkgDriver;

public class SlSPOT {
    public static final String WINDOW_TITLE = "CSC 133: Click & Kill Time!";
    public static final int POLY_OFFSET = 40;
    public static final int POLYGON_LENGTH = 100;
    public static final int POLY_PADDING = 40;
    public static final int NUM_POLY_ROWS = 9;
    public static final int NUM_POLY_COLS = 7;

    public static final int WIN_WIDTH = 2 * POLY_OFFSET + (NUM_POLY_COLS - 1) * POLY_PADDING
            + NUM_POLY_COLS * POLYGON_LENGTH;
    public static final int WIN_HEIGHT = 2 * POLY_OFFSET + (NUM_POLY_ROWS - 1) * POLY_PADDING
            + NUM_POLY_ROWS * POLYGON_LENGTH;

    public static final float FRUSTUM_LEFT = 0.0f;
    public static final float FRUSTUM_RIGHT = (float) WIN_WIDTH;
    public static final float FRUSTUM_BOTTOM = 0.0f;
    public static final float FRUSTUM_TOP = (float) WIN_HEIGHT;
    public static final float Z_NEAR = -1.0f;
    public static final float Z_FAR = 1.0f;
}