package axip.ailia;

public class AiliaDetector {
    public static final int ALGORITHM_YOLOV1 = 0;
    public static final int ALGORITHM_YOLOV2 = 1;
    public static final int ALGORITHM_YOLOV3 = 2;
    public static final int ALGORITHM_SSD = 8;

    public static native long Create(long netHandle, int format, int channel, int range, int algorithm, int category_count, int flags) throws AiliaException;
    public static native void Destroy(long handle);
    public static native void Compute(long handle, byte[] src, int srcStride, int srcWidth, int srcHeight, int srcFormat, float threshold, float iou) throws AiliaException;
    public static native int GetObjectCount(long handle) throws AiliaException;
    public static native AiliaDetectorObject GetObject(long handle, int objIdx, int version) throws AiliaException;
    public static native void SetAnchors(long handle, float[] anchors, int anchorsCount) throws AiliaException;
    public static native void SetInputShape(long handle, int input_width, int input_height) throws AiliaException;
}
