ECHO.
ECHO Example #1: Loading SQLServer Driver ...
ECHO.
java -jar dbQueryExport.jar -c net.sourceforge.jtds.jdbc.Driver

ECHO.
ECHO Example #2: Loading SQLServer Driver and define databse url ...
ECHO.
java -jar dbQueryExport.jar -c net.sourceforge.jtds.jdbc.Driver -d jdbc:jtds:sqlserver://localhost:1433;databaseName=example

ECHO.
ECHO Example #3: Loading SQLServer Driver and define databse url, username and password ...
ECHO.
java -jar dbQueryExport.jar -c net.sourceforge.jtds.jdbc.Driver -d jdbc:jtds:sqlserver://localhost:1433;databaseName=example -u example -p example

ECHO.
ECHO Example #4: Loading SQLServer Driver and define databse url, username and password and define SqlFileName complete path ...
ECHO.
java -jar dbQueryExport.jar -c net.sourceforge.jtds.jdbc.Driver -d jdbc:jtds:sqlserver://localhost:1433;databaseName=example -u example -p example -f .\example-sqlserver-query-1.sql

ECHO.
ECHO Example #5: Loading SQLServer Driver and define databse url, username and password, define SqlFileName complete path and define ExcelOutputFileName complete path ...
ECHO.
java -jar dbQueryExport.jar -c net.sourceforge.jtds.jdbc.Driver -d jdbc:jtds:sqlserver://localhost:1433;databaseName=example -u example -p example -f .\example-sqlserver-query-1.sql -o .\example-sqlserver-query-1.xls

ECHO.
ECHO Example #6: Loading SQLServer Driver and define databse url, username and password, define SqlFileName complete path, define ExcelOutputFileName complete path and ask for a ad-hoc parameter ...
ECHO.
java -jar dbQueryExport.jar -c net.sourceforge.jtds.jdbc.Driver -d jdbc:jtds:sqlserver://localhost:1433;databaseName=example -u example -p example -f .\example-sqlserver-query-2.sql -o .\example-sqlserver-query-2.xls
