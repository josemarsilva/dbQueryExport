# Welcome dbQueryExport
**dbQueryExport** (Database Query Export) allows you export to a file the results of a query. 

# What is DbQueryExport for?

**dbQueryExport** is usefull tool to your final end user that does not know SQL, but frequently issues you need of information from your systems. Usually final end user needs the result of a query execution exported in a Excel file. Using **dbQueryExport**, you can customize command line arguments to pre-setting some options for your end user, like database connection, path-to-sqlfile, etc. And finally, you can create shortcut icons with these command line arguments.


# Demonstration

* Demo \#1: Using **dbQueryExport** to export an Oracle database Query
![demo-1](https://github.com/josemarsilva/dbQueryExport/blob/master/doc/dbqueryexport-demo-1.png)  

* Demo \#2: Using **dbQueryExport** to export a Mysql e database Query
![demo-2](https://github.com/josemarsilva/dbQueryExport/blob/master/doc/dbqueryexport-demo-2.png)  

* Demo \#3: Using **dbQueryExport** to export a PostgreSQL database Query
![demo-3](https://github.com/josemarsilva/dbQueryExport/blob/master/doc/dbqueryexport-demo-3.png)


# Command line arguments

```
Usage: dbQueryExport [options]

    -c     Class name for invocation
    -d     Database Url location for Jdbc Driver
    -u     Username Jdbc connection
    -p     Password Jdbc connection
    -f     SQL Query Filename complete path
    -o     Export Filename complete path


Examples:
    dbQueryExport.jar -c oracle.jdbc.driver.OracleDriver -d jdbc:oracle:thin:@localhost:1521:dbname -u username -p password -f \"C:\\TEMP\\sqlquery.sql\" -o \"C:\\TEMP\\sqlquery.xls\"
    dbQueryExport.jar -c org.postgresql.Driver -d jdbc:postgresql://localhost/dbname -u username -p password -f \"C:\\TEMP\\sqlquery.sql\" -o \"C:\\TEMP\\sqlquery.xls\"
    dbQueryExport.jar -c com.mysql.jdbc.Driver -d jdbc:mysql://localhost:3306/dbname -u username -p password -f \"C:\\TEMP\\sqlquery.sql\" -o \"C:\\TEMP\\sqlquery.xls\"

See also:
    http://github.com/josemarsilva/dbQueryExport\n"
```

# Checking out from Git
You you can check out the new branch:

```
$ cd your_repo_root/repo_name
$ git fetch origin
$ git checkout gh-pages
```


# License
Free software Yeah !!!
