package axip.ailia;

public class AiliaPoseEstimatorModel implements AutoCloseable
{
    public static final int  ALGORITHM_ACCULUS_POSE = 0;
    public static final int  ALGORITHM_ACCULUS_FACE = 1;
    public static final int  ALGORITHM_ACCULUS_UPPOSE = 2;
    public static final int  ALGORITHM_ACCULUS_UPPOSE_FPGA = 3;
    public static final int  ALGORITHM_ACCULUS_HAND = 5;
    public static final int  ALGORITHM_OPEN_POSE = 10;
    public static final int  ALGORITHM_LW_HUMAN_POSE = 11;
    public static final int  ALGORITHM_OPEN_POSE_SINGLE_SCALE = 12;

    private long estimatorHandle;

    public AiliaPoseEstimatorModel(long netHandle, int algorithm) throws AiliaException {
        estimatorHandle = AiliaPoseEstimator.Create(netHandle, algorithm);
    }

    public void close()
    {
        AiliaPoseEstimator.Destroy(estimatorHandle);
    }

    public void compute(byte[] src, int srcStride, int srcWidth, int srcHeight, AiliaImageFormat srcFormat) throws AiliaException
    {
        AiliaPoseEstimator.Compute(estimatorHandle, src, srcStride, srcWidth, srcHeight, srcFormat.getValue());
    }

    public int getObjectCount() throws AiliaException
    {
        return AiliaPoseEstimator.GetObjectCount(estimatorHandle);
    }

    public AiliaPoseEstimatorObjectPose getObjectPose(int objIndex) throws AiliaException
    {
        return AiliaPoseEstimator.GetObjectPose(estimatorHandle, objIndex, AiliaPoseEstimatorObjectPose.version);
    }

    public AiliaPoseEstimatorObjectFace getObjectFace(int objIndex) throws AiliaException
    {
        return AiliaPoseEstimator.GetObjectFace(estimatorHandle, objIndex, AiliaPoseEstimatorObjectFace.version);
    }

    public AiliaPoseEstimatorObjectUpPose getObjectUpPose(int objIndex) throws AiliaException
    {
        return AiliaPoseEstimator.GetObjectUpPose(estimatorHandle, objIndex, AiliaPoseEstimatorObjectUpPose.version);
    }
}
