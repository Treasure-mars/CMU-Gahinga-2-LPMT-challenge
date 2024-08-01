# CMU-Gahinga-2-LPMT-challenge
## Module / Week 1: Solution Design & User Management: Week 1
# During this week
1. The Team of students needs to understand the problem and agree on steps to solve it,
any misunderstanding should be addressed to the TAs. Ensure you are making no
assumptions.
2. The team needs to come up with a clear design of the solution, including:
a. The use case, activity, and class diagrams design of your solution
b. Descriptions of all files that will be used as data store
3. The team needs to work on the User Management requirements: This module includes
a. Registration:
● Admin onboarding / initiating a registration by proving the email of the
user.
● The UUID code will be generated that the user should use while
completing the rest of the registration.
Note: Each team should come up with user-store.txt that contains the
users. Populate the first admin user that will be used to start all other
registration processes. Beware of not storing a plain password but a
hashed password. You may use tools such as OpenSSL or other hashing
utilities, but you may not install any additional packages aside those that
come with a base Ubuntu 24.04 installation.
#Procedures & steps for Registration
● The admin provides the email of the user, and a code is generated, the
user primary info (email & UUID code) stored in the user-store.txt file,
the role is by default “Patient”
● The Patient then complete the registration, provides the UUID code and:
1. First Name
2. Last Name
3. Date of birth
4. Whether they have HIV or not
a. If yes, the time they caught the virus (same as diagnosis
date)
b. If they are on ART drugs
i. If yes, the time they started the ART
5. Country of residence (ISO Code)
6. Password (must be hashed)
b. Login:
The user can log in with email & password: Once completed, logging in will give
the user the ability to
● View Profile Data (Week 2 deliverable)
● Update profile data (Week 2 deliverable)
● download the iCalendar (.ics) schedule of the demise (bonus module in
week 3)
How?
❖ Use OOP to implement this user management system.
❖ The user provides emails & password
➢ While logging in, the system should know the user type without
the user manually specifying it (by the accurate use of
Polymorphism)
❖ Java login method calls a bash script that checks if the user with these
credentials exists, and then returns the data for the information needed to
instantiate the relevant User object to Java.
#OOP & Polymorphism requirement
This module needs to follow a strict polymorphic concept
a. Create an Enum defining the possible user roles in the application
b. Create an interface / abstract class named User
All user fields that all users have in common should be here, that is:
Attributes
● First name
● Last name
● email
● Password
● <Any other information you may need>
c. Create a concrete/child class named Admin extending / implementing User
Does not have any specific attributes.
d. Create a concrete/child class named Patient extending / implementing User
Attributes
1. Date of birth
2. Whether they have HIV or not
3. If yes, the time they caught the virus (same ad diagnosis date)
4. If they are on ART drugs
5. If yes, the time they started the ART
6. Country of residence (ISO)
# What to use Bash for
1. Create a text file to store users (e.g., user-store.txt) that has an initial admin.
This is the same file that the user will be updating every time they update their
information.
2. Create a bash script that manage users (e.g., user-manager.sh) (you may create
multiple scripts, with as many functions as you need). Your script(s) should be able to
support:
● Initiating registration by admin
● Registration by Patient
● Login by a user
Deliverables
● Admin should be able to initiate the Patient registration
● Patient should be able to complete their registration
● Patient should be able to log in
● Admin should be able to download 2 empty CSV files
1. The file that will contain all users (empty now)
2. A CSV file that will contain analytics (empty now)
