package axip.ailia;

public enum AiliaFeatureExtractorDistanceType {

    L2NORM(0);

    private int value;
    private AiliaFeatureExtractorDistanceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
