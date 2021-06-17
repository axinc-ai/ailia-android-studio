package axip.ailia;

public enum AiliaDetectorFlags {

    NORMAL(0);

    private int value;
    private AiliaDetectorFlags(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
