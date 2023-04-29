#!/bin/bash
# OS Defaults
OS=$(uname -s) # Gather OS Name
USER=$(whoami) # Gather USER Name

#FrameWeb-Vp-Plugin Defaults
repo_name="frameweb-vp-plugin"
repo_url="https://github.com/propilideno/frameweb-vp-plugin/archive/refs/heads/main.zip"
frameweb_plugin_path="frameweb-vp-plugin-0.1/"

# =========== Visual Paradigm Defaults ===========
#App Default Path
VISUAL_PARADIGM_APP_DIR_WINDOWS="C:\\Program Files\\Visual Paradigm CE 17.0\\" #Windows
VISUAL_PARADIGM_APP_DIR_MAC="/Applications/Visual Paradigm.app/Contents/Resources/app/" #MacOS
VISUAL_PARADIGM_APP_DIR_LINUX="/home/$USER/Visual_Paradigm_17.0/" #Linux
#Plugin Default Path
VISUAL_PARADIGM_PLUGIN_DIR_WINDOWS="C:\\Users\\$USER\\AppData\\Roaming\\VisualParadigm\\plugins\\" #Windows
VISUAL_PARADIGM_PLUGIN_DIR_MAC="/Users/$USER/Library/Application Support/VisualParadigm/plugins/" #MacOS
VISUAL_PARADIGM_PLUGIN_DIR_LINUX="/home/$USER/.config/VisualParadigm/plugins/" #Linux

function readPath(){ #UNDER DEVELOPMENT
    while true; do
        if [[ -d "$1" ]]; then
            break
        else
            printf "<FOLDER NOT FOUND> Type a valid path!\n"
            read -p "The path to your Visual Paradigm $2: " VISUAL_PARADIGM_APP_DIR_LINUX
        fi
    done
    echo $1
}

function get_VP_App_Path(){
    case "$OS" in
        Linux*)  
            while true; do
                echo "Visual Paradigm Path: $VISUAL_PARADIGM_APP_DIR_LINUX"
                read -p "Confirm (y/n)?: " choice
                case "$choice" in
                    y|Y ) 
                        if [[ -d $VISUAL_PARADIGM_APP_DIR_LINUX ]]; then
                            break
                        else
                            printf "<FOLDER NOT FOUND> Type a valid path!\n"
                        fi
                    ;;
                    n|N ) read -p "The path to your Visual Paradigm (APP FOLDER) is: " VISUAL_PARADIGM_APP_DIR_LINUX ;;
                    * ) printf "Invalid input\n";;
                esac
            done
            app_dir=$VISUAL_PARADIGM_APP_DIR_LINUX
        ;;
        Darwin*)
            while true; do
                echo "Visual Paradigm Path: $VISUAL_PARADIGM_APP_DIR_MAC"
                read -p "Confirm (y/n)?" choice
                case "$choice" in
                    y|Y ) 
                        if [[ -d $VISUAL_PARADIGM_APP_DIR_MAC ]]; then
                            break
                        else
                            printf "<FOLDER NOT FOUND> Type a valid path!\n"
                        fi
                    ;;
                    n|N ) read -p "The path to your Visual Paradigm (APP FOLDER) is: " VISUAL_PARADIGM_APP_DIR_MAC ;;
                    * ) printf "Invalid input\n";;
                esac
            done
            app_dir=$VISUAL_PARADIGM_APP_DIR_MAC
        ;;
        MINGW64*)
            while true; do
                echo "Visual Paradigm Path: $VISUAL_PARADIGM_APP_DIR_WINDOWS"
                read -r -p "Confirm (y/n)?: " choice
                case "$choice" in
                    y|Y ) 
                        if [[ -d $VISUAL_PARADIGM_APP_DIR_WINDOWS ]]; then
                            break
                        else
                            printf "<FOLDER NOT FOUND> Type a valid path!\n"
                        fi
                    ;;
                    n|N ) read -r -p "The path to your Visual Paradigm (APP FOLDER) is: " VISUAL_PARADIGM_APP_DIR_WINDOWS ;;
                    * ) printf "Invalid input\n";;
                esac
            done
            app_dir=$VISUAL_PARADIGM_APP_DIR_WINDOWS
            app_dir=$(echo "$app_dir" | sed 's/\\/\\\\/g')
        ;;
        *)
            echo "Operating System not Supported"
            exit 1
    esac
}

