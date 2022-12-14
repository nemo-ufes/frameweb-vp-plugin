# FrameWeb Plugin for Visual Paradigm

This plug-in is under development, once it becomes ready for production use (or almost) a better README file should be provided.



## Instructions for developers

At this point, the project may require some knowledge of software development in Java with Maven to be used. 


### How to install and test the plug-in

1. Have Maven and the Java (11+) Development Kit installed in your computer;

2. Install [Visual Paradigm](https://www.visual-paradigm.com/download/) (the [Community Edition](https://www.visual-paradigm.com/download/community.jsp) is free for non-commercial use);

3. Clone this repository;

4. Open `pom.xml` and set the value of the variables listed below:

    4.1. Set the path to the folder in which the Visual Paradigm application is located, e.g.:

    ```xml
    <visualparadigm.app.dir>
       /Applications/Visual Paradigm.app/Contents/Resources/app/
    </visualparadigm.app.dir>
    ```

    This path is typically:

    * On Linux: ???
    * On MacOS: `/Applications/Visual Paradigm.app/Contents/Resources/app/`
    * On Windows: `C:\Program Files\Visual Paradigm CE 17.0`

   4.2. Set the path to Visual Paradigm's plugin folder, e.g.:

   ```xml
    <visualparadigm.plugin.dir>
        /Users/vitor/Library/Application Support/VisualParadigm/plugins
    </visualparadigm.plugin.dir>
    ```

    This path is typically:

    * On Linux: `/home/<YOUR_USER_NAME>/.config/VisualParadigm/plugins/`
    * On MacOS: `/Users/<YOUR_USER_NAME>/Library/Application Support/VisualParadigm/plugins/`
    * On Windows: `C:\Users\<YOUR_USER_NAME>\AppData\Roaming\VisualParadigm\plugins\`

5. With Visual Paradigm closed, run `mvn install`;

6. Open Visual Paradigm. Check if there is a _Plugin_ tab in the toolbar.


### How to contribute

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
