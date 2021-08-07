# contact-management-system-mysql-javafx

### Functionalities up to date:

- Sign up
- Login with respective credentials given when signing up
- Add contacts
- Delete user profile through password authentication [ this also clears all contacts user has saved so far ]
- Then sign up as a new user again



### IMPORTANT!

1. *Set your respective MySQL credentials at* ***src\app\Database.java***

2. *You should have the* ***jdbc connector*** *loaded to your project through your IDE*

3. *Run the 3 simple SQL queries mentioned in any one of the files in the* ***sql*** *folder* ***before running the application for the first time*** 

4. *Java version used is 1.8, i.e. Java8*

5. *If have you forgotten your credentials you can run the below MySQL query and retrieve them*

``` sql
  SELECT * FROM users;
```

#### Note :

The value of the contacts' number **cannot exceed (2^31 - 1)** and should be non-negative.