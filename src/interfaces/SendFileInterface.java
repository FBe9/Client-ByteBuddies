package interfaces;

import java.io.File;

/**
 * Interface for managing the transaction of files.
 *
 * @author Alex
 */
public interface SendFileInterface {

    /**
     * The method used to send the file.
     *
     * @param file The file to be sent.
     */
    public void sendFile(File file);

    /**
     * The method used to receive a specific file stored in a given path.
     *
     * @param path The given path.
     * @return The requested file.
     */
    public File receiveFile(String path);
}
