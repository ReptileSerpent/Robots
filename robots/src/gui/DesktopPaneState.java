package gui;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.*;
import java.util.HashMap;

public class DesktopPaneState
{
    public static void WriteToFile(JDesktopPane desktopPane) throws IOException
    {
        var file = new File(System.getProperty("user.home") + "/RobotsDesktopPaneState.txt");
        var outputStream = new FileOutputStream(file);
        var dataOutputStream = new DataOutputStream(new BufferedOutputStream(outputStream));
        for (var frame : desktopPane.getAllFrames())
        {
            dataOutputStream.writeUTF(frame.getTitle());
            dataOutputStream.writeInt(frame.getX());
            dataOutputStream.writeInt(frame.getY());
            dataOutputStream.writeInt(frame.getWidth());
            dataOutputStream.writeInt(frame.getHeight());
            dataOutputStream.writeBoolean(frame.isSelected());
            dataOutputStream.writeBoolean(frame.isMaximum());
            dataOutputStream.writeBoolean(frame.isIcon());
        }
        dataOutputStream.close();
        outputStream.close();
    }

    public static HashMap<String, FrameState> ReadFromFile(JDesktopPane desktopPane) throws IOException
    {
        var frameStates = new HashMap<String, FrameState>();

        var file = new File(System.getProperty("user.home") + "/RobotsDesktopPaneState.txt");
        var inputStream = new FileInputStream(file);
        try
        {
            var dataInputStream = new DataInputStream(new BufferedInputStream(inputStream));
            try
            {
                var hasReachedEOF = false;
                while (!hasReachedEOF) {
                    var frameWasReadCompletely = true;
                    try
                    {
                        var title = dataInputStream.readUTF();
                        frameWasReadCompletely = false;
                        var x = dataInputStream.readInt();
                        var y = dataInputStream.readInt();
                        var width = dataInputStream.readInt();
                        var height = dataInputStream.readInt();
                        var isSelected = dataInputStream.readBoolean();
                        var isMaximum = dataInputStream.readBoolean();
                        var isIcon = dataInputStream.readBoolean();
                        frameStates.put(title, new FrameState(x, y, width, height, isSelected, isMaximum, isIcon));
                        frameWasReadCompletely = true;
                    }
                    catch (EOFException e)
                    {
                        hasReachedEOF = true;
                        if (!frameWasReadCompletely)
                            throw new IOException("The file format is incorrect: one of the frame states is incomplete");
                    }
                }
            }
            finally
            {
                dataInputStream.close();
            }
        }
        finally
        {
            inputStream.close();
        }

        return frameStates;
    }

    public static void ModifyDesktopPaneState(JDesktopPane desktopPane, HashMap<String, FrameState> frameStates)
    {
        for (var frame : desktopPane.getAllFrames())
        {
            if (!frameStates.containsKey(frame.getTitle()))
                continue;

            var newFrameState = frameStates.get(frame.getTitle());
            frame.setLocation(new Point(newFrameState.x, newFrameState.y));
            frame.setSize(newFrameState.width, newFrameState.height);
            try
            {
                frame.setIcon(newFrameState.isIcon);
                frame.setSelected(newFrameState.isSelected);
                frame.setMaximum(newFrameState.isMaximum);
            }
            catch (PropertyVetoException e)
            {
                e.printStackTrace();
            }
        }
    }
}
