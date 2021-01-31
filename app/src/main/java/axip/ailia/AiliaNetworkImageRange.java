package axip.ailia;

public enum AiliaNetworkImageRange {
    UNSIGNED_INT8(0),
    SIGNED_INT8  (1),
    UNSIGNED_FP32(2),
    SIGNED_FP32  (3);

    private int value;
    private AiliaNetworkImageRange(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
