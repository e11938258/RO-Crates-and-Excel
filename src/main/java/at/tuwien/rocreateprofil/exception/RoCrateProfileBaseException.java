package at.tuwien.rocreateprofil.exception;

public class RoCrateProfileBaseException extends RuntimeException {

    public RoCrateProfileBaseException(Error error) {
        super(error.getMessage());
    }

    public RoCrateProfileBaseException(Error error, Object... arguments) {
        super(String.format(error.getMessage(), arguments));
    }

}
