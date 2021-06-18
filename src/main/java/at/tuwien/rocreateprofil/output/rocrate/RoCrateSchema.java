package at.tuwien.rocreateprofil.output.rocrate;

public class RoCrateSchema {

    public static final String CONTEXT = "@context";
    public static final String GRAPH = "@graph";
    public static final String TYPE = "@type";
    public static final String TYPE_DATASET = "Dataset";
    public static final String ABOUT = "about";
    public static final String CONFORMS_TO = "conformsTo";
    public static final String SCHEMA_VERSION = "https://w3id.org/ro/crate/1.1";
    public static final String CONTEXT_VALUE = SCHEMA_VERSION + "/context";
    public static final String DATE_PUBLISHED = "datePublished";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String LICENSE = "license";

    public static final String DATASET = "dataset";
    public static final String HAS_PART = "hasPart";
    public static final String FILE = "file";
    public static final String ENCODING_FORMAT = "encodingFormat";
    public static final String APPLICATION = "application";
    public static final String APPLICATION_TYPE = "SoftwareApplication";
    public static final String VERSION = "version";

    public static final String ID = "@id";
    // root entity mandatory values
    public static final String ROOT_ENTITY_TYPE = "CreativeWork";
    public static final String ROOT_ENTITY_ID = "ro-crate-metadata.json";
    public static final String ROOT_ENTITY_ABOUT_ID = "./";

}
