### Quality Policy
> Describe your Quality Policy in detail for this Sprint (remember what I ask you to do when I talk about the "In your Project" part in the lectures and what is mentioned after each assignment (in due course you will need to fill out all of them, check which ones are needed for each Deliverable). You should keep adding things to this file and adjusting your policy as you go.
> Check in Project: Module Concepts document on Canvas in the Project module for more details 

**GitHub Workflow** (start Sprint 1)
  > Only complete tasts/user stories should be merged into master. No partially complete features.
  > Every merge to master needs to be reviewed by at least one team member before being approved. 
  > Every team member must test their own code before merging into master.
  > Each user story intended to be merged to master branch must have its own branch. Each branch (beside master branch) only contains changes pertaining to a single user story.
  > All pull requests into master should be approved at least 1 day before the duedate of the sprint
  > Team members can approve their own pull requetss to merge a task branch into the user story branch, but at least one member must test and review the user story branch before merging into develop branch 
  
  > Naming conventions:
    > Every branch name will be name "us#" where # is the number of the user story
      > If the branch is for a subtask of a user story, it will be named "us#task#" where the first # is the number of the user story and the second # is the number of       the subtask
  
  > Taiga board:
    > Only two user stories assigned to one team member at a time max
    
  > Communication:
    > The team will keep a daily journal for each sprint that documents what tasks they worked on each day

**Unit Tests Blackbox** (start Sprint 2)
The developer writes blackbox tests and tests them before submitting a pull request to merge the user story branch into the development branch. The tester also runs the blackbox tests and adds any new ones they feel are necessary when testing. Blackbox testing classes are named us#BB, where # stands the the number of the user story.

 **Unit Tests Whitebox** (online: start Sprint 2, campus: start Sprint 3)
The developer writes whitebox tests and tests them before submitting a pull request to merge the user story branch into the development branch. The tester also runs the whitebox tests and adds any new ones they feel are necessary when testing. Whitebox testing classes are named us#WB, where # stands the the number of the user story.

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

Code Review Checklist (Will be included in pull request comments)

Specification / Design 

- [ ] Is the functionality described in the specification fully implemented by the code? 

- [ ] Is there any excess functionality in the code but not described in the specification? 

Initialization and Declarations

- [ ] Are all local and global variables initialized before use? 

- [ ] Are variables and class members of the correct type and appropriate mode 

- [ ] Are variables declared in the proper scope? 

- [ ] Is a constructor called when a new object is desired? 

- [ ] Are all needed import statements included?

- [ ] Names are simple and if possible short

- [ ] There are no usages of “magic numbers”

General

- [ ] Code is easy to understand

- [ ] Variable and method names are spelt correctly

- [ ] There is no dead code (i.e., code inaccessible at runtime)

- [ ] Code is not repeated or duplicated

- [ ] No empty blocks of code

Method Calls 

- [ ] Are parameters presented in the correct order?

- [ ] Are parameters of the proper type for the method being called?

- [ ] Is the correct method being called, or should it be a different method with a similar name? 

- [ ] Are method return values used properly? Are they being cast to the needed type?

Arrays/Data structures 

- [ ] Are there any off-by-one errors in array indexing? 

- [ ] Can array indexes ever go out-of-bounds? 

- [ ] Is a constructor called when a new array item is desired? 

- [ ] Are your data structures ideal?

- [ ] Collections are initialized with a specific estimated capacity

Object 

- [ ] Are all objects (including Strings) compared with equals and not ==? 

- [ ] No object exists longer than necessary

- [ ] Files/Sockets and other resources if used are properly closed even if an exception occurs when using them

Output Format 

- [ ] Are there any spelling or grammatical errors in the displayed output?

- [ ] Is the output formatted correctly and consistently in terms of line stepping and spacing? 

Computation, Comparisons and Assignments 

- [ ] Check order of 

    - [ ] computation/evaluation

    - [ ] operator precedence and 

    - [ ] parenthesizing 

- [ ] Can the denominator of any divisions ever be zero? 

- [ ] Is integer arithmetic, especially division, ever used inappropriately, causing unexpected truncation/rounding? 

- [ ] Check each condition to be sure the proper relational and conditional operators are used. 

- [ ] If the test is an error-check, can the error condition actually be legitimate in some cases? 

- [ ] Does the code rely on any implicit type conversions?

Exceptions 

- [ ] Are all relevant exceptions caught? 

- [ ] Is the appropriate action taken for each catch block? 

- [ ] Are all appropriate exceptions thrown?

- [ ] Are catch clauses fine-grained and catching specific exceptions?

Flow of Control 

- [ ] In switch statements, is every case terminated by break or return? 

- [ ] Do all switch statements have a default branch?

- [ ] Check that nested if statements don't have “dangling else” problems. 

- [ ] Are all loops correctly formed, with the appropriate initialization, increment and termination expressions? 

- [ ] Are open-close parentheses and brace pairs properly situated and matched?

Files 

- [ ] Are all files properly declared and opened? 

- [ ] Are all files closed properly, even in the case of an error? 

- [ ] Are EOF conditions detected and handled correctly? 

- [ ] Are all file exceptions caught? 

Documentation

- [ ] Methods commented in clear language

- [ ] Most comments should describe rationale or reasons (the why); fewer should describe the what; few should describe how. 

- [ ] Are there any out-of-date comments that no longer match their associated code?

- [ ] All public methods/interfaces/contracts are commented describing usage

- [ ] All edge cases are described in comments

- [ ] All unusual behavior or edge case handling is commented

- [ ] Data structures and units of measurement are explained
   
Functional Defects (FD):
Any defects that hinder the program on working correctly

**Static Analysis**  (online: start Sprint 3, campus: start Sprint 3)
  > Your Static Analysis policy   

**Continuous Integration**  (start Sprint 3)
  > Your Continuous Integration policy
