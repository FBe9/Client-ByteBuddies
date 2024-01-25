/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.io.File;

/**
 *
 * @author Alex
 */
public interface SendFileInterface {
    
    public void sendFile(File file);
    
    public File receiveFile(String path);
}
