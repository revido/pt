package pt;

import pt.Menu.Menu;
import pt.config.ConfigManager;

class Pt {

    Menu menu;
    ConfigManager confMan;

    // Initialization of Pt
    public Pt() {
        confMan = new ConfigManager();
        confMan.load();
        menu = new Menu(confMan);
    }

    // Runs the program menu
    public void runProgram() {
        menu.runProgram();
    }


}
