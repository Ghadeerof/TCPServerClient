/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tcpserverclient;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ghadeer
 */
public class Server {
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private DataOutputStream output = null;
    private DataInputStream input = null;
    Scanner scan = null;
    
    public Server(){
        scan = new Scanner(System.in);
    }
    
    public static void main(String[] args){
        Server server = new Server();
        
        server.startConnection();
        server.connecting();
        server.stopConnection();
    }
    
    private void stopConnection(){
        closeSocket();
        closeStreams();
    }
    
    private void startConnection(){
        openSocket();
        waitingConnection();
        openStreams();
    }
    
    
    private void connecting(){
        String line = "";
        while(! line.equals(Constants.STOP)){
            line = read();
            log("Client : " + line);
            write();
        }
    }
    
    private void waitingConnection(){
        try {
            log("Waiting for client...");
            
            socket = serverSocket.accept();
        } catch (IOException ex) {
            log(ex.getMessage());
        }
    }
    
    private String read(){
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
        } catch (IOException ex) {
            log(ex.getMessage());
        }
    }
    
    private void closeSocket(){
        try {
            serverSocket.close();
            socket.close();
        } catch (IOException ex) {
            log(ex.getMessage());
        }
    }
    
    private void openSocket(){
        try {
            serverSocket = new ServerSocket(Constants.PORT);
            log("Server Started...");
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
