package at.tuwien.rocreateprofil;

import ExcelReader.ExcelBasedReader;
import Transformer.ExcelTransformer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

public class Run {

    public static void main(String args[]) throws IOException {

        // Load
        ExcelBasedReader excelBasedReader = new ExcelBasedReader(new File("./input/test.xlsx"));
        excelBasedReader.readExcelConverter();
        // Transform
        ExcelTransformer excelTransformer = new ExcelTransformer(excelBasedReader);
        // Create
        excelTransformer.create();

        // Get rdf model
        OntModel model = excelTransformer.getModel();

        // Export
        final File file = new File("./output/test.ttl");
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file, false);
        RDFDataMgr.write(fos, model, Lang.TURTLE);
        fos.close();
    }
}
