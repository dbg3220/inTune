---
geometry: margin=1in
---
# PROJECT Design Documentation

## Team Information
* Team name: In-Tune
* Team members
  * Hayden Cieniawski
  * Clayton Acheson
  * Jonathan Zhu
  * Donovan Cataldo
  * Damon Gonzalez

## Executive Summary

To create a web store that allows users to rent and purchase instruments, as well as partake in lessons. Users will be able to log into the website with an email and password, and then have payment and shipping information saved.

### Purpose
To allow customers to interact with the full inventory and capability of the music store from
a personal computer.

### Glossary and Acronyms
> _Provide a table of terms and acronyms._

| Term | Definition |
|------|------------|
| SPA  | Single Page|

## Requirements

This section describes the features of the application.

> _In this section you do not need to be exhaustive and list every
> story.  Focus on top-level features from the Vision document and
> maybe Epics and critical Stories._

Any user who is not the admin will be able to log in using their username and be presented with a home page where they
can browse products in multiple ways(search bar, top products, by category). They will be able to add any product in
the store to their cart with a requested quantity that is equal to or less than the amount the store has in inventory.
If a user logs out than the products in their cart will be retained. The admin will have the ability to login with the
username 'admin' and be presented with a home page similar to that of a regular user but will include the ability to edit
the inventory of the store. Users who have purchased products will also be able to leave reviews, which they can rate on a 1-5 scale and write a short description of their experience with the product. These reviews can be seen by all users. Users can also schedule lessons, from which admins can delete and add to.


### Definition of MVP
The MVP will consist of a fully functional estore with a working backend representation of relevant data types.
Any user will be able to login with a username and password or create an account if they have no login. Products
checked out in their cart will be remembered between sessions and any user should be able to see all products through
a search bar. The admin will be able to login with the username 'admin' and be able to edit the inventory of the store.
Users who have purchased products will also be able to leave reviews, which they can rate on a 1-5 scale and write a short description of their experience with the product. These reviews can be seen by all users. Users can also schedule lessons, from which admins can delete and add to.

### MVP Features
1. Create Account
2. Edit Products
3. Get products by category
4. Browse my cart
5. Save my cart
6. Save payment and Address Info
7. Learn about an Instrument
8. Browse Instrument Categories
9. Log in
10. Search for an Instrument
11. Add an Instrument to my cart
12. Checkout my cart
13. Add a review
14. View all reviews
15. Look at all Lessons
16. Schedule a Lesson


### Roadmap of Enhancements
1. Add a review
2. View all reviews
3. Look at all Lessons
4. Schedule a Lesson
5. admin privileges for Lesson



## Application Domain

This section describes the application domain.

![Domain Model](domain-model-placeholder.png)

> _Provide a high-level overview of the domain for this application. You
> can discuss the more important domain entities and their relationship
> to each other._


## Architecture and Design

The application architecture will be separated into 2 distinct components, an angular frontend implementation
and a Spring API backend implementation. 
Frontend:
Backend: The Spring API will implement a Model, View, View_Model structure that will use a local database
in the form of json files, 1 json file for each type of object being handled by the backend.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

The e-store web application, is built using the Model–View–ViewModel (MVVM) architecture pattern. 

The Model stores the application data objects including any functionality to provide persistence. 

The View is the client-side SPA built with Angular utilizing HTML, CSS and TypeScript. 
The ViewModel provides RESTful APIs to the client (View) as well as any logic required to 
manipulate the data objects from the Model.

Both the ViewModel and Model are built using Java and Spring Framework. Details of the components within these tiers are supplied below.


### Overview of User Interface
On landing, the user will be displayed with a selection of the most popular products by name. This will herein be referred to as the dashboard. From here, they will also be able to see, at the header, there will be four options. These include the Dashboard, Products, Login, and Cart. Products will present the user with a list of all current products available along with their photo, name, and price. There is a search bar on this page that allows users to look up products by first name. From here, a user can click on any product to be taken to that products detail page. Here, the user will be able to add the product to their unique cart (if logged in). If they do not have an account/are not logged in, they will be informed that they need to log in. Otherwise, the product can be added to their cart. The login page is a simple page prompting for users to enter their username. A success page will be followed once the user is signed in. Finally, there is a cart page, it is here that users can see what products they have in their cart and how many of each.


### View Tier
The view tier consists of a parent level app component, as well as numerous child components. These are as follows:
1. Dashboard/Home - The dashboard/home page is the first page that a user will see when they visit the site. It will display the most popular products in the store, as well as an introductory animation with the estore logo. Alongside these, there is a mission statement.
2. Products - The products page will display all products in the store. It will display the name, price, and photo of each product. There will be a search bar that allows users to search for products by name. There will also be a button that allows users to filter products by instrument family.
3. Login - The login page will prompt users to enter their username. If the username is not found, the user will be prompted that an account has been made. which then they must login to confirm. If the username is found, the user will be logged in.
4. Cart - The cart page will display all products in the user's cart. It will display the name, price, and photo of each product. There will be a button that allows users to remove products from their cart. There will also be a button that allows users to checkout their cart.
5. Product Detail - The product detail page will display the name, price, and photo of the product. It will also display a description of the product. There will be a button that allows users to add the product to their cart. There will also be a button that allows users to view all reviews for the product. If the User is an Admin, they will be allowed to delete and edit the values of the product
6. Lessons - The lessons page will display all lessons in the store. It will display the name, price, and time of each lesson. There will also be a button that allows users to schedule a lesson. If the user is an admin, they will be allowed to delete and edit the values of the lesson.
7. Checkout - After hitting the checkout button, a component prompting for shipping and billing information will be displayed. After the user enters this information, they will be prompted to confirm their order. If they confirm, the order will be placed and the user will be taken to the confirmation. If they cancel, they will be taken back to the cart page.
8. 404 page - If a user tries to access a page that does not exist, they will be taken to a 404 page. This page will display a message informing the user that the page they are looking for does not exist. It will also display a button that allows the user to return to the home page.
9. Confirmation - After the user confirms their order, they will be taken to a confirmation page. This page will display a message informing the user that their order has been placed. It will also display a button that allows the user to return to the home page.


### ViewModel Tier
The ViewModel Tier is the Rest-API of our program and it consists only of controllers
for data types used in the View tier. It acts as the point of contact between our front end
and back end. It interacts with HTTP requests and responds with certain bodies and certain error
codes as responses(i.e. OK, CREATED, INTERNAL_SERVER_ERROR, CONFLICT)


### Model Tier
The Model Tier has Product, User, Cart classes which represent the data being stored in the
local database. They represent little functionality of the application besides what is needed
to maintain their internal state.

### Static Code Analysis/Design Improvements

## Testing
Currently 100% of backend tests pass.

### Acceptance Testing
Almost all acceptance tests pass. The only issues that we have run into is that users are unable to
save their cart between sessions. This is because we are using a local database while cart is still being
worked on by the frontend team. Besides that shortcoming, all other acceptance tests pass.

### Unit Testing and Code Coverage
Our code coverage was lower then we would have liked, but this is mostly chalked up to the fact that
the merging process was not smooth. There are other branches with higher coverage, but they were not
brought over, we plan to have these issues ironed out by early sprint 4.

