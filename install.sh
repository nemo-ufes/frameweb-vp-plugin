#!/bin/bash

os=$(uname -s) # Gather OS Name

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
    #App Default Path
    local VISUAL_PARADIGM_APP_DIR_WINDOWS="C:\\Program Files\\Visual Paradigm CE 17.0\\"
    local VISUAL_PARADIGM_APP_DIR_MAC="/Applications/Visual Paradigm.app/Contents/Resources/app/"
    local VISUAL_PARADIGM_APP_DIR_LINUX="/home/$USER/Visual_Paradigm_17.0/"
    case "$os" in
        Linux*)  
            while true; do
                echo "Visual Paradigm Path: $VISUAL_PARADIGM_APP_DIR_LINUX"
                read -p "Confirm (y/n)?" choice
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
                    y|Y ) break;;
                    n|N ) read -p "The path to your Visual Paradigm (APP FOLDER) is: " VISUAL_PARADIGM_APP_DIR_MAC ;;
                    * ) printf "Invalid input\n";;
                esac
                if [[ -d $VISUAL_PARADIGM_APP_DIR_MAC ]]; then
                    break
                else
                    printf "<FOLDER NOT FOUND> Type a valid path!\n"
                fi
            done
            echo $VISUAL_PARADIGM_APP_DIR_MAC
        ;;
        CYGWIN*)
            while true; do
                echo "Visual Paradigm Path: $VISUAL_PARADIGM_APP_DIR_WINDOWS"
                read -p "Confirm (y/n)?" choice
                case "$choice" in
                    y|Y ) break;;
                    n|N ) read -p "The path to your Visual Paradigm (APP FOLDER) is: " VISUAL_PARADIGM_APP_DIR_WINDOWS ;;
                    * ) printf "Invalid input\n";;
                esac
                if [[ -d $VISUAL_PARADIGM_APP_DIR_WINDOWS ]]; then
                    break
                else
                    printf "<FOLDER NOT FOUND> Type a valid path!\n"
                fi
            done
            echo $VISUAL_PARADIGM_APP_DIR_WINDOWS
        ;;
        *)
            echo "Operating System not Supported"
            exit 1
    esac
}

function get_VP_Plugin_Path(){
    #Plugin Default Path
    local VISUAL_PARADIGM_PLUGIN_DIR_WINDOWS="C:\\Users\\%USERNAME%\\AppData\\Roaming\\VisualParadigm\\plugins\\" #Windows
    local VISUAL_PARADIGM_PLUGIN_DIR_MAC="/Users/$USER/Library/Application Support/VisualParadigm/plugins/" #UNIX
    local VISUAL_PARADIGM_PLUGIN_DIR_LINUX="/home/$USER/.config/VisualParadigm/plugins/" #UNIX
    case "$os" in
        Linux*)  
            while true; do
                echo "Visual Paradigm Plugin Path: $VISUAL_PARADIGM_PLUGIN_DIR_LINUX"
                read -p "Confirm (y/n)?" choice
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
                echo "Visual Paradigm Plugin Path: $(VISUAL_PARADIGM_PLUGIN_DIR_LINUX)" 
                read -p "Confirm (y/n)?" choice
                case "$choice" in
                    y|Y ) break;;
                    n|N ) read -p "The path to your Visual Paradigm (PLUGIN FOLDER) is: " VISUAL_PARADIGM_PLUGIN_DIR_LINUX ;;
                    * ) printf "Invalid input\n";;
                esac
            done
            echo $VISUAL_PARADIGM_PLUGIN_DIR_LINUX # Return Value
        ;;
        CYGWIN*)
            while true; do
                echo "Visual Paradigm Plugin Path: $VISUAL_PARADIGM_PLUGIN_DIR_LINUX" 
                read -p "Confirm (y/n)?" $answer
                if [ "$answer" == "y" ]; then
                    break
                else
                    if [[ ! -d $VISUAL_PARADIGM_PLUGIN_DIR_LINUX ]]; then
                        printf "<FOLDER NOT FOUND> Type a valid path!\n"
                        read -p "The path to your Visual Paradigm (PLUGIN FOLDER) is: " VISUAL_PARADIGM_PLUGIN_DIR_LINUX
                    fi
                fi
            done
            echo $VISUAL_PARADIGM_PLUGIN_DIR_LINUX # Return Value
        ;;
        *)
            echo "Operating System not Supported"
            exit 1
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
        # Instalação de dependências
        if [[ "$OSTYPE" == "darwin"* ]]; then
            # Instalação no Mac
            brew install maven
        elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
            # Instalação no Linux
            sudo apt-get install maven
        elif [[ "$OSTYPE" == "cygwin" ]]; then
            # Instalação no Windows usando Cygwin
            echo "Not supported yet"
            exit 1
        else
            echo "Sistema operacional não suportado"
            exit 1
        fi
    fi
}

function install_jdk(){
    if command -v javac &> /dev/null; then
        echo "JDK already installed."
    else
        # Instalação de dependências
        if [[ "$OSTYPE" == "darwin"* ]]; then
            # Instalação no Mac
            brew install java
        elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
            # Instalação no Linux
            sudo apt-get install default-jdk
        elif [[ "$OSTYPE" == "cygwin" ]]; then
            # Instalação no Windows
            echo "Not supported yet"
            exit 1
        else
            echo "Sistema operacional não suportado"
            exit 1
        fi
    fi
}

function install_frameweb_vp_plugin(){
    # Name of frameweb plugin
    local frameweb_plugin_path="frameweb-vp-plugin-0.1/" #Change this line
    # Get the paths to write on pom.xml
    get_VP_App_Path
    get_VP_Plugin_Path
    if [ -d  $plugin_dir$frameweb_plugin_path ]; then
        echo "<WARNING> FRAMEWEB PLUGIN INSTALLED!"
        install_fail
    else

        # Config pom.xml with gathered paths
        sed -i "s|<!-- APP_PATH -->|$app_dir|g" pom.xml
        sed -i "s|<!-- PLUGIN_PATH -->|$plugin_dir|g" pom.xml
        #sed -i "s|<visualparadigm.app.dir>.*</visualparadigm.app.dir>|<visualparadigm.app.dir>$app_dir</visualparadigm.app.dir>|g" pom.xml

        # Install plugin with maven
        mvn install
    fi
}

function install_visual_paradigm(){
    echo "Opening Visual Paradigm WebSite"
    open https://www.visual-paradigm.com/download/
}

function install_brew(){
    # Check if homebrew is installed
    if [ ! command -v brew &> /dev/null ]; then
        echo "Installing Homebrew ..."
        # if it's is not installed, then install it.
        /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/uninstall.sh)" 
        # Check for what arcitecture, so you can place path.
        if [[ "uname -m" == "x86_64" ]]; then
            echo "export PATH=/usr/local/bin:$PATH" >> ~/.bash_profile && source ~/.bash_profile
        fi
    # If not
    else
        # Print that it's already installed
        echo "Homebrew is already installed"
    fi
}

# Function to install for debian based distros (and ubuntu)
function install_shell_deps(){ 
    if [[ "$OSTYPE" == "darwin"* ]]; then
        # Instalação no Mac
        install_brew
    elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
        # Instalação no Linux
        echo "No shell required dependencies on linux"
    fi
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
    echo "Installing frameweb-vp-plugin..."
    install_frameweb_vp_plugin || install_fail
    echo "Your app dir: $app_dir"
    echo "Your plugin dir: $plugin_dir"
}

# Run the main function
install_main