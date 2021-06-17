package axip.ailia;

public class AiliaDetectorObject {
    public static final int version = 1;
	/**
	 * Object category number (0 to category_count-1)
	 */
    public int category;
	/**
	 * Estimated probability (0 to 1)
	 */
    public float prob;
	/**
	 * Position at the top left (1 for the image width)
	 */
    public float x, y;
	/**
	 * Size (1 for the width of the image, negative numbers not allowed)
	 */
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
