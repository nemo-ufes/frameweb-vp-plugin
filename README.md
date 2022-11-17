# FrameWeb Plugin for Visual Paradigm

This plug-in is under development, once it becomes ready for production use (or almost) a better README file should be provided.

## Instructions for developers

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
        ~/Library/Application Support/VisualParadigm/plugins/
    </visualparadigm.plugin.dir>
    ```

    This path is typically:

    * On Linux: `~/.config/VisualParadigm/plugins/`
    * On MacOS: `~/Library/Application Support/VisualParadigm/plugins/`
    * On Windows: `C:\Users\<YOUR_USER_NAME>\AppData\Roaming\VisualParadigm\plugins\`

5. With Visual Paradigm closed, run `mvn install`;

6. Open Visual Paradigm. Check if there is a _Plugin_ tab in the toolbar.
