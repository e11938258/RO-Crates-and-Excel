package at.tuwien.rocreateprofil.convertor.rocrate;

import at.tuwien.rocreateprofil.exception.RoCrateError;
import at.tuwien.rocreateprofil.exception.RoCrateProfileBaseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonFileParser {

    public static JSONObject parseFileIntoJSONObject(String jsonFileLocation) {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(jsonFileLocation)));
            return (JSONObject) (new JSONParser()).parse(jsonString);
        } catch (ParseException e) {
            throw new RoCrateProfileBaseException(RoCrateError.MALFORMED_RO_CRATE_METADATA_JSON);
        } catch (IOException e) {
            throw new RoCrateProfileBaseException(RoCrateError.FILE_NOT_FOUND, jsonFileLocation);
        }
    }
}
