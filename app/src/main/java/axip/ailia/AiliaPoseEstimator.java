package axip.ailia;

public class AiliaPoseEstimator
{
    public static native long Create(long netHandle, int algorithm) throws AiliaException;
    public static native void Destroy(long handle);
    public static native void Compute(long handle, byte[] src, int srcStride, int srcWidth, int srcHeight, int srcFormat) throws AiliaException;
    public static native int GetObjectCount(long handle) throws AiliaException;
    public static native AiliaPoseEstimatorObjectPose GetObjectPose(long handle, int objIndex, int version) throws AiliaException;
    public static native AiliaPoseEstimatorObjectFace GetObjectFace(long handle, int objIndex, int version) throws AiliaException;
    public static native AiliaPoseEstimatorObjectUpPose GetObjectUpPose(long handle, int objIndex, int version) throws AiliaException;
}