function get_VP_Plugin_Path(){
    case "$OS" in
        Linux*)  
            while true; do
                echo "Visual Paradigm Plugin Path: $VISUAL_PARADIGM_PLUGIN_DIR_LINUX"
                read -p "Confirm (y/n)?: " choice
                case "$choice" in
                    y|Y ) break;;
                    n|N ) read -p "The path to your Visual Paradigm (PLUGIN FOLDER) is: " VISUAL_PARADIGM_PLUGIN_DIR_LINUX ;;
                    * ) printf "Invalid input\n";;
                esac
            done
            plugin_dir=$VISUAL_PARADIGM_PLUGIN_DIR_LINUX
        ;;
        Darwin*)
            while true; do
                echo "Visual Paradigm Plugin Path: $VISUAL_PARADIGM_PLUGIN_DIR_MAC"
                read -p "Confirm (y/n)?: " choice
                case "$choice" in
                    y|Y ) break;;
                    n|N ) read -p "The path to your Visual Paradigm (PLUGIN FOLDER) is: " VISUAL_PARADIGM_PLUGIN_DIR_MAC ;;
                    * ) printf "Invalid input\n";;
                esac
            done
            plugin_dir=$VISUAL_PARADIGM_PLUGIN_DIR_MAC
        ;;
        MINGW64*)
            while true; do
                echo "Visual Paradigm Plugin Path: $VISUAL_PARADIGM_PLUGIN_DIR_WINDOWS"
                read -p "Confirm (y/n)?: " choice
                case "$choice" in
                    y|Y ) break;;
                    n|N ) read -r -p "The path to your Visual Paradigm (PLUGIN FOLDER) is: " VISUAL_PARADIGM_PLUGIN_DIR_WINDOWS ;;
                    * ) printf "Invalid input\n";;
                esac
            done
            plugin_dir=$VISUAL_PARADIGM_PLUGIN_DIR_WINDOWS
            plugin_dir=$(echo "$plugin_dir" | sed 's/\\/\\\\/g')
        ;;
        *)
            echo "Operating System not Supported"
            exit 1
    esac
}

function download_plugin(){
    echo "Downloading FrameWeb VP Plugin Repository ..."
    case "$(basename $(pwd))" in
        $repo_name*) # Case frameweb-vp-plugin or frameweb-vp-plugin-main
            echo "FrameWeb Repository already downloaded, running the script ..."
        ;;
        *)
            echo "Downloading the FrameWeb Repository ..." 
            rm -rf $repo_name-main
            curl -sL $repo_url -o main-frameweb-temp.zip
            unzip main-frameweb-temp.zip
            rm -rf main-frameweb-temp.zip
            cd $repo_name-main
    esac
}

# If the install fails, then print an error and exit.
function install_fail() {
    echo "Installation failed" 
    exit 1 
} 

# This is the help fuction. It helps users withe the options
function Help(){ 
    echo "Usage: ./install.sh" 
    echo "Make sure u have permission to execute install.sh"
    echo "If u had problems try it: "chmod +x install.sh" or allow execution in properties"
}

function install_maven(){
    if command -v mvn &> /dev/null; then
        echo "Maven already installed."
    else
        case "$OS" in
            Linux*)
                sudo apt-get install maven
            ;;
            Darwin*)
                # Instalação no Mac
                brew install maven
            ;;
            MINGW64*)
                echo "Maven automatic installion is not supported by Windows yet."
                echo "Follow this tutorial and try it again ..."
                open https://phoenixnap.com/kb/install-maven-windows
                exit 1
            ;;
        *)
            echo "Operating System not Supported"
            exit 1
        esac
    fi
}

function install_jdk(){
    if command -v javac &> /dev/null; then
        echo "JDK already installed."
    else
        case "$OS" in
            Linux*)
                sudo apt-get install default-jdk 
            ;;
            Darwin*)
                # Instalação no Mac
                brew install java
            ;;
            MINGW64*)
                echo "Maven automatic installion is not supported by Windows yet."
                echo "Follow this tutorial and try it again ..."
                open https://phoenixnap.com/kb/install-java-windows
                exit 1
            ;;
        *)
            echo "Operating System not Supported"
            exit 1
        esac
    fi
}

