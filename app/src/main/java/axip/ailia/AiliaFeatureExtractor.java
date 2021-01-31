package axip.ailia;

public class AiliaFeatureExtractor {
    public static final int DISTANCE_L2NORM = 0;

    public static native long Create(long netHandle, int format, int channel, int range, String layerName) throws AiliaException;
    public static native void Destroy(long handle);
    public static native void Compute(long handle, float[] dst, int dstSize, byte[] src, int srcStride, int srcWidth, int srcHeight, int srcFormat) throws AiliaException;
    public static native float Match(long handle, int distanceType, float[] feature1, int feature1Size, float[] feature2, int feature2Size) throws AiliaException;
}
