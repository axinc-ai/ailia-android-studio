package axip.ailia;

public class AiliaPoseEstimatorKeypoint
{
	public float x;
	public float y;
	public float z_local;
	public float score;
	public int interpolated;

	AiliaPoseEstimatorKeypoint(float x, float y, float z_local, float score, int interpolated)
	{
		this.x = x;
		this.y = y;
		this.z_local = z_local;
		this.score = score;
		this.interpolated = interpolated;
    }
}
