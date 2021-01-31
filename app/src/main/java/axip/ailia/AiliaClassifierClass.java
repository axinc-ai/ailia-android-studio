package axip.ailia;

public class AiliaClassifierClass {
    public static final int version = 1;

    public int category;
    public float prob;

    AiliaClassifierClass(int category, float prob) {
        this.category = category;
        this.prob = prob;
    }
}
