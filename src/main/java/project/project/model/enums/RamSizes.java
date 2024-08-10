package project.project.model.enums;

public enum RamSizes {
    FOUR_GB(4),
    EIGHT_GB(8),
    SIXTEEN_GB(16);

    private final int size;

    RamSizes(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
