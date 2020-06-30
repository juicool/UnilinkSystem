# UnilinkSystem
## JAVAFX Application
Unilink system allows user to create and reply to posts of type event, sale and job.


#Task Specifications

##Packages and Organisation of Code
You must use the following packages to separate your code into sets of related classes and interfaces:
●	view: contains all your GUI classes.
●	controller: contains all your listener classes with event handling code.
●	model: contains the main business logic of your application, all your classes to store and process data (Post, Reply, Event, Sale, Job ...), all exception classes and all database and file handling classes.
●	main: contains the startup class, i.e. UniLinkGUI.java, which is the main entry point of your application.


##Exception Handlings

###Creating Custom Exception Classes
You are required to create at least three exception classes such as PostNotFoundException, PostClosedException, InvalidOfferPriceException… or any other custom exception class suitable for your application to represent various exceptional cases that can occur when your program executes. All those custom exception types should allow appropriate error messages to be specified when exception objects are created.

###Generating and Handling Exception Objects
To apply the custom exceptions in your program, you will need to look for areas in your program where you need to handle various cases such as when there is an invalid post id, or when a post is already closed or when there is an invalid user input. Then you should generate appropriate exception objects, throw and catch those exception objects correctly in your code. 
All exceptions need to be caught and handled in an appropriate manner. Users should be able to see the error messages encapsulated in exception objects. In a GUI application, error messages must be shown to the users using dialog boxes. 


##Using Database For Data Persistence
Every time your program is executed and terminated, data must be read from and saved to a database. You must use an embedded HSQLDB or SQLite database. You must keep all database files in a folder named "database" which is a direct sub-folder of your project.
Your database must contain at least two tables, one table to store post information and another table to store replies information. It's not enough to just simply store the output of the toString methods of a post object or reply object. You must not store binary data directly in database. You need to create tables with multiple columns having appropriate data types (i.e. text and numerical data types)  that are suitable for storing data in your application. 


##Graphical User Interface (GUI)
All user interaction with the UniLink system must be done via the GUI. Implementation requirements for each component of the GUI are described below:

###Login Window
This login window is the first window to appear when your program is executed. This window will reappear when users log out of the main window.
The login window must be placed in the centre of the screen and must contain the following components: 
•	A text field to allow the user to enter a username to log in (for simplicity, no need for password). Username requirements are: 
    - Should begin with the character 's' followed by a number, length should be less than or equal to 7 characters.
•	Log In button: after entering a valid username, the user clicks this button to log in, this login window will be closed and the main window will appear.


###Main Window
The main window must have the following components:

####Menu bar
The main window must contain a menu bar with the following two menus:
•	A menu named Unilink having two menu items:
o	Developer Information: when user clicks on this menu item, a small window appears in the middle and on top of the main window, showing your student name and student number. User can click a button to close this window and return to the main window.
o	Quit UniLink: when user clicks on this menu item, your program will stop execution and quit after saving all data to database. 
•	A menu named Data having two menu items:
o	Export: when user clicks on this menu item, all post and reply data is exported into a single text file
o	Import: when user clicks on this menu item, he or she can select a text file on your local computer to import post and reply data.

####Toolbar
The toolbar must allow users to perform the following functionalities:
•	Create a new post:
o	you need to include appropriate buttons that user can click on to create new posts of the three types of post (i.e. event, sale and job posts). 
o	When user clicks on one of these buttons to create a new post, your program must display a window containing a form for user to fill in necessary details to create the new event or sale or job post. 
o	Your program must allow the user to browse and select an image from your computer to upload to your program when creating a new post. The image will be saved in the images folder which is a direct sub-folder of your project.
•	Filtering capabilities: the toolbar must contain the following three drop-down lists to allow the user to filter the list of posts displayed in the centre area in the main window. Details are below:
o	Filter by type: a drop-down list contains post type, i.e. All, Event, Sale, Job. When user selects an item in the list, the main post list is displayed accordingly. For example, when user selects Event, then only Event posts are shown, when user selects All, then all posts are shown in the list of posts.
o	Filter by status: a drop-down list contains post status values, i.e. All, Open, Closed. When user selects an item in the list, the main post list is displayed accordingly. For example, when user selects Closed, then only closed posts are displayed, when user selects Open, then only open posts are displayed.
o	Filter by creator: a drop-down list contains two items, i.e. My Post and All. When user selects My Post, then only the posts created by the current user are shown, if the user selects All, then all posts are shown.
•	Log Out button: when the user clicks this button, the main window is closed and the login window reappears in the centre of the computer screen.

