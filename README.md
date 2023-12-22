# RO-Crates-and-Excel

## Goal

The goal of the RO-Crates-and-Excel application is to provide the data model for describing data of the excel file in the form RO-Crate. The RO-Crate describes the contained objects in JSON-LD format. The resulting JSON-LD model should be representative of the dataset contained in the excel file and should facilitate machine actionability. This was kept in mind when desinging the model, an extension to RO-Crate base model, primarily to support dataset search.   

Queries can be done on 2 levels - the dataset level and the column, dimension, level. Dataset relates to 1 sheet of the excel. We selected the following attributes describing both of those core concepts. (Note that this is an aggregate list and there is further subdivision based on the column type)

* Dataset metadata:
    * "datePublished" is creation date
    * "dateModified" is last modified date
    * "license" is license type
    * "author" is owner of the file
    * "encoding" is data encoding type
    * "name" is name of the software application
    * "number_of_rows" is number of rows in one sheet
    * "number_of_cols" is number of columns in one sheet
    * "sheet_name" is sheet name
* Column metadata:
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
    * "mode" is the number of occurences of the value that appears most frequetly
    * "mode_key" is the key that appears most frequetly
    * "mode_in_percent" is the proportion of the value that appears most frequetly

The full RO-Crate Profile can be found in the folder "./profile" and further describes the resulting RO-crate with the use of restriction terms as defined in RFC 2119.

Additionally the application can generate an exemplary excel based on a RO-Crate. This is useful in the evaluation to see if the dataset description represents the dataset well.

## Design decisions
We wanted to include both the dataset description fromthe excel metadata, containing the author information and created and modified dates as well as the application used to create/modify the document, as well as the contents of the file and thus we chose to support the Microsoft Excel *xlsx format* which has been the standard since 2007. There might be multiple datasets located inside the same file. We support this option but we restrict a single dataset per 1 sheet. If there are empty columns this is still treated as a single dataset in the resulting RO-crate metadata. We chose this approach is it can not be clearly identified where 1 dataset ends and the other one starts as for example the "Total" column of monthly sales can be separated by 1 empty column to increase the visibility. Each dataset is also required to have a header at 0th row to be able to convey the attribute information. 

