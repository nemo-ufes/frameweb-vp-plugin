# FrameWeb Plugin for Visual Paradigm

This plug-in is under development, once it becomes ready for production use (or almost) a better README file should be provided.

## Requirements
1. JDK (11+): https://www.oracle.com/br/java/technologies/downloads/
2. Maven: https://maven.apache.org/download.cgi
3. Visual Paradigm: https://www.visual-paradigm.com/download/

**Only for Windows Users**:
- Git Bash: https://git-scm.com/downloads

## Installation
### Automatic:
- **Windows**, open `Git Bash` ;
- **Linux / MacOS**, open `Terminal` and type:
```
bash <(curl -sL https://raw.githubusercontent.com/propilideno/frameweb-vp-plugin/main/install.sh)
```
### Manual:
Open `pom.xml`, and:
- Edit: `<!-- APP_PATH -->` to the **PATH** of your Visual Paradigm's **Application Folder**.
- Edit: `<!-- PLUGIN_PATH -->` to the **PATH** of your Visual Paradigm's **Plugin Folder**.
```
    <visualparadigm.app.dir>
      <!-- APP_PATH -->
    </visualparadigm.app.dir>

    <visualparadigm.plugin.dir>
      <!-- PLUGIN_PATH -->
    </visualparadigm.plugin.dir>
```
After edittings, run: `mvn install`.

## Quickstart
Open Visual Paradigm and check if there is a _Plugin_ tab in the toolbar. <br>
If the installation was successfull, you can learn how to use in [FrameWeb Repository](https://github.com/nemo-ufes/FrameWeb).

## Contributing & Ways to improve
This plug-in is under development, improvements can always be made!
- [ ] Allow the designer to add new templates for code generation
- [ ] Allow the designer to choose the templates to use in code generation
- [ ] Generate code for the Entity Model
- [x] Release a stable FrameWeb Plugin for Visual Paradigm
- [x] Automatically install FrameWeb Plugin and your dependencies with Shell Script.

Please read our [contributing guidelines](CONTRIBUTING.md) for more detailed information.
