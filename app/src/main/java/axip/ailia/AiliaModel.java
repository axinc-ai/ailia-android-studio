package axip.ailia;

import java.util.LinkedList;
import java.util.List;

public class AiliaModel implements AutoCloseable {
    private long netHandle;

    static public List<AiliaEnvironment> getEnvironments() throws AiliaException {
        int len = Ailia.GetEnvironmentCount();
        LinkedList<AiliaEnvironment> envs = new LinkedList<>();
        for (int i = 0; i < len; i++) {
            envs.push(Ailia.GetEnvironment(i, AiliaEnvironment.version));
        }
        return envs;
    }

    public AiliaModel(int envId, int numThread, byte[] streamData, byte[] weightData) throws AiliaException {
        netHandle = Ailia.Create(envId, numThread);

        Ailia.OpenStreamMem(netHandle, streamData, streamData.length);
        Ailia.OpenWeightMem(netHandle, weightData, weightData.length);
    }

    public AiliaModel(int envId, int numThread, String streamPath, String weightPath) throws AiliaException {
        netHandle = Ailia.Create(envId, numThread);

        Ailia.OpenStreamFile(netHandle, streamPath);
        Ailia.OpenWeightFile(netHandle, weightPath);
    }

    public AiliaClassifierModel createClassifier(AiliaNetworkImageFormat format, AiliaNetworkImageChannel channel, AiliaNetworkImageRange range) throws AiliaException {
        return new AiliaClassifierModel(netHandle, format, channel, range);
    }

    public AiliaDetectorModel createDetector(AiliaNetworkImageFormat format, AiliaNetworkImageChannel channel, AiliaNetworkImageRange range, int algorithm, int categoryCount, int flags) throws AiliaException {
        return new AiliaDetectorModel(netHandle, format, channel, range, algorithm, categoryCount, flags);
    }

    public AiliaFeatureExtractorModel createFeatureExtractor(AiliaNetworkImageFormat format, AiliaNetworkImageChannel channel, AiliaNetworkImageRange range, String layerName) throws AiliaException {
        return new AiliaFeatureExtractorModel(netHandle, format, channel, range, layerName);
    }

    public AiliaPoseEstimatorModel createPoseEstimator(int algorithm) throws AiliaException {
        return new AiliaPoseEstimatorModel(netHandle, algorithm);
    }

    public void Predict(float[] dest, int destSize, float[] src, int srcSize) throws AiliaException {
        Ailia.Predict(netHandle,dest,destSize,src,srcSize);
    }

    public void setInputShape(AiliaShape shape) throws AiliaException {
        Ailia.SetInputShape(netHandle, shape, AiliaShape.version);
    }
    public AiliaShape getInputShape() throws AiliaException {
        return Ailia.GetInputShape(netHandle, AiliaShape.version);
    }
    public AiliaShape getOutputShape() throws AiliaException {
        return Ailia.GetOutputShape(netHandle, AiliaShape.version);
    }

    public void setInputBlobShape(AiliaShape shape, int blobIndex, int version) throws AiliaException {
        Ailia.SetInputBlobShape(netHandle, shape, blobIndex, version);
    }

    public int getInputBlobCount() throws AiliaException {
        return Ailia.GetInputBlobCount(netHandle);
    }
    public int getBlobIndexByInputIndex(int inputBlobIndex) throws AiliaException {
        return Ailia.GetBlobIndexByInputIndex(netHandle, inputBlobIndex);
    }
    public int getOutputBlobCount() throws AiliaException {
        return Ailia.GetOutputBlobCount(netHandle);
    }
    public int getBlobIndexByOutputIndex(int outputBlobIndex) throws AiliaException {
        return Ailia.GetBlobIndexByOutputIndex(netHandle, outputBlobIndex);
    }

    public void close() {
        Ailia.Destroy(netHandle);
    }

    public long getHandle() {
        return netHandle;
    }
}
