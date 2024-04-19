package org.robotsteam;

import org.robotsteam.gui.MainApplicationFrame;
import org.robotsteam.gui.states.AppState;

import java.io.*;

public class AppLoader {
    public static MainApplicationFrame deserializeApp() {
        try (FileInputStream file = new FileInputStream("app.dat")) {
            ObjectInputStream inStream = new ObjectInputStream(file);
            return new MainApplicationFrame((AppState)inStream.readObject());
        }
        catch (IOException | ClassNotFoundException e) { return new MainApplicationFrame(); }
    }

    public static void serializeApp(AppState appState) {
        try (FileOutputStream file = new FileOutputStream("app.dat")) {
            ObjectOutputStream outStream = new ObjectOutputStream(file);
            outStream.writeObject(appState);
        }
        catch (IOException e){ e.printStackTrace(System.out); }
    }
}
