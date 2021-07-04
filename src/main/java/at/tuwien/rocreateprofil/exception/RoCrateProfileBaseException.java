package at.tuwien.rocreateprofil.exception;

public class RoCrateProfileBaseException extends RuntimeException {

    public RoCrateProfileBaseException(RoCrateError roCrateError) {
        super(roCrateError.getMessage());
    }

    public RoCrateProfileBaseException(RoCrateError roCrateError, Object... arguments) {
        super(String.format(roCrateError.getMessage(), arguments));
    }

}
