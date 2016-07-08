# Project #1: List App

---

## Overview
It's time to create your very first Android app that runs on a real Android device!
You will learn the basic Android UI building blocks, and how they fit together to
make a fully functional app.

You may make whatever you want. Your app must demonstrate mastery of Android
Activities, Intents, Layouts, and ListViews. Come up with ideas of what you
want to make and talk to the instructional staff for approval.

This might seem intimidating at first, but you will soon have the ability to break
down what you see on the screen into easily identifiable parts. Learning to both
design an app and implement your designs are crucial skills to being a successful
Android developer.

You will be working individually for this project. The project will be split into
two phases. First, you will be creating one iteration of a paper prototype. You
will be drawing the design of each screen, as well as describing what happens when
the user interacts with each element on the screen. Make sure you receive instructor
feedback after completing your prototype. Second, you will be coding the app itself.
Implement the design you created in the first step using all of the knowledge you
gained this so far. Test both the layout and functionality of each screen as you
create them. Don't wait until the end!

---

#Requirements:
- Implement a paper prototype for your app.
- Your app must contain a ListView and/or GridView in two activities
- Your app should be able to show a list of lists
- Your app must implement at least two Activities
- Data must be passed from one activity to another
- Data changes in one Activity must appear in all other Activities.
- Persist data while your app is open using a **singleton** (or other, better, ways)
- Users can add new items to each list
- Users can edit items in each list
- Users can delete items in each list
- Create custom Java classes to model your data (see below)
- Display correctly in both landscape and portrait orientations
- Show error messages if invalid input is given

---

#### List of Lists
Your app should be able to hold a collection of lists. For example: 

- A Pokemon app could have a list of Pokemon Types, then a list of specific Pokemon inside that type.
- A library app could have a list of authors, then a list of books the author has written.
- A bus app could have a list of bus routes, then a list of stops and arrivals.
- A To-Do list app could have a list categories, and things to do inside each category.

You're app must have a list of lists somewhere!! Ask the instructors if you're not sure if
your app meets this requirement.

---

#### Create Custom Java Classes to Model Your Data
If you have an app that shows a directory of courses then your app
should have a file Course.java that defines a class with proper
constructors, getters and setters. Something like this:

```
public class Course {
  public int courseNumber;
  public String name;
  public ClassRoom room;
  
  // ... and so on and so forth
}
```

---

#### Singletons
Singletons are a way to store data that persists between Activites. This technique
will be covered thoroughly in a small lecture and lab during project week.

---

#### Code of Conduct

As always, your app must adhere to General Assembly's
[student code of conduct guidelines](../0_onboarding/code-of-conduct.md).

If you have questions about whether or not your work adheres to these guidelines,
please speak with a member of your instructional team.

---

#### Necessary Deliverables:
- photos of your paper prototype
- a git repo on GitHub (forked from this repo)
- a descriptive README.md file
- screenshots of your app included in the README.md

#### Suggested Ways to Get Started

- Complete as much of the layout XML as possible before starting to write your logic
- Use the Android API documentation - it is very thorough and provides useful code samples
- Don’t hesitate to write throwaway code to solve short term problems
- Write pseudocode before you write actual code (remember to think through the logic first!)

---

### Useful Resources

- [Android API Reference](http://developer.android.com/reference/packages.html)
- [Android API Guides](http://developer.android.com/guide/index.html)

---

#### Example Deliverable

Below you can find an example of what the instructors' final product looks like. Here's a To-Do List app.
**Be creative with your own designs!**

<p align="center">
  <img src="./screenshot1.png" width="250">
  <img src="./screenshot2.png" width="250">
  <img src="./screenshot3.png" width="250">
  <img src="./screenshot4.png" width="250">
</p>

#### Project Feedback + Evaluation


Base on the requirements you can earn a maximum of 18 points on this project. Your
instructors will score each of your technical requirements using the scale below:

    Score | Expectations
    ----- | ------------
    **0** | _Incomplete._
    **1** | _Does not meet expectations._
    **2** | _Meets expectations, good job!_
    **3** | _Exceeds expectations, you wonderful creature, you!_

 This will serve as a helpful overall gauge of whether you met the project goals,
 but __the more important scores are the individual ones__ above, which can help
 you identify where to focus your efforts for the next project!
