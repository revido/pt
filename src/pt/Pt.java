package pt;

import pt.Menu.Menu;
import pt.config.ConfigManager;

class Pt {

    private final Menu menu;

    // Initialization of Pt
    public Pt() {
        ConfigManager confMan = new ConfigManager();
        confMan.load();
        menu = new Menu(confMan);
    }

    // Runs the program menu
    public void runProgram() {
        menu.runProgram();
    }


}
