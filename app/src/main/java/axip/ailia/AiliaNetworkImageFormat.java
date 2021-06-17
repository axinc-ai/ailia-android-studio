package axip.ailia;

public enum AiliaNetworkImageFormat {
	/**
	 * BGR order
	*/
    BGR(0),
	/**
	 * RGB order
	*/
    RGB(1),
	/**
	 * GrayScale (1ch)
	*/
    GRAY(2),
	/**
	 * EqualizedGrayScale (1ch)
	*/
	GRAY_EQUALIZE(3);

    private int value;
    private AiliaNetworkImageFormat(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
