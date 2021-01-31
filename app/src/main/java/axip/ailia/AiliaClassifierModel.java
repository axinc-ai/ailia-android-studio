package axip.ailia;

public class AiliaClassifierModel implements AutoCloseable {
    private long classifierHandle;

    public AiliaClassifierModel(long netHandle, AiliaNetworkImageFormat format, AiliaNetworkImageChannel channel, AiliaNetworkImageRange range) throws AiliaException {
        classifierHandle = AiliaClassifier.Create(netHandle, format.getValue(), channel.getValue(), range.getValue());
    }

    public void close() {
        AiliaClassifier.Destroy(classifierHandle);
    }

    public void compute(byte[] src, int srcStride, int srcWidth, int srcHeight, AiliaImageFormat srcFormat, int maxClassCount) throws AiliaException {
        AiliaClassifier.Compute(classifierHandle, src, srcStride, srcWidth, srcHeight, srcFormat.getValue(), maxClassCount);
    }

    public int getClassCount() throws AiliaException {
        return AiliaClassifier.GetClassCount(classifierHandle);
    }

    public AiliaClassifierClass getClass(int index) throws AiliaException {
        return AiliaClassifier.GetClass(classifierHandle, index, AiliaClassifierClass.version);
    }
}
