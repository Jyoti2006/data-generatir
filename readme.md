This utility helps to generate csv file output from the xsd schema.

Prerequisite:
======
Install and set up Java 11 and maven
Import the project to IDE
Under src/main/xsd directory place the xsd schema file for which we need to generate java clases

=======================
Flow:
======
This utility generates Java classes at the time of building project for the xsd schema file present in src/main/xsd directory
Using the Java classes, each objects of worker is created and converted to json and written to csv file

In pom.xml, the plugin : jaxb2-maven-plugin helps to create java classes for the xml schema
Build the project using: mvn clean install
The generated classes would be present under target directory

=======================
Code Flow:
======
WorkerApplication.java is the starting spring boot class to run the code. It creates object of Driver class.
Property files: config/config.properties
	Contains number of records to be generated, output file path, number of threads, chunk_size(generate and write to file at a time)
	Property : generate_record_for_countries : number_of_records records will be generated only for country this country list. If the value is empty or equals "all" then all the object defined in dummy-worker-values.json is considered.

config/dummy-worker-vales-json: This json config file defines set of values for any key. Any one value is randomly picked for a key to be set in final json.

In RecordGeneratorImpl.java class WorkerGenerationTask object is being created to generate each worker record. Following code path creates complete random json and writes to csv file
No. of CSV file = Number of threads. OutputWriter.java writes data to file specific to Worker data.

After the file is written, all the files present in the output directory is uploaded to GCS bucket (configuration is present in config.properties file)

=======================

The code can be extended to generate data from other xsd file. Place the xsd file under src/main/xsd.
Similar to RecordGeneratorImpl and WorkerGenerationTask create Classes for other and create and set respective attributes to java object.
Create respective output writer.
 