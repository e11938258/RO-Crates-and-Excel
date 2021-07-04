package at.tuwien.rocreateprofil.model.entity.dataset;

public enum ColumnType {

    CATEGORICAL,
    NUMERIC,
    STRING;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    public static ColumnType fromString(String source) {
        for (ColumnType type: ColumnType.values()) {
            if (type.toString().equals(source)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown column type '" + source + "'");
    }

}
