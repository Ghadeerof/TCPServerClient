/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tcpserverclient;

import com.sun.org.apache.bcel.internal.classfile.Constant;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ghadeer
 */
public class Client {
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    private Scanner scan = null;
    
    public Client(){
        scan = new Scanner(System.in);
    }
    
    public static void main(String[] args){
        Client client = new Client();
        
        client.startConnection();
        client.connecting();
        client.stopConnection();
    }
    
    private void stopConnection(){
        closeStreams();
        clostSocket();
        log("close client");
    }
    
    private void startConnection(){
        openSocket();
        
        if(socket != null){
            openStreams();
            log("Start Client");
        }
    }
    
    private void connecting(){
        String line = "";
        while(! line.equals(Constants.STOP)){
            write();
            line = read();
            log("Server : " + line);
        }
    }
    
    
    private String read() {
        String line = "";

        try {
            line = input.readUTF();
        } catch (IOException ex) {
            log(ex.getMessage());
        }
        return line;
    }
    
    private void write(){
        try {
            String msg = scan.nextLine();
            
            output.writeUTF(msg);
            output.flush();
            log("Client : " + msg);
        } catch (IOException ex) {
            log(ex.getMessage());
        }
    }
    
    private void clostSocket(){
        try {
            socket.close();
        } catch (IOException ex) {
            log(ex.getMessage());
        }
    }
    
    private void openSocket(){
        try {
            socket = new Socket(Constants.IP, Constants.PORT);
            
        } catch (IOException ex) {
            log(ex.getMessage());
        }
    }
    private void closeStreams(){
        try {
            input.close();
            output.close();
        } catch (IOException ex) {
            log(ex.getMessage());
        }
    }
    private void openStreams(){
        openInputStream();
        openOutputStream();
    }
    private void openInputStream(){
        try {
            input = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            log(ex.getMessage());
        }
    }
    private void openOutputStream(){
        try {
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            log(ex.getMessage());
        }
    }
    
    private void log(String msg){
        System.out.println(msg);
    }
}
