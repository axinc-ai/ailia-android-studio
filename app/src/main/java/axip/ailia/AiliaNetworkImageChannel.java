package axip.ailia;

public enum AiliaNetworkImageChannel {
    FIRST(0), // DCYX
    LAST( 1); // DYXC

    private int value;
    private AiliaNetworkImageChannel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
