### Quality Policy
> Describe your Quality Policy in detail for this Sprint (remember what I ask you to do when I talk about the "In your Project" part in the lectures and what is mentioned after each assignment (in due course you will need to fill out all of them, check which ones are needed for each Deliverable). You should keep adding things to this file and adjusting your policy as you go.
> Check in Project: Module Concepts document on Canvas in the Project module for more details 

TheNerdyBunch Quality Policy:
- main will remain protected at all times
- Each developer will use their own respective development branch under the guise FirstNameDev
- Each developer will use a US# branch when working on that specific User Story
- The developer is responsible for testing their changes against the current working system
- After a task is complete, a pull request will be created and the current Git Master will be responsible for reviewing and approving/denying the request

**GitHub Workflow** (start Sprint 1)
  > Your Workflow

- Branch from Development (Dev) into your US# branch.
- Branch from Development (Dev) can be used to alter policy/README/checklist or other similar files)
- Work on new US branch.
- Commit small changes, even if they do not compile yet (you are in a US/task branch so it is ok). I want to see that you work consistently so this is really important! (these can be on your "dirty" branch)
- Push to the remote repo often.
- When User Story is done and tested, merge current Dev into your US branch through Pull Request (if the Dev changed)
- Test if everything works after Dev was merged into your US branch (you should test thoroughly, later on with Unit Test, Static Analysis etc.).
- If everything worked well, create a Pull Request to Dev and request a review.
- Reviews of Pull Requests should include good comments and a good code review by a team member (especially after we covered Code Reviews).
- If the Pull Request is approved merge into Dev by Git Master.
- Good teams usually merge Dev into the US branches as soon as Dev changed.
- Git Master then merges the Pull Request into master (this is done to have another level of security).

**Unit Tests Blackbox** (start Sprint 2)
  > Your Blackbox testing policy 

 **Unit Tests Whitebox** (online: start Sprint 2, campus: start Sprint 3)
  > Your Whitebox testing policy 

**Code Review** (online: due start Sprint 2, campus: start Sprint 3)
  > Your Code Review policy   

  > Include a checklist/questions list which every developer will need to fill out/answe when creating a Pull Request to the Dev branch. 

  > Include a checklist/question list which every reviewer will need to fill out/anser when conducting a review, this checklist (and the answers of course) need to be put into the Pull Request review.

**Static Analysis**  (online: start Sprint 3, campus: start Sprint 3)
  > Your Static Analysis policy   

**Continuous Integration**  (start Sprint 3)
  > Your Continuous Integration policy