function install_frameweb_vp_plugin(){
    # Get the paths to write on pom.xml
    get_VP_App_Path
    get_VP_Plugin_Path
    if [ -d  "$plugin_dir$frameweb_plugin_path" ]; then
        echo "<WARNING> FRAMEWEB PLUGIN INSTALLED!"
        while true; do
            read -p "Do you want proceed installation (y/n)?: " choice
            case "$choice" in
                y|Y ) break;;
                n|N ) greetings ;;
                * ) printf "Invalid input\n";;
            esac
        done
    fi
    # Config pom.xml with gathered paths
    case "$OS" in 
        Darwin*) 
            sed -i '' "s|<!-- APP_PATH -->|$app_dir|g" pom.xml # '' Before the regex is to prevent ISSUE on MacOS.
            sed -i '' "s|<!-- PLUGIN_PATH -->|$plugin_dir|g" pom.xml # '' Before the regex is to prevent ISSUE on MacOS
            ;;
        *)
            sed -i "s|<!-- APP_PATH -->|$app_dir|g" pom.xml
            sed -i "s|<!-- PLUGIN_PATH -->|$plugin_dir|g" pom.xml
            #sed -i "s|<visualparadigm.app.dir>.*</visualparadigm.app.dir>|<visualparadigm.app.dir>$app_dir</visualparadigm.app.dir>|g" pom.xml
    esac
    # Install plugin with maven
    mvn install
}

function install_visual_paradigm(){
    echo "Opening Visual Paradigm WebSite"
    open https://www.visual-paradigm.com/download/
    # https://www.visual-paradigm.com/download/?platform=linux&arch=64bit #Linux -> Visual_Paradigm_17_0_20230401_Linux64.sh
}

function install_brew(){
    # Check if homebrew is installed
    if [ ! command -v brew &> /dev/null ]; then
        echo "Installing Homebrew ..."
        # if it's is not installed, then install it.
        /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
        # Check for what arcitecture, so you can place path.
        #if [[ "uname -m" == "x86_64" ]]; then
        #    echo "export PATH=/usr/local/bin:$PATH" >> ~/.bash_profile && source ~/.bash_profile
        #fi
    # If not
    else
        # Print that it's already installed
        echo "Homebrew is already installed"
    fi
}

# Function to install for debian based distrOS (and ubuntu)
function install_shell_deps(){ 
    if [[ "$OSTYPE" == "darwin"* ]]; then
        # Instalação no Mac
        install_brew
    elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
        # Instalação no Linux
        sudo apt-get install unzip
    fi
}

function clean_installation(){
    printf "\nDo you want to clean installation files?\n"
    read -p "Confirm (y/n)?: " choice
    case "$choice" in
        y|Y )
            case "$(basename $(pwd))" in
                $repo_name*) # Case frameweb-vp-plugin or frameweb-vp-plugin-main
                    echo "Cleaning temporary files, downloads, zips and frameweb-vp-plugin installation ..."
                    cd ..
                    rm -rf $repo_name-main
                ;;
            esac
        ;;
        n|N ) ;;
        * ) printf "Invalid input\n"
    esac
    
}

function greetings(){
    printf "\n\n==> FrameWeb was installed with SUCCESS !!!\n"
    echo "==> Contribute with us giving this repo a Star ⭐"
    echo "Contributors:"
    printf "\t - Vitor E. Silva Souza     |  @vitorsouza\n"
    printf "\t - Igor Sunderhus e Silva,  |  @igorssilva\n"
    printf "\t - Lucas R. de Almeida,     |  @propilideno\n"
    exit 1
}

# Main function
function install_main(){ 
    echo "=== Frameweb-vp-plugin Installer ==="
    echo "Installing shell dependencies ..."
    install_shell_deps || install_fail
    echo "Installing plugin dependencies ..."
    install_jdk || install_fail
    install_maven || install_fail
    #install_visual_paradigm || install_fail
    # Download the frameweb-vp-plugin repository
    download_plugin
    echo "Installing frameweb-vp-plugin..."
    install_frameweb_vp_plugin || install_fail
    echo "Your app dir: $app_dir"
    echo "Your plugin dir: $plugin_dir"
    # Cleaning installation
    clean_installation
    # Run the greetings by using FrameWeb
    greetings
}

# Run the main function
install_main
