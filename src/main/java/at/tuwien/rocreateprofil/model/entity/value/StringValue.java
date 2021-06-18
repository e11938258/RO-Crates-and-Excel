package at.tuwien.rocreateprofil.model.entity.value;

public class StringValue implements Value {
    
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }
    
    @Override
    public Object getValue() {
        return value;
    }

}
