SELECT TABLE_NAME
FROM   USER_TABLES
WHERE  TABLE_NAME LIKE UPPER('${TableNameStartingWith|Table Name Starting with}%')
ORDER  BY TABLE_NAME