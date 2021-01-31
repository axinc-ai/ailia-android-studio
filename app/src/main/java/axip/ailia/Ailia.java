package axip.ailia;

public class Ailia {
    public static final int MULTITHREAD_AUTO = 0;
    public static final int ENVIRONMENT_ID_AUTO = -1;

    public static native long Create(int envId, int numThread) throws AiliaException;
    public static native void Destroy(long handle);

    public static native void OpenStreamFile(long handle, String path) throws AiliaException;
    public static native void OpenStreamMem(long handle, byte[] buf, int buf_size) throws AiliaException;
    public static native void OpenWeightFile(long handle, String path) throws AiliaException;
    public static native void OpenWeightMem(long handle, byte[] buf, int buf_size) throws AiliaException;

    public static native void SetInputShape(long handle, AiliaShape shape, int version) throws AiliaException;
    public static native AiliaShape GetInputShape(long handle, int version) throws AiliaException;
    public static native AiliaShape GetOutputShape(long handle, int version) throws AiliaException;

    public static native void Predict(long handle, float[] dest, int destSize, float[] src, int srcSize) throws AiliaException;

    public static native int GetBlobCount(long handle) throws AiliaException;
    public static native AiliaShape GetBlobShape(long handle, int blobIdx, int version) throws AiliaException;
    public static native void GetBlobData(long handle, float[] dest, int destSize, int blobIdx) throws AiliaException;
    public static native int FindBlobIndexByName(long handle, String name) throws AiliaException;
    public static native String FindBlobNameByIndex(long handle, int blobIdx) throws AiliaException;
    public static native String Summary(long handle) throws AiliaException;

    public static native int GetInputBlobCount(long handle) throws AiliaException;
    public static native int GetBlobIndexByInputIndex(long handle, int input_blob_idx) throws AiliaException;
    public static native void SetInputBlobShape(long handle, AiliaShape shape, int blob_idx, int version) throws AiliaException;
    public static native void SetInputBlobData(long handle, float[] src, int srcSize, int blobIdx) throws AiliaException;
    public static native int GetOutputBlobCount(long handle) throws AiliaException;
    public static native int GetBlobIndexByOutputIndex(long handle, int output_blob_idx) throws AiliaException;
    public static native void Update(long handle) throws AiliaException;

    public static native void SetTemporaryCachePath(String cache_path) throws AiliaException;

    public static native int GetEnvironmentCount() throws AiliaException;
    public static native AiliaEnvironment GetEnvironment(int env_id, int version) throws AiliaException;
    public static native AiliaEnvironment GetSelectedEnvironment(long handle, int version) throws AiliaException;

    public static native String GetErrorDetail(long handle);

    public static native String GetVersion();
}
