# FrameWeb Plugin for Visual Paradigm
Our team is currently developing a plugin that will enable integration between [the FrameWeb Method](https://nemo.inf.ufes.br/en/projetos/frameweb/) and Visual Paradigm. With our easy-to-install plugin, you can quickly create FrameWeb models within the Visual Paradigm environment allowing code generation for Web applications that comply with FrameWeb's conventions and best practices.


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
bash <(curl -sL https://raw.githubusercontent.com/nemo-ufes/frameweb-vp-plugin/main/install.sh)
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

> Note: on a traditional installation, the Visual Paradigm's `APP_PATH` is `/Applications/Visual Paradigm.app/Contents/Resources/app/` on a MacOS and `C:\Program Files\Visual Paradigm CE 17.0` on a Windows system. The `PLUGIN_PATH` should be `~/.config/VisualParadigm/plugins/` on Linux, `~/Library/Application Support/VisualParadigm/plugins/` on MacOS and `~\AppData\Roaming\VisualParadigm\plugins\` on Windows (replace `~` with your user's folder). In case of doubt, run the `install.sh` script and check if it finds the paths for you.


## Quickstart
Once the plug-in is installed, open Visual Paradigm and check if there is a _Plugin_ tab in the toolbar with FrameWeb related buttons. This means that the plug-in has been successfully installed.

If the installation was successfull, you can learn how to use the plug-in to create FrameWeb models, generate code from them, etc. in our [wiki](https://github.com/nemo-ufes/frameweb-vp-plugin/wiki).


## Contributing & current development status
This plug-in is under development, currently it supports:
- Creating FrameWeb Entity Models with help from context-sensitive menus;
- Creating FrameWeb Application, Navigation and Persistence models without help from the plug-in;

Some features on which we are currently working or planning for the future (see [issues](https://github.com/nemo-ufes/frameweb-vp-plugin/issues)):
- Generate code based on FrameWeb models;
- Allow the user to select the code generation templates to use and provide new ones;
- Validate the models according to FrameWeb rules and suggest fixes.

Please read our [contributing guidelines](CONTRIBUTING.md) for more detailed information on how to contribute.
