package axip.ailia;

public class AiliaDetectorObject {
    public static final int version = 1;

    public int category;
    public float prob;
    public float x, y;
    public float w, h;

    public AiliaDetectorObject(int category, float prob, float x, float y, float w, float h) {
        this.category = category;
        this.prob = prob;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
}
