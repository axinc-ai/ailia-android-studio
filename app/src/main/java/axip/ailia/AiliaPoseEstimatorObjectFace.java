package axip.ailia;

public class AiliaPoseEstimatorObjectFace
{
    public static final int version = 1;

	public static final int KEYPOINT_COUNT = 68;

	public AiliaPoseEstimatorKeypoint[] points;
    public float totalScore;

	AiliaPoseEstimatorObjectFace(AiliaPoseEstimatorKeypoint[] points, float totalScore)
	{
		this.points = new AiliaPoseEstimatorKeypoint[KEYPOINT_COUNT];

		this.totalScore = totalScore;
		for(int i = 0; i < KEYPOINT_COUNT; ++i)
		{
			this.points[i] = points[i];
		}
    }
}
