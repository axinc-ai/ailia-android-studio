package axip.ailia;

public class AiliaPoseEstimatorObjectPose
{
    public static final int version = 1;

	public static final int KEYPOINT_NOSE = 0;
	public static final int KEYPOINT_EYE_LEFT = 1;
	public static final int KEYPOINT_EYE_RIGHT = 2;
	public static final int KEYPOINT_EAR_LEFT = 3;
	public static final int KEYPOINT_EAR_RIGHT = 4;
	public static final int KEYPOINT_SHOULDER_LEFT = 5;
	public static final int KEYPOINT_SHOULDER_RIGHT = 6;
	public static final int KEYPOINT_ELBOW_LEFT = 7;
	public static final int KEYPOINT_ELBOW_RIGHT = 8;
	public static final int KEYPOINT_WRIST_LEFT = 9;
	public static final int KEYPOINT_WRIST_RIGHT = 10;
	public static final int KEYPOINT_HIP_LEFT = 11;
	public static final int KEYPOINT_HIP_RIGHT = 12;
	public static final int KEYPOINT_KNEE_LEFT = 13;
	public static final int KEYPOINT_KNEE_RIGHT = 14;
	public static final int KEYPOINT_ANKLE_LEFT = 15;
	public static final int KEYPOINT_ANKLE_RIGHT = 16;
	public static final int KEYPOINT_SHOULDER_CENTER = 17;
	public static final int KEYPOINT_BODY_CENTER = 18;
	public static final int KEYPOINT_COUNT = 19;

	public AiliaPoseEstimatorKeypoint[] points;
    public float totalScore;
	public int numValidPoints;
	public int id;
	public float[] angle;

	AiliaPoseEstimatorObjectPose(AiliaPoseEstimatorKeypoint[] points, float totalScore, int numValidPoints, int id, float[] angle)
	{
		this.points = new AiliaPoseEstimatorKeypoint[KEYPOINT_COUNT];
		this.angle = new float[3];

		this.totalScore = totalScore;
		this.numValidPoints = numValidPoints;
		this.id = id;
		for(int i = 0; i < KEYPOINT_COUNT; ++i)
		{
			this.points[i] = points[i];
		}
		for(int i = 0; i < 3; ++i)
		{
			this.angle[i] = angle[i];
		}
    }
}
