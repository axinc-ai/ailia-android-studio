package axip.ailia;

public enum AiliaNetworkImageFormat {
    BGR(0),
    RGB(1),
    GRAY(2);

    private int value;
    private AiliaNetworkImageFormat(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
