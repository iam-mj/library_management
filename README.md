## Library Management Project

A CLI app which oversees a simplified version of a library. Different actions can be executed from the perspective of either a Client or a Librarian. 
A Client has to first sign in and then log in to have access to the app.

---
#### The following classes are included:
1) Lendable Item -> an abstract class, describing any item which can be borrowed by the client
2) Book
3) AudioBook
4) DVDGame
5) Person -> an abstract class
6) User -> an abstract class, describes the common behaviour of a client and a librarian
7) Client
8) Author
9) Lending Card -> describes an active library card of a client
10) Menu -> singleton, offers a simple console interface showing the services
11) Client Services -> utility class
12) Librarian Services -> utility class
    
*) Librarian and Activity Log are classes created, but not yet used due to lack of time

---    
#### The following actions can be performed:

As a Client:
1) Create account
2) Log in
3) Browse all the main.items provided by the library
4) Check how many copies of an item are available
5) Borrow an available item
6) Check the date when a copy of an unavailable item would be back
7) See all your lending cards (which main.items you borrowed and until when you should bring them back) as well as how many overdue main.items you have
8) Edit your profile
9) Delete your profile

As a Librarian:
1) Add an item
2) Edit an item
3) Delete an item
4) See which users have overdue main.items and how many of them
5) Check for an item which users have borrowed a copy of it and when they should bring it back
