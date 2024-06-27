package it.polimi.ingsw.am24.view.GUI.controllers;

import it.polimi.ingsw.am24.view.input.InputReaderGUI;
/**
 * Abstract base class for GUI controllers in the application.
 * Provides access to an InputReaderGUI instance for handling user input.
 */
public abstract class GUIController {

    /**
     * The InputReaderGUI instance associated with this controller.
     */
    private InputReaderGUI inputReaderGUI;

    /**
     * Retrieves the InputReaderGUI instance associated with this controller.
     *
     * @return The InputReaderGUI instance.
     */
    public InputReaderGUI getInputReaderGUI() {
        return inputReaderGUI;
    }

    /**
     * Sets the InputReaderGUI instance for this controller.
     *
     * @param inputReaderGUI The InputReaderGUI instance to set.
     */
    public void setInputReaderGUI(InputReaderGUI inputReaderGUI) {this.inputReaderGUI = inputReaderGUI;}
}
