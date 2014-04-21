package nl.jappieklooster.math.vector;

enum Dimension {
	x(0), y(1), z(2)

    Dimension(int value) { this.value = value }
    private final int value
    public int value() { return value }
}