As suggested by RO-crate we attempted to make use of the already existing ontologies. We reused the datacube ontology (https://www.w3.org/TR/vocab-data-cube/) to model the relationships of the columns to a dataset(sheet). We did not find an ontology for the specific terms we came up with which describe the dataset(sheet) and the columns and thus created our own xlsx2rocrate ontology. The ontology describes the metadata which are mentioned in the previous chapter. It can be found in the folder "./ontology".
The ontology has three classes: ExcelColumn, which represents column, ExcelSheet, which represents sheet, and ExcelWorkbook, which represents whole workbook. It has also two object properties: hasColumn and hasSheet. Data properties describes all column metadata and "encoding".

We support 3 types of columns in the following resolution order numeric, categorical, string. This means if a column is not deemed numeric, it is checked against a category threshold, default 10 unique strings, and if this check does not pass it is resolved as string.
We define additional properties based on the type of the column (for description of the properties see the first chapter)
* numeric: - include range, some spread information, information if double or integer
    * minimum
    * maximum
    * mean
    * stdev
    * integer
* categorical: - list all categories and their proportions to define spread, include most numerous category and its properties
    * category
    * category_proportions
    * mode_key
    * mode_in_percent
* string: - information about the length of the strings (range and spread) and whether the string is only alphanumeric
    * minimum_length
    * maximum_length
    * average_length
    * alpha_numeric
    * stdev_length

Each of the columns also contains the information whether it contains missing values (missing_values) and if so what the proportion the missing values represent (missing_values_proportion). Note that there is no support for custom missing value strings and only an empty string is considered to be a missing value (e.g. we do not support NaN or other abbreviations as of now).

## Implementation details

The main usage of the application is to craete the RO-crate by parsing the metadata, sheets and columns from the selected excel and tries to build the model. This is done in 2 parts - first the xlsx file is unzipped and *docProps/app.xml* and *docProps/core.xml* are used to extract the author, creation/modification dates and the application information (name and version) respectively. Secondly the excel file is transformed into rdf triples using the excel ontology (https://github.com/allNormal/SpreadsheetTransformation) and then the aggregate information is built from that. In order to ensure non-conflicting context entity ids we implemented a synchronized store that provisions unique identifiers. This is also done for columns, as name of the column is its id, but a random unique suffix is added as the names don't have to be unique neither inside a signle sheet nor across the sheets. Afterwards the data is converted into JSON-LD entities and written into the RO-crate metadata (ro-crate-metadata.json). Finally the original file is copied over to the *data/* folder as required by the RO-crate specification.

The other flow is to generate excel files based on the RO-crate metadata to check how the data is described. This is done by reading the mandatory properties of the sheets and columns and generating valid values given the restrictions defined by the properties. As we do not store the exact distribution information for the string and numeric columns normal distribution with the provided mean (mean and average_length respectively) and standard deviation (stdev and stdev_length respectively) is used. For the categorical columns we make use of the proportions to generate the same distribution of each factor. However, due to the library used (http://poi.apache.org/download.html#POI-5.0.0) only 256 columns are supported in the generation (this is not a limit for the RO-creation). The generation is repeated for each sheet given the original sheets number of observations (rows). 

Both the RO-crate and the excel are created in the *output/* directory relative to the execution directory. The RO-create root will be the *output/ro-crate* directory and the generated excel will be call the same as the original file.

### How to run

You can run help as:

```Bash
java -jar target/ro-crate-excel-profile-1.0-SNAPSHOT.jar help
```

or run the data conversion from Excel file to the RO-Crate metadata as: (Note that the license-URL is mandatory for the crate creation, as it is required by the RO-specification.)

```Bash
java -jar target/ro-crate-excel-profile-1.0-SNAPSHOT.jar <excel-file-location> <license-URL>
```

or run the data reconstruction from RO-Crate metadata to the Excel file as:

```Bash
java -jar target/ro-crate-excel-profile-1.0-SNAPSHOT.jar rocrate2excel <ro-crate-folder-location>
```

Of course, you can build and run the code from your preferred development environment or console. 
**ATTENTION: Firstly, you have to install the external library Spreadsheet Transformation to your JAVA environment from the folder external:**

```bash
mvn install:install-file -Dfile=./external/SpreadsheetTransformation-0.0.1.jar -DgroupId=io.github.allNormal -DartifactId=SpreadsheetTransformation -Dversion=0.0.1 -Dpackaging=jar -DgeneratePom=true
```

We also provide two examples: one for excel2rocrate direction and one for rocrate2excel. You can find them in the folder "./examples".

## Evaluation of generated excel files

First of all the excel files describe the dataset on the column level somewhat reliably, albeit only normal distribution is used. There are obvious shortcomings such as no formatting of the cells and no function preservation mapping cell relationships. Those are not contained in the profile on purpose as they are not necessary for the description of the dataset (even though the formulas could provide insights but are hard to standardise in terms of feature connections inside a query and thus not very machine actionable). However, there are other column relationships that could be interesting for data synthesis that would capture the relationships in a bayesian network (e.g. as done in https://github.com/DataResponsibly/DataSynthesizer). However, this approach does not scale very well with high number of columns.

## Possible Extensions & shortcomings

* We just implemented parser for numeric, categorical and string values in the cell. Other column types such as date could be added.
    * We used the library Spreadsheet Transformation, which has its limitation for some value type, for instance, there is no Date type.
* Excel must containt row with header at the beginning, there is no parameters to set it differently.
* We used the library which allows us to generate just 256 column, therefore column like AB0 will not work.
* We cannot replicate some excel metadata like created data or original author.
* We only provide global static threshold for a column to be resolved as a categorical column - column specific threshold could be added or explicit categorical column list could be supported
* Additional information about the distribution of the data inside the columns could be added to support more complex queries (though some of those can be done with the mode_in_percent).
* Missing value handling could be extended with specifieble missing value string (e.g. "Nan", "."...) 
* For better dataset description additional column relationship properties could be added as described in "Evaluation of generated excel files"
