package pkgUtils;

public class SITime {
    private long lastTime;
    private float deltaTime;

    public SITime() {
        reset();
    }

    public void reset() {
        lastTime = System.nanoTime();
        deltaTime = 0.0f;
    }

    public void update() {
        long currentTime = System.nanoTime();
        deltaTime = (currentTime - lastTime) / 1_000_000_000.0f;
        lastTime = currentTime;
    }

    public float getDeltaTime() {
        return deltaTime;
    }
}
