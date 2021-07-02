package at.tuwien.rocreateprofil.output.rocrate;

public class RoCrateSchema {

    public static final String CONTEXT = "@context";
    public static final String GRAPH = "@graph";
    public static final String TYPE = "@type";
    public static final String TYPE_DATASET = "Dataset";
    public static final String TYPE_FILE = "File";
    public static final String ABOUT = "about";
    public static final String CONFORMS_TO = "conformsTo";
    public static final String SCHEMA_VERSION = "https://w3id.org/ro/crate/1.1";
    public static final String DATE_PUBLISHED = "datePublished";
    public static final String NAME = "name";
    public static final String GENERIC_NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String GENERIC_DESCRIPTION = "Ro crate describing a dataset contained in a single excel(xlsx) file." +
            " Provides information on dataset level, as well as on column level based on the column type";
    public static final String LICENSE = "license";

    public static final String DATASET = "dataset";
    public static final String HAS_PART = "hasPart";
    public static final String ENCODING_FORMAT = "encodingFormat";
    public static final String APPLICATION = "application";
    public static final String TYPE_APPLICATION = "SoftwareApplication";
    public static final String VERSION = "version";
    public static final String CREATED = "dateCreated";
    public static final String MODIFIED = "dateModified";
    public static final String AUTHOR = "author";
    public static final String TYPE_PERSON = "Person";
    public static final String TYPE_CREATE_ACTION = "CreateAction";
    public static final String INSTRUMENT = "instrument";
    public static final String CREATE_ACTION_GENERIC_DESCRIPTION = "Process via which the file was created/last saved";
    public static final String RESULT = "result";
    public static final String LICENSE_GENERIC_DESCRIPTION = "License as provided by the creator of the ro-crate";


    public static final String ID = "@id";
    // root entity mandatory values
    public static final String TYPE_CREATIVE_WORK = "CreativeWork";
    public static final String ROOT_ENTITY_ID = "ro-crate-metadata.json";
    public static final String ROOT_DATA_ENTITY_ID = "./";

    // context namespaces
    public static final String RO_CRATE_CONTEXT_VALUE = SCHEMA_VERSION + "/context";
    public static final Object DATA_CUBE_CONTEXT = "http://purl.org/linked-data/cube#";
    public static final Object DATA_CUBE_NAMESPACE = "qb";
    public static final String CUSTOM_ONTOLOGY_CONTEXT = "https://githubraw.com/linkToTaggedTtlFileOfOntology#";
    public static final String CUSTOM_ONTOLOGY_NAMESPACE = "xlsx2rocrate";
    
    public static final String COMPONENTS = "components";
    
    public static final String DATA_CUBE_DATA_STRUCTURE_DEFINITION = "DataStructureDefinition";
    public static final String DATA_CUBE_DIMENSTION_PROPERTY = "DimensionProperty";
}
