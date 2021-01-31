package axip.ailia;

public class AiliaFeatureExtractorModel implements AutoCloseable {
    private static final int SIZEOF_FLOAT = 4;

    private long fextractorHandle;

    public AiliaFeatureExtractorModel(long netHandle, AiliaNetworkImageFormat format, AiliaNetworkImageChannel channel, AiliaNetworkImageRange range, String layerName) throws AiliaException {
        fextractorHandle = AiliaFeatureExtractor.Create(netHandle, format.getValue(), channel.getValue(), range.getValue(), layerName);
    }

    public void close() {
        AiliaFeatureExtractor.Destroy(fextractorHandle);
    }

    public void compute(float[] dst, byte[] src, int srcStride, int srcWidth, int srcHeight, AiliaImageFormat srcFormat) throws AiliaException {
        AiliaFeatureExtractor.Compute(fextractorHandle, dst, dst.length * SIZEOF_FLOAT, src, srcStride, srcWidth, srcHeight, srcFormat.getValue());
    }

    public float match(int distanceType, float[] feature1, float[] feature2) throws AiliaException {
        return AiliaFeatureExtractor.Match(fextractorHandle, distanceType, feature1, feature1.length * SIZEOF_FLOAT, feature2, feature2.length * SIZEOF_FLOAT);
    }
}
