### Quality Policy

> Our quality policy aims to ensure the overall quality of the project throughout its development cycle. It encompasses
> the following key aspects:

1. Functionality: The program should deliver the intended functionality as specified in the user stories and
   requirements. Thorough testing will be conducted to ensure that all features work correctly and meet user
   expectations.
2. Usability: The user interface should be intuitive, user-friendly, and consistent. Usability studies and user feedback
   will guide the improvement of the interface, ensuring that users can easily navigate and interact with the program.
3. Localization: The program should support multiple languages and display symbols and text accurately. Localization
   issues will be addressed to provide a seamless experience for users in different regions and languages.
4. Visual Consistency: The program's visual elements, such as colors, fonts, and layout, should be consistent throughout
   the interface. Design standards and guidelines will be followed to ensure a cohesive and visually appealing user
   experience.
5. Branding: The program's branding, including the logo, splash screen, and program name, will be designed to reflect
   the desired image and create a memorable identity for the application.
6. Performance: The program should perform efficiently and respond promptly to user actions. Performance testing and
   optimization will be conducted to enhance the program's responsiveness and minimize any delays or bottlenecks.
7. Code Quality: The codebase should adhere to best practices and coding standards. Attention will be given to
   readability, maintainability, and scalability. Warnings and errors will be addressed promptly to maintain a clean and
   robust codebase.
8. Continuous Testing: Unit tests, both blackbox and whitebox, will be developed to verify the functionality and
   integrity of the code. Automated testing will be integrated into the development process to ensure continuous quality
   assurance.
9. Collaboration: Developers will actively engage in code reviews to promote knowledge sharing, identify potential
   issues, and improve code quality. A checklist will be followed during code reviews to ensure thorough examination and
   feedback.
10. Documentation: Clear and comprehensive documentation will be provided to aid developers, users, and maintainers.
    This includes user manuals, API documentation, and inline code comments.
11. Continuous Improvement: Feedback from users, stakeholders, and team members will be actively sought and used to
    continuously improve the quality of the program. Lessons learned from previous sprints will be applied to refine
    development processes and practices.

> Check in Project: Module Concepts document on Canvas in the Project module for more details

**GitHub Workflow** (start Sprint 1)

1. Create a new branch for each user story or task based on the main branch.
2. Develop and implement the changes for each user story or task in their respective branches.
3. Regularly commit changes and push them to the remote repository.
4. Once the development is complete, create a Pull Request to merge the branch into the main branch.
5. Assign a team member for code review.
6. Upon successful code review, merge the branch into the main branch.
7. Monitor and resolve any merge conflicts, if they occur.
8. Repeat the process for each user story or task in the Sprint backlog.

**Unit Tests Blackbox** (start Sprint 2)
> Testing will be saved in src/test/java Both Blackbox and Whitebox testing can be in the same file. BlackBox testing
> will be created by the Developer who wrote the code being reviewed prior to writing or by a team member who has not
> worked on this code previously. The tests will preformed by a team member who has not worked on the code. Junit tests
> are acceptable for BlackBox testing.

**Unit Tests Whitebox** (online: start Sprint 2, campus: start Sprint 3)
> Testing will be saved in src/test/java. Both BlackBox and Whitebox testing can be in the same file. The Developer will
> create WhiteBox testing as needed to attain at least 75% coverage.

Edge and node coverage can be completed from BlackBox testing, only add additional WhiteBox if coverage criteria is not
met.
**Code Review** (online: due start Sprint 2, campus: start Sprint 3)
> Before user story is merged into Dev, at least one code review will be preformed.

Coding Standards (CG):

1. All source code files must have a file banner comment present and filled in. This banner is available in the
   templates file on Canvas.
2. All public classes must have a class banner comment present and filled in. This banner is available in the templates
   file.
3. All public methods including constructors, except getter/setter methods, must have a method banner comment present
   and filled in.
5. All attributes must be private (class member variables, not constants).
6. All literal values, except loop indices starting at 0 or 1 must be declared as constants.
7. All code should be consistent stylistically. This includes:
   a. All {} should appear with the { at the end of a line and } on its own line
   b. Indentation should be consistent.
   c. All complex statements (if, else, switch, loops) must use explicit {} even if the body is a single line.

> Include a checklist/question list which every reviewer will need to fill out/anser when conducting a review, this
> checklist (and the answers of course) need to be put into the Pull Request review.

# Review CheckList

- [X] Are the comments comprehensible and add something to the maintainability of the code (trivial comments do not
  help, rather write readable code)?
- [X] Are the comments are neither too numerous nor verbose?
- [X] Have types have been generalized where possible?
- [X] Are parameterized types being used appropriately?
- [X] Are there exceptions being used appropriately?
- [X] Is there repetitive code that has been factored out?
- [X] Are the frameworks being used appropriately – methods have all been defined?
- [X] Are unit tests are present and correct?
- [X] Have common errors been checked for?

**Static Analysis**  (online: start Sprint 3, campus: start Sprint 3)
> Your Static Analysis policy

**Continuous Integration**  (start Sprint 3)
> Your Continuous Integration policy
