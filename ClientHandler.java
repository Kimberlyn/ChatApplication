import java.io.*; 
import java.util.*; 
import java.net.*; 
import java.util.Date;
import java.text.SimpleDateFormat; 

class ClientHandler implements Runnable  
{ 
    Scanner scn = new Scanner(System.in); 
    private String name; 
    final DataInputStream dis; 
    final DataOutputStream dos; 
    Socket s; 
    boolean isloggedin; 
      
    public ClientHandler(Socket s, String name, 
                            DataInputStream dis, DataOutputStream dos) { 
        this.dis = dis; 
        this.dos = dos; 
        this.name = name; 
        this.s = s; 
        this.isloggedin=true; 
    } 
  
    @Override
    public void run() { 
  
        String received; 
        try
            {
        received = dis.readUTF(); 
        this.name = received;
        } catch (IOException e) { 
        }   
        while (true)  
        { 
            try
            { 
                received = dis.readUTF(); 
 
                System.out.println(received); 
                  
                if(received.equals("QUIT") || received.equals("Quit")){ 
                    for (ClientHandler mc : Server.ar)
                    {
                        if (this.name != mc.name)
                         mc.dos.writeUTF("["+new SimpleDateFormat("MM/dd/YYYY hh:mm:ss").format(new Date())+"] " + this.name+ ": has left the chat.");

                    }  
                    this.isloggedin=false; 
                    this.s.close(); 
                } 
  
                for (ClientHandler mc : Server.ar)  
                { 
                    if (mc.isloggedin==true && this.name != mc.name && !received.equals("QUIT") && !received.equals("Quit"))  
                    { 
                        mc.dos.writeUTF("["+new SimpleDateFormat("MM/dd/YYYY hh:mm:ss").format(new Date())+"] " + this.name+": "+ received); 
                    }
                } 

            } catch (IOException e) { 
            }     
        } 
    } 
}