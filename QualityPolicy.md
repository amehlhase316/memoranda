### Quality Policy
> Describe your Quality Policy in detail for this Sprint (remember what I ask you to do when I talk about the "In your Project" part in the lectures and what is mentioned after each assignment (in due course you will need to fill out all of them, check which ones are needed for each Deliverable). You should keep adding things to this file and adjusting your policy as you go.
> Check in Project: Module Concepts document on Canvas in the Project module for more details 

**GitHub Workflow** (start Sprint 1)
  > We have a main development branch, called "development", which is a sub-branch of master. For each User-story on our Taiga board, a branch will be created off of the development branch. When an individual assigns themselves a task from the Taiga board, they should create a branch for that specific task off of its corresponding User story. When a task is complete the Git master can be notified to merge the task into the User story branch. When all tasks of a User story are completed and merged, the User story branch can be merged into development by the Git master. At the end of sprint, development will be merged into master.

**Unit Tests Blackbox** (start Sprint 2)
  Blackbox testing of units will be performed by the reviewer of a pull request in cases where high code coverage is not sufficient to fully test a method, such as methods that use complicated formulas instead of branches or throw exceptions for certain inputs. Tests must:
  > 1. Develop test cases that cover as many equivalence classes as feasible for each function or module.
  > 2. Be written to pass in accordance with the units' intended behavior as described in their documentation.
  > 3. Pass or fail consistently with the same inputs.
  > 4. Only check more than one result per test when the latter result is dependent on the correctness of the result before it.
  > 5. Be simple and have only one possible execution path.

   Reviewers should:
  > 6. Provide timely feedback to developers on defects found during testing.
  > 7. Implement best practices for blackbox unit testing, such as equivalence class partitioning and boundary value analysis.

 **Unit Tests Whitebox** (online: start Sprint 2, campus: start Sprint 3)
  Formal whitebox unit testing will be performed by the reviewer of a pull request when the units under test are self-contained, meaning they are not reliant on state data or complex functions from other classes or libraries. Tests must:
  > 1. Acheive a node and edge coverage of at least 80% for the units tested.
  > 2. Be written to pass in accordance with the units' intended behavior as described in their documentation.
  > 3. Pass or fail consistently with the same inputs.
  > 4. Only check more than one result per test when the latter result is dependent on the correctness of the result before it.
  > 5. Have only one possible execution path, and be simpler than the unit(s) they test.
  
  Reviewers should:
  > 6. Provide timely feedback to developers on defects found during testing.
  > 7. Correct any very simple and clearly unintentional defects they find, such as a misspelled variable, anomalous indent, or missing semicolon.

**Code Review** (online: due start Sprint 2, campus: start Sprint 3)
  > 1. Conduct code reviews for all changes to the codebase, including new features, bug fixes, and refactoring.
  > 2. Follow a consistent code review process that includes guidelines for the types of issues to look for, the severity of each issue, and the actions to take for each type of issue.
  > 3. Provide clear and actionable feedback to developers during the code review process, including suggestions for improvement and recommendations for best practices.
  > 4. Maintain a culture of collaboration and constructive feedback during code reviews, emphasizing the importance of improving the codebase as a team effort.
  
  > Checklist for developer:
  > - [ ] Is the code well-organize and easy to navigate?
  > - [ ] Are the naming conventions consistent and meaningful?
  > - [ ] Are there any unnecessary comments or code duplication?
  > - [ ] Does the code implement the required functionality correctly?
  > - [ ] Are all edge cases and error conditions handled properly?
  > - [ ] There are no merge conflicts that break the software.
  > - [ ] Are merge conflicts easily fixable.

  > Checklist for reviewer:
  > - [ ] Is the code well-organize and easy to navigate?
  > - [ ] Are the naming conventions consistent and meaningful?
  > - [ ] Are there any unnecessary comments or code duplication?
  > - [ ] Does the code implement the required functionality correctly?
  > - [ ] Are all edge cases and error conditions handled properly?
  > - [ ] There are no merge conflicts that break the software.
  > - [ ] Are merge conflicts easily fixable.

**Static Analysis**  (online: start Sprint 3, campus: start Sprint 3)
  > Your Static Analysis policy   

**Continuous Integration**  (start Sprint 3)
  > Your Continuous Integration policy
