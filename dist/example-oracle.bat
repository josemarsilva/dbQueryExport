ECHO.
ECHO Example #1: Loading Oracle Driver ...
ECHO.
java -jar dbQueryExport.jar -c oracle.jdbc.driver.OracleDriver

ECHO.
ECHO Example #2: Loading Oracle Driver and define databse url ...
ECHO.
java -jar dbQueryExport.jar -c oracle.jdbc.driver.OracleDriver -d jdbc:oracle:thin:@localhost:1521:XE

ECHO.
ECHO Example #3: Loading Oracle Driver and define databse url, username and password ...
ECHO.
java -jar dbQueryExport.jar -c oracle.jdbc.driver.OracleDriver -d jdbc:oracle:thin:@localhost:1521:XE -u MANAGER -p MANAGER

ECHO.
ECHO Example #4: Loading Oracle Driver and define databse url, username and password and define SqlFileName complete path ...
ECHO.
java -jar dbQueryExport.jar -c oracle.jdbc.driver.OracleDriver -d jdbc:oracle:thin:@localhost:1521:XE -u MANAGER -p MANAGER -f .\example-oracle-query-1.sql

ECHO.
ECHO Example #5: Loading Oracle Driver and define databse url, username and password, define SqlFileName complete path and define ExcelOutputFileName complete path ...
ECHO.
java -jar dbQueryExport.jar -c oracle.jdbc.driver.OracleDriver -d jdbc:oracle:thin:@localhost:1521:XE -u MANAGER -p MANAGER -f .\example-oracle-query-1.sql -o .\example-oracle-query-1.xls

ECHO.
ECHO Example #6: Loading Oracle Driver and define databse url, username and password, define SqlFileName complete path, define ExcelOutputFileName complete path and ask for a ad-hoc parameter ...
ECHO.
java -jar dbQueryExport.jar -c oracle.jdbc.driver.OracleDriver -d jdbc:oracle:thin:@localhost:1521:XE -u MANAGER -p MANAGER -f .\example-oracle-query-2.sql -o .\example-oracle-query-2.xls
