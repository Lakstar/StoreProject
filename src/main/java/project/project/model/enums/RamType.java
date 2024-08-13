package project.project.model.enums;

public enum RamType {
    DDR1,DDR2,DDR3,DDR4;

    @Override
    public String toString() {
        return switch (this) {
            case DDR1 -> "DDR1";
            case DDR2 -> "DDR2";
            case DDR3 -> "DDR3";
            case DDR4 -> "DDR4";
        };
    }
}
