package axip.ailia;

public class AiliaShape {
    public static final int version = 1;

    public int x, y, z, w;
    public int dim;

    public AiliaShape(int x, int y, int z, int w, int dim) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        this.dim = dim;
    }

    @Override
    public String toString() {
        return "Shape{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                ", dim=" + dim +
                '}';
    }
}
