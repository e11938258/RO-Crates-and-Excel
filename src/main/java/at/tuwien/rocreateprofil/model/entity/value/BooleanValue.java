package at.tuwien.rocreateprofil.model.entity.value;

public class BooleanValue implements Value  {
    
    private final Boolean value;

    public BooleanValue(Boolean value) {
        this.value = value;
    }
    
    @Override
    public Object getValue() {
        return value;
    }
    
}
