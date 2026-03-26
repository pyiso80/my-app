
### Git 
You have uncommited changes that you want to keep just in case. And you want to go back to the last commit and also keep the current changes.

Think of the stash as a "clipboard" for your entire project. It will take all your current changes, whisk them away to a safe storage area, and automatically reset your working directory to the exact state of your last commit.
```
git stash -u
```

- To see the changes without applying them: `git stash show -p`

- To bring the changes back into your code: `git stash pop`

### Running test selectively with maven
```
Single Class	    mvn test -Dtest=MyTest
Single Method	    mvn test -Dtest=MyTest#testOne
Pattern Match       mvn test -Dtest=Service*
Multiple Classes    mvn test -Dtest=TestA,TestB
```

## Order of execution

Here is the precise sequence of events for a single test method:

`@Sql` (Class-level): If `MERGE` mode is enabled.

`@Sql` (Method-level): Your `contacts-data.sql` runs here.

`@BeforeEach`: Your JUnit setup code runs.

`@Test`: The actual Selenium test method.

`@AfterEach`: Cleanup code.

`@Sql (AFTER_TEST_METHOD)`: If you have any cleanup scripts defined.