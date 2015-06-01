# dbQueryExport
Database Query Export

# Welcome to DbQueryExport project
**DbQueryExport** (Database Query Export) allows you export to a file the results of a query. 

### For what is DbQueryExport?

It is usefull tool to your final end user that frequently issues you need of information from your system to export to Excel file. You can use command line arguments to pre-setting some options for your end user, like database connection, path-to-sqlfile, etc.


### Demo
![demo-1](https://github.com/josemarsilva/dbQueryExport/blob/master/doc/dbqueryexport-demo-1.png)  ![demo-2](https://github.com/josemarsilva/dbQueryExport/blob/master/doc/dbqueryexport-demo-2.png)  ![demo-3](https://github.com/josemarsilva/dbQueryExport/blob/master/doc/dbqueryexport-demo-3.png)


### Checking out from Git
You you can check out the new branch:

```
$ cd your_repo_root/repo_name
$ git fetch origin
$ git checkout gh-pages
```

### Command line arguments

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

### License
Free software Yeah !!!
