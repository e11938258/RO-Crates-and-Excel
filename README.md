# RO-Crates-and-Excel

## Goal

The goal of the RO-Crates-and-Excel application is to provide the data model for describing data of the excel file in the form RO-Crate. The application can parse data from the selected excel as metadata or generate the data from the RO-Crate metadata.

The application can load the file and parse the metadata. This metadata is not only about dataset, but also about columns, because we aggregate information from each: 

* Dataset metadata:
    * "datePublished" is creation date
    * "dateModified" is last modified date
    * "license" is license typ
    * "author" is owner of the file
    * "encoding" is data encoding type
    * "name" is name of the software application
* Column metadata:
    * "number_of_rows" is number of rows in one sheet
    * "number_of_cols" is number of columns in one sheet
    * "sheet_name" is sheet name
    * "missing_values" indicates if the sheet or column has missing values
    * "column_id" is name of column in the excel
    * "alpha_numeric" indicates if column contains only alpha numeric chars with space
    * "format_type" is type of column. Can be numeric, categorical or string
    * "maximum_length" is maximum length of string
    * "average_length" is average length of string
    * "minimum_length" is minimal length of string
    * "missing_values_proportion" indicates what are the percentage of missing values
    * "stdev_length" is standard deviation of the string length
    * "sum" is sum of numeric values
    * "integer" indicates if numeric values are only type of integer
    * "median" is median of numeric values
    * "mean" is mean of numeric values
    * "maximum" is numeric maximum of column
    * "minimum" is numeric minimum of column
    * "stdev" is standard deviation
    * "category" is the array of categories
    * "category_proportions" is the array of the proprotions of categories
    * "mode" is the value that appears most frequetly
    * "mode_key" is the key that appears most frequetly
    * "mode_in_percent" is the proportion of the value that appears most frequetly

We have RO-Crate Profile which you can find in the folder "./profile".


## Design decisions

We have create our own ontology xlsx2rocrate, because we did not find any appropriate one for our metadata. The ontology describes the metadata which are mentioned in the previous chapter. You can find it in the folder "./ontology".

The ontology has three classes: ExcelColumn, which represents column, ExcelSheet, which represents sheet, and ExcelWorkbook, which represents whole workbook. It has also two object properties: hasColumn and hasSheet. Data properties describes all column metadata and "encoding".

## Implementation details & how to run

The application has the two main parts: parser and mapper. Parser tries to parse the sheets and columns from the selected excel and tries to build the model. It also find the necessary dataset metadata as author or creation date from the file. Mapper loads the metadata from the JSON, generate the model and save them to the new excel. The model is defined from cells, columns, column profiles, rows and sheets. We have also implemented the handler for resolving arguments.

### How to run

You can run help as:

```Bash
java -jar target/ro-crate-excel-profile-1.0-SNAPSHOT.jar help
```

or run the data conversion from Excel file to the RO-Crate metadata as:

```Bash
java -jar target/ro-crate-excel-profile-1.0-SNAPSHOT.jar <excel-file-location> <license-URL>
```

or run the data reconstruction from RO-Crate metadata to the Excel file as:

```Bash
java -jar target/ro-crate-excel-profile-1.0-SNAPSHOT.jar rocrate2excel <ro-crate-folder-location>
```

Of course, you can build and run the code from your preferred development environment or console. ATTENTION: Firstly, you have to install the external library Spreadsheet Transformation to your JAVA environment from the folder external:

```bash
mvn install:install-file -Dfile=./external/SpreadsheetTransformation-0.0.1.jar -DgroupId=io.github.allNormal -DartifactId=SpreadsheetTransformation -Dversion=0.0.1 -Dpackaging=jar -DgeneratePom=true
```

We also provide two examples: one for excel2rocrate direction and one for rocrate2excel. You can find them in the folder "./examples".

## Possible Extensions & shortcomings

* We just implemented parser for numeric, categorical and string values in the cell.
* We used the library Spreadsheet Transformation, which has its limitation for some value type, for instance, there is no Date type.
* Excel must containt row with header at the beginning, there is no parameters to set it differently.
* We used the library which allows us to generate just 256 column, therefore column like AB0 will not work.
* We cannot replicate some excel metadata like created data or original author.
