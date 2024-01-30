package implementation;

import interfaces.SendFileInterface;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Implementation of the SendFileInterface
 *
 * @author Alex
 */
public class SendFileInterfaceImplementation implements SendFileInterface {

    /**
     * The method used to send the file.
     *
     * @param file The file to be sent.
     */
    @Override
    public void sendFile(File file) {

    }

    /**
     * The method used to receive a specific file stored in a given path.
     *
     * @param path The given path.
     * @return The requested file.
     */
    @Override
    public File receiveFile(String path) {
        return new File(path);
    }

    // TO BE IMPLEMENTED
    private void fileWriter(String path, byte[] text) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TO BE IMPLEMENTED
    private static byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
