package at.tuwien.rocreateprofil.exception;

public enum Error {

    UNKNOWN_ARGUMENT("Unknown parameter '%s'"),
    INSUFFICIENT_ARGUMENTS_TO_CONSUME("Parameter '%s' requires %s arguments but only %s provided"),
    HANDLER_CANNOT_BE_INSTANTIATED("Handler for parameter '%s' cannot be instantiated"),
    ZERO_PARAMETER_CONSTRUCTOR_REQUIRED("Zero parameter constructor required for class '%s'"),
    MULTIPLE_CONVERSIONS_NOT_ALLOWED("Only one conversion direction allowed! Please check your parameters and consult help"),
    ARGUMENT_MANDATORY_PARAMETERS_NOT_PROCESSED("Additional parameters need to be processed before convertor can be initialized."),
    EXCEL_UNZIP_FAILED("Failed to unzip the excel file! Reason:%s"),
    FILE_NOT_FOUND("File '%s' was not found"),
    INVALID_EXCEL_FILE_SUFFIX("Excel file needs to have the 'xlsx' suffix (created by Excel 2007 onwards)"),
    RO_CRATE_ROOT_DIRECTORY_FAILED("Failed to create root directory '%s' for RO Crate! Check your permissions"),
    RO_CRATE_CONTENT_WRITE_FAILED("Failed to write RO Crate content int '%s'! Check your permissions"),
    FAILED_TO_PROCESS_EXCEL_METADATA("Failed to process excel XML metadata. Ensure that the .xlsx file is not corrupted " +
            "and check if it is supported by the application."),
    INVALID_LICENSE_URL("Provided license URL '%s' is not valid!"),
    CANNOT_MOVE_FILE_TO_RO_CRATE("Cannot move '%s' to the ro crate! Check permissions.");


    Error(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return this.message;
    }
}
