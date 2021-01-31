package axip.ailia;

public class AiliaClassifier {
    public static native long Create(long netHandle, int format, int channel, int range) throws AiliaException;
    public static native void Destroy(long handle);
    public static native void Compute(long handle, byte[] src, int srcStride, int srcWidth, int srcHeight, int srcFormat, int maxClassCount) throws AiliaException;
    public static native int GetClassCount(long handle) throws AiliaException;
    public static native AiliaClassifierClass GetClass(long handle, int clsIdx, int version) throws AiliaException;
}
