package at.tuwien.rocreateprofil.exception;

import org.apache.batik.util.Messages;

public class RoCrateProfileBaseException extends RuntimeException {

    public RoCrateProfileBaseException(Error error) {
        super(error.getMessage());
    }

    public RoCrateProfileBaseException(Error error, Object... arguments) {
        super(Messages.formatMessage(error.getMessage(), arguments));
    }

}
