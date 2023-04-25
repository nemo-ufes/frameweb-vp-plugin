# FrameWeb Plugin for Visual Paradigm

This plug-in is under development, once it becomes ready for production use (or almost) a better README file should be provided.

### Requirements
1. JDK (11+): https://www.oracle.com/br/java/technologies/downloads/
2. Maven: https://maven.apache.org/download.cgi
3. Visual Paradigm: https://www.visual-paradigm.com/download/

### Quickstart


**Ubuntu/Mac**, open the terminal and type:
```
chmod +x install.sh
./install.sh
```
**Windows**:
1. Right Click on `install.sh`
2. Click on `properties`
3. Allow your execution and save

### Testing
Open Visual Paradigm and check if there is a _Plugin_ tab in the toolbar.
If the installation was successfull, you can learn how to use in [FrameWeb Repository](https://github.com/nemo-ufes/FrameWeb).

### Releases
- [x] Release a stable FrameWeb Plugin for Visual Paradigm
- [ ] Automatically install FrameWeb Plugin dependencies with Shell Script.

### Contributing with us fixing Issues

1. If you're a student at NEMO/UFES working at this project, ask the repository administrators to give you developer access;

2. Go to the [project's issues](https://github.com/nemo-ufes/frameweb-vp-plugin/issues) click one of the open issues and assign it to yourself;

3. On the right-hand side of the issue page, look for the **Development** section, click on the _Create a branch_ link and create the branch;

4. Use the instructions shown by GitHub (`git fetch origin` and `git checkout <branch-name>`) in your local computer to switch to the newly created branch;

5. Work on that branch to resolve the issue at hand. Make as many commits as you want, you can push them to GitHub too;

6. Once you finish (make sure you pushed all your commits), open your branch on GitHub (after it's created, GitHub places a link to it under the **Development** section mentioned earlier), see the information that the branch is ahead of main, click on the _Contribute_ button and select _Open Pull Request_;

7. In the body of the pull request (_Leave a comment_ field), add a [closing keyword](https://docs.github.com/articles/closing-issues-using-keywords) referring to the ID of the issue you are resolving. For instance, if it's issue #1, you can write `Closes #1`. If needed, include other information in the body of the pull request (write as much as you need). Finally, click on _Create Pull Request_;

8. On the right-hand side of the pull request page, under **Assignees**, assign it to yourself;

9. Also on the right-hand side, under **Reviewers**, assign a reviewer to the pull request according to what has been agreed with the other developers and the repository administrators;

10. Wait for the code review comments, work on the code if needed, until the pull request is approved. At this point, click on _Merge pull request_ to merge your changes into the main branch;

11. Finally, go back to the main branch in your local computer (`git checkout main`) and update it to get the changes that were merged by the pull request on GitHub (`git pull origin`).

Move on to the next issue and repeat the cycle.