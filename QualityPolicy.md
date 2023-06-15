### Quality Policy
> Describe your Quality Policy in detail for this Sprint (remember what I ask you to do when I talk about the "In your Project" part in the lectures and what is mentioned after each assignment (in due course you will need to fill out all of them, check which ones are needed for each Deliverable). You should keep adding things to this file and adjusting your policy as you go.
> Check in Project: Module Concepts document on Canvas in the Project module for more details 

**GitHub Workflow** (start Sprint 1)
  > Only complete tasts/user stories should be merged into master. No partially complete features.
  > Every merge to master needs to be reviewed by at least one team member before being approved. 
  > Every team member must test their own code before merging into master.
  > Each user story intended to be merged to master branch must have its own branch. Each branch (beside master branch) only contains changes pertaining to a single user story.
  > All pull requests into master should be approved at least 1 day before the duedate of the sprint 
  
  > Naming conventions:
    > Every branch name will be name "us#" where # is the number of the user story
      > If the branch is for a subtask of a user story, it will be named "us#task#" where the first # is the number of the user story and the second # is the number of       the subtask
  
  > Taiga board:
    > Only two user stories assigned to one team member at a time max
    
  > Communication:
    > The team will keep a daily journal for each sprint that documents what tasks they worked on each day

**Unit Tests Blackbox** (start Sprint 2)
  The developer writes blackbox tests and tests them before submitting a pull request. The tester also runs the blackbox tests and adds any new ones they feel are necessary when testing.

 **Unit Tests Whitebox** (online: start Sprint 2, campus: start Sprint 3)
  The developer writes whitebox tests and tests them before submitting a pull request. The tester also runs the whitebox tests and adds any new ones they feel are necessary when testing.

**Code Review** (online: due start Sprint 2, campus: start Sprint 3)
After running black and whitebox tests, the tester does a code review following these standards: 

Coding Standards (CG):
1.   All source code files must have a file banner comment present and filled in. This banner is available in the templates.java file on Canvas.
2.   All public classes must have a class banner comment present and filled in. This banner is available in the templates.java file.
3.   All public methods including constructors, except getter/setter methods, must have a method banner comment present and filled in. This banner is available in the templates.java file.
4.   Naming conventions are as follows:
a.   Constants and Enums should be in all CAPS (example: PI)
b.   Class names should be upper CamelCase, with the first letter uppercase (example: MyClass).
c.    Variable, Parameter, and Method names should be in lower camelCase, with the first letter in lowercase (example: fooBar).
5.   All attributes must be private (class member variables, not constants).
6.   All literal values, except loop indices starting at 0 or 1 must be declared as constants.
7.   All code should be consistent stylistically. This includes:
a.   All {} should appear with the { at the end of a line and } on its own line
b.   Indentation should be consistent.
c.    All complex statements (if, else, switch, loops) must use explicit {} even if the body is a single line.

Code Smells (CS):
1.   Duplicate code
2.   Large Class
3.   Switch statements
4.   Feature Envy: A class that uses methods of other class extensively
5.   Lazy class: A class that does too little.
6.   Excessive use of literals: these should be coded as named constants.
7.   Data clump: A group of variables are passed around together in various parts of the program. Should rather be a single object with these members that can be passed.
8.   Method has too many parameters
9.   Long method
10. Too long identifiers
11. Too short identifiers
   
Functional Defects (FD):
Any defects that hinder the program on working correctly

**Static Analysis**  (online: start Sprint 3, campus: start Sprint 3)
  > Your Static Analysis policy   

**Continuous Integration**  (start Sprint 3)
  > Your Continuous Integration policy
