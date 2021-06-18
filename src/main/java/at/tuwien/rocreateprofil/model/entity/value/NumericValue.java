package at.tuwien.rocreateprofil.model.entity.value;

public class NumericValue implements Value {
    
    private final Double value;

    public NumericValue(Double value) {
        this.value = value;
    }
    
    @Override
    public Object getValue() {
        return value;
    }
}
