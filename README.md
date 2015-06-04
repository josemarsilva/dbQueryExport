# Welcome dbQueryExport
**dbQueryExport** (Database Query Export) allows you export to a file the results of a query. 

# What is DbQueryExport for?

**dbQueryExport** is usefull tool to your end user that does not know SQL, but frequently issues you with need of information from your systems databases. Usually end user needs the result of a query execution exported in a Excel file. Using **dbQueryExport**, you can customize command line arguments to pre-setting some options for your end user, like database connection, path-to-SqlQuery-file, adhoc parameters asked during execution, etc. Additionaly, you can create shortcut on his DeskTop with icons and descriptions starting **dbQueryExport** with command line parameters.


# Demonstration

* Demo \#1: Using **dbQueryExport** to export an Oracle database Query

![demo-1](https://github.com/josemarsilva/dbQueryExport/blob/master/doc/dbqueryexport-demo-1.png)  

* Demo \#2: Using **dbQueryExport** to export a Mysql e database Query

![demo-2](https://github.com/josemarsilva/dbQueryExport/blob/master/doc/dbqueryexport-demo-2.png)  

* Demo \#3: Using **dbQueryExport** to export a PostgreSQL database Query

![demo-3](https://github.com/josemarsilva/dbQueryExport/blob/master/doc/dbqueryexport-demo-3.png)

* Demo \#4: Using **dbQueryExport** to ask adhoc parameters used Sql Query

![demo-4](https://github.com/josemarsilva/dbQueryExport/blob/master/doc/dbqueryexport-demo-4.png)


# Command line arguments

```
Usage: dbQueryExport [options]

    -c     Class name for invocation
    -d     Database Url location for Jdbc Driver
    -u     Username Jdbc connection
    -p     Password Jdbc connection
    -f     SQL Query Filename complete path
    -o     Export Filename complete path

SQL Query ad hoc parameters convention is ${ParamName|ParamDescription}

Examples:
    a) Command line arguments
       dbQueryExport.jar -c oracle.jdbc.driver.OracleDriver -d jdbc:oracle:thin:@localhost:1521:dbname -u username -p password -f "C:\TEMP\sqlquery.sql" -o "C:\TEMP\sqlquery.xls"
       dbQueryExport.jar -c org.postgresql.Driver -d jdbc:postgresql://localhost/dbname -u username -p password -f "C:\TEMP\sqlquery.sql" -o "C:\TEMP\sqlquery.xls"
       dbQueryExport.jar -c com.mysql.jdbc.Driver -d jdbc:mysql://localhost:3306/dbname -u username -p password -f "C:\TEMP\sqlquery.sql" -o "C:\TEMP\sqlquery.xls"
    b) SQL Query adhoc parameters to bind execution:
       SELECT owner, table_name FROM user_tables WHERE owner = UPPER('${pOwner|Enter Owner of the tables}')
       SELECT hostname, user FROM user WHERE hostname LIKE '${pHostName|Enter HostName to users}'

See also:
    http://github.com/josemarsilva/dbQueryExport
```

# Checking out from Git
You you can check out the new branch:

```
$ cd your_repo_root/repo_name
$ git fetch origin
$ git checkout gh-pages
```


# License
Yes! It is a **Free** software with open source, Yeah !!!
