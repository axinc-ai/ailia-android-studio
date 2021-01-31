package axip.ailia;

public class AiliaDetectorModel implements AutoCloseable {
    private long detectorHandle;

    public AiliaDetectorModel(long netHandle, AiliaNetworkImageFormat format, AiliaNetworkImageChannel channel, AiliaNetworkImageRange range, int algorithm, int categoryCount, int flags) throws AiliaException {
        detectorHandle = AiliaDetector.Create(netHandle, format.getValue(), channel.getValue(), range.getValue(), algorithm, categoryCount, flags);
    }

    public void close() {
        AiliaDetector.Destroy(detectorHandle);
    }

    public void compute(byte[] src, int srcStride, int srcWidth, int srcHeight, AiliaImageFormat srcFormat, float threshold, float iou) throws AiliaException {
        AiliaDetector.Compute(detectorHandle, src, srcStride, srcWidth, srcHeight, srcFormat.getValue(), threshold, iou);
    }

    public int getObjectCount() throws AiliaException {
        return AiliaDetector.GetObjectCount(detectorHandle);
    }

    public AiliaDetectorObject getObject(int index) throws AiliaException {
        return AiliaDetector.GetObject(detectorHandle, index, AiliaDetectorObject.version);
    }

    public void setAnchors(float[] anchors) throws AiliaException {
        AiliaDetector.SetAnchors(detectorHandle, anchors, anchors.length / 2);
    }
}
