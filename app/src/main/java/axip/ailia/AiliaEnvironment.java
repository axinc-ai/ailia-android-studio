package axip.ailia;

public class AiliaEnvironment {
    public static final int version = 2;

    public static final int TYPE_CPU   = 0;
    public static final int TYPE_BLAS  = 1;
    public static final int TYPE_GPU   = 2;

    public static final int BACKEND_NONE = 0;
    public static final int BACKEND_AMP  = 1;
    public static final int BACKEND_CUDA = 2;
    public static final int BACKEND_MPS  = 3;
    public static final int BACKEND_RENDERSCRIPT = 4;
    public static final int BACKEND_OPENCL = 5;

    public static final int PROPERTY_NORMAL	= 0;
    public static final int PROPERTY_LOWPOWER = 1;
    public static final int PROPERTY_FP16 = 2;

    public int id;
    public int type;
    public String name;
    public int backend;
    public int props;

    public AiliaEnvironment(int id, int type, String name, int backend, int props) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.backend = backend;
        this.props = props;
    }
}
