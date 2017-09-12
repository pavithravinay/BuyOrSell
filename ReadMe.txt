										BuyOrSell.com
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

CSP595 - Enterprise Web Applications
Project - BuyOrSell web application
Team 13

Team Members:
Gupta, Nishu Ganesh, DaminiBorde, Rohan Satishrao Vinay, PavithraMishra, Gautam


Total Lines of Code :- 6048 LOC
~~~~~~~~~~~~~~~~~~~

Features implemented of Assignment #1 :-
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	1. Customer is able to create online account and use the account credentials to login. Forgot password feature is also implemented.
	2. Customer is able to post new advertisements, view advertisements of other users.
	3. Seller can add / update / delete advertisements at any time.
	4. Buyer can view different advertisements and can contact Seller via means of contact number and email address.
	5. We have used SAX Parser in order to store advertisement Categories which are loaded once Tomcat starts.

Features implemented of Assignment #2 :-
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	1. Users account information and messages are stored in MySQL database.
	2. Seller advertisements are stored in MySQL database. Advertisement insert / delete / update are reflected in both MySQL database and HashMap. 
	3. Customers are able to submit reviews of both advertisement and seller.
	4. All the reviews are stored in MongoDB database    
	5. Added Trending data for Top 6 Viewed Advertisement, Top 6 Rated Seller, Top 6 Rated Advertisement.

Features implemented of Assignment #3 :-
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	1. We have used combination of both JSP and Servlet along with Java Beans / POJO in our project.

Features implemented of Assignment #4 :-
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	We have implemented search Auto-Completion feature using Ajax in order to search for required advertisements.
	When user types in search bar, then we search for the matching advertisements in our database using Ajax.

Features implemented of Assignment #5 :-
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	We implemented Twitter integration where we are fetching last 100 tweets each from Bestbuy_deals, Newegg and Slickdeals twitter accounts.
	Then we are matching these tweets with our Advertisement Title and displaying the respective tweets in advertisement page.

Steps to deploy and run the project:-
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	1. Copy the "CSP595_Project" folder into the Apache Tomacat's "webapps" folder.

	2. Set classpath of MongoDb and MySQL drivers if not there. The required JAR files can be found in "CSP595_Project\WEB-INF\lib" folder.

	3. You should have MySQL running on port number 3306. Execute the attach SQL script file "dml_script.sql" to create database/tables required to run the project.

	4. This project also requires that you have a MongoDb Database named "BuyOrSell" and collection named "Reviews" in BuyOrSell database.

	5. Navigate to "CSP595_Project\WEB-INF\classes\" folder where all the JAVA source code is located. 

	6. Compile all the files in CMD by typing -->  javac *.java

	7. If all the files are compiled properly then you should see .class files in the "classes" folder without any error message on command prompt.

	8. Start a web browser and navigate to http://localhost/CSP595_Project. You should see the webpage of BuyOrSell portal.

	9. Sample user's login credentials is specified in output.pdf. You can use those to login and test functionalities.