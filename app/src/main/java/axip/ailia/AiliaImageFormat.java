package axip.ailia;

public enum AiliaImageFormat {
    RGBA(0x00),
    BGRA(0x01),
    RGBA_B2T(0x10),
    BGRA_B2T(0x11);

    private int value;
    private AiliaImageFormat(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