####Centre area of the main window
The user id of the currently logged in user must be displayed at the top of the centre area so we know which user is currently logging in. 
The centre area of the main window must contain a scrollable list of posts managed by your program. In this list, each post is displayed as a list item. 
Each list item must contain the following information:
•	A small image of the post.
•	Common details of all types of posts such as post id, title, description, creator id, status.
•	Details specific to each type of post:
o	event post: venue, date, capacity, attendee count, no need to show attendee list here.
o	sale post: highest offer, minimum raise 
Note: asking price should only be visible in a post if the currently logged in user is the creator of the post, other users should not be able to see the asking price of a sale post.
o	job post: proposed price, lowest offer
o	different background color should be used for different type of post (for example: light cyan for event post, light pink for sale post, light yellow for job post) to make it easier to recognise the post type.
•	Reply button: the user clicks this button to reply to a post ( a post creator should not be allowed to reply to his or her own post)
o	If the post is an event post, this button should be implemented as a Join button which the user can click on to join the event. Your program must display appropriate response messages when the user clicks this Join button, in cases when the join request is accepted and when the event is full.
o	If the post is a sale or job post, then when the user clicks Reply, your program must allow the user to enter an offer value if the post is open. Your program must display appropriate response messages when an offer value is accepted or rejected. 
o	Your program must not allow post creators to reply to their own posts.
•	More Details button: this button should only be visible in posts created by the currently logged in user. When the user clicks on this button, the main window will be closed and the Post Detail window will open. Your program must not show both the main window and post detail window at the same time. 
Note: post replies must not be displayed in the main window. Post replies must be displayed in the post detail window. Only the creator of a post can view replies to that post. 


###Post Detail Window
Only the creator of a post can open this window to see more details about the post, including all replies to that post. If there is no reply, then the post creator is allowed to edit his or her post details. If there is one or more replies, then the post creator is no longer allowed to edit any post detail.

####Edit post details
In this post detail window, all post details are displayed, including a larger image associated with the post. If there is no reply, your program must enable the post creator to edit post details (post id should not be editable) and upload another image which will replace the existing image. After the post creator finishes editing, he or she can click a Save button, and all details will be saved. If there is one or more replies, it means that responders have  given replies based on existing details and the post creator is no longer allowed to edit any post details.

####View replies
The post creator must be able to view all replies to the post in this post detail window
•	Event post: your program must show the list of users participating in the event.
•	Sale post: your program must use a table to display responder ids and offer values in descending order of offer values.
•	Job post: your program must use a table to display responder ids and offer values in ascending order of offer values.

####Close post
There must be a button to allow the creator to close the post. If a post is closed, it is no longer possible to re-open it.

####Delete post
There must be a Delete button to allow the creator to delete the post. If the creator clicks this button, a confirmation dialog box is displayed. 
•	If the post creator clicks No to not delete the post, he or she returns to the post detail window.
•	If the post creator clicks Yes to confirm the deletion, the post is deleted, the post detail window is closed and the post creator returns to the main window. The deleted post is no longer visible in the post list in the main window.

####Back to main window
The post detail window must have a button to allow the post creator to go back to the main window. When the post creator clicks on this button, the post detail window is closed and the main window is reopened.



##Other GUI Requirements and Input Validation Requirements
●	You must use layout manager classes such as BorderPane, GridPane, HBox, VBox... to control the layout of the components in your GUI. 
●	You are encouraged to explore and use various JavaFX User Interface (UI) controls to implement your graphical interfaces for all the required functionalities. For such UI controls use the Dialog class and its subclasses such as TextInputDialog, ChoiceDialog and Alert classes in the javafx.scene.control package. 
●	All user inputs via the GUI must be validated. Your program should not crash at any point given any user input.
●	All error messages and messages from exception objects should be displayed in the GUI using JavaFX Alert classes in the javafx.scene.control package. 

#Run locally
●	Pull the folder in IntelliJ IDEA

##For adding JAVAFX library to your project:
●	Select the correct JavaFx for your operating system
● Unzip the downloaded zip file, rename the bin folder to JavaFX11 (no need to rename, just use the bin folder directly), remember the absolute path (the location) of this bin folder on your computer.
● Create a run configuration (IMPORTANT: specify the Virtual Machine (VM) options) like:
  --module-path absolute_path_to_lib_folder --add-modules=javafx.controls,javafx.fxml,javafx.graphics
  


