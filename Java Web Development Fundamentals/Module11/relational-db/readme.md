# Reading about H2 DB

https://www.h2database.com/html/tutorial.html

https://www.h2database.com/html/cheatSheet.html


# Installation

The installation methods are non-invasive in both cases.
You only need to download files and copy to a folder.

## H2 DB

https://www.h2database.com/html/download.html

Choose Platform-Independent Zip

You only need the `h2-<version>.jar` file from the bin folder.

## DBeaver

https://dbeaver.io/download/

Download Community edition. Unzip the file to a folder of your choice.


# Create DB

Run H2 shell:

`java -cp h2*.jar org.h2.tools.Shell`

Answer the questions. Make sure you provide the DB name in the url (**test** by default):

`jdbc:h2:~/test`

Configure user and password. User is **sa** by default.


# Run H2 DB Server

`java -cp h2*.jar org.h2.tools.Server -tcp`


# Connect from DBeaver

Run DBeaver: `dbeaver.exe`

Create a new Database Connection.

- Use URL connection type.
- Type the following URL (replace test with other DB if needed): `jdbc:h2:tcp://localhost:9092/~/test`


# DB operations

If the connection is successful, you can open SQL Script and execute SQL commands.

See example files in project **scripts** folder. Execute in this order:

- `create.sql`
- `insert.sql`
- `query.sql`
- `optimization.sql`


# Notes

H2 DB creates database files in you user's home directory.

- `test.mv.db`

