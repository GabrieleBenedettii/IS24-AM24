package it.polimi.ingsw.am24.view.GUI.controllers;

import it.polimi.ingsw.am24.view.input.InputReaderGUI;

public abstract class GUIController {
    private InputReaderGUI inputReaderGUI;

    public InputReaderGUI getInputReaderGUI() {
        return inputReaderGUI;
    }
    public void setInputReaderGUI(InputReaderGUI inputReaderGUI) {this.inputReaderGUI = inputReaderGUI;}
}
