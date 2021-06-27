package at.tuwien.rocreateprofil.convertor.excel.parser.content;

public class Queries {

    public static final String GET_SHEETS
            = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "PREFIX : <http://www.semanticweb.org/GregorKaefer/ontologies/2020/8/excelOntology#>\n"
            + "select * where {\n"
            // Worksheet
            + " ?w rdf:type :Worksheet .\n"
            + " ?w :SheetName ?SheetName .\n"
            + "} \n"
            + "ORDER BY ASC(?SheetName)";

    public static final String GET_COLUMNS
            = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "PREFIX : <http://www.semanticweb.org/GregorKaefer/ontologies/2020/8/excelOntology#>\n"
            + "select * where {\n"
            // Worksheet
            + " ?w rdf:type :Worksheet .\n"
            + " ?w :SheetName ?SheetName .\n"
            // Column
            + " ?col rdf:type :column .\n"
            + " ?col :ColumnID ?ColumnID .\n"
            + " ?w :hasSheetElement ?col .\n"
            + "}";
    
    public static final String GET_ROWS
            = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "PREFIX : <http://www.semanticweb.org/GregorKaefer/ontologies/2020/8/excelOntology#>\n"
            + "select * where {\n"
            // Worksheet
            + " ?w rdf:type :Worksheet .\n"
            + " ?w :SheetName ?SheetName .\n"
            // Column
            + " ?row rdf:type :row .\n"
            + " ?row :RowID ?RowID .\n"
            + " ?w :hasSheetElement ?row .\n"
            + "}";

    public static final String GET_CELLS
            = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "PREFIX : <http://www.semanticweb.org/GregorKaefer/ontologies/2020/8/excelOntology#>\n"
            + "select * where {\n"
            // Cell
            + " ?c rdf:type :cell .\n"
            + " ?c :CellID ?CellID .\n"
            // Value
            + " ?v :CellValue ?value .\n"
            + " OPTIONAL { ?v :FunctionName ?FunctionName } .\n"
            + " ?v rdfs:label ?type .\n"
            + " ?c :hasValue ?v .\n"
            // Worksheet
            + " OPTIONAL {\n"
            + " ?w rdf:type :Worksheet .\n"
            + " ?w :SheetName ?SheetName .\n"
            + " ?c :isPartOfWorksheet ?w } .\n"
            // Column
            + " ?col rdf:type :column .\n"
            + " ?col :ColumnID ?ColumnID .\n"
            + " ?c :hasColumn ?col .\n"
            // Row
            + " ?row rdf:type :row .\n"
            + " ?row :RowID ?RowID .\n"
            + " ?c :hasRow ?row .\n"
            // Column worksheet - due to lib error
            + " OPTIONAL {\n"
            + " ?col_w rdf:type :Worksheet .\n"
            + " ?col_w :SheetName ?SheetNameCol .\n"
            + " ?col :isPartOfWorksheet ?col_w } . \n"
            + "}";

}
