import java.net.*;
import java.io.*;
import java.util.*;


class jHTTPSMulti extends Thread {
    private Socket socket = null;
    String getAnswer() {
        InetAddress adres;
        String name = "";
        String ip = "";
        try {
            adres = InetAddress.getLocalHost();
            name = adres.getHostName();
           // ip = adres.getHostAddress();
        }
        catch (UnknownHostException ex) { System.err.println(ex); }


        String document = "<html>\r\n" +
                "<body><br>\r\n" +
                "<center><h2><font color=red>Serwer\r\n" +
                "</font></h2>\r\n" +
                "<h3>Serwer na watkach</h3><hr>\r\n" +
                "Data: <b>" + new Date() + "</b><br>\r\n" +
                "Nazwa hosta: <b>" + name + "</b><br>\r\n" +
                "Kalkulator" + "</b><br>\r\n" +
                "<input type='text' id='first' ><br><br>"+
                "<input type='text' id='second' ><br><br>"+
                "<button type='Text' onClick='myFunction()'>Dodaj</button><br><br>"+
                "<button type='Text' onClick='myFunction2()'>Odejmij</button><br><br>"+
                "<button type='Text' onClick='myFunction3()'>Podziel</button><br><br>"+
                "<button type='Text' onClick='myFunction4()'>Pomnoz</button><br><br>"+
                "<script>\n" +
                "function myFunction() {\n" +
                "let var1=document.getElementById('first').value\n"+
                "let var2=document.getElementById('second').value\n"+
                "let var3=parseInt(var1)+parseInt(var2)\n"+
                "  alert(var3);\n" +
                "}\n" +
                "</script>"+

                "<script>\n" +
                "function myFunction2() {\n" +
                "let var1=document.getElementById('first').value\n"+
                "let var2=document.getElementById('second').value\n"+
                "let var3=parseInt(var1)-parseInt(var2)\n"+
                "  alert(var3);\n" +
                "}\n" +
                "</script>"+

                "<script>\n" +
                "function myFunction3() {\n" +
                "let var1=document.getElementById('first').value\n"+
                "let var2=document.getElementById('second').value\n"+
                "if(var2==0){\n"+
                "alert(\"bledne dane\");\n"+
                "}\n" +
                "else{\n"+
                "let var3=parseInt(var1)/parseInt(var2)\n"+
                "  alert(var3);\n" +
                "}\n" +
                "}\n" +
                "</script>"+

                "<script>\n" +
                "function myFunction4() {\n" +
                "let var1=document.getElementById('first').value\n"+
                "let var2=document.getElementById('second').value\n"+
                "let var3=parseInt(var1)*parseInt(var2)\n"+
                "  alert(var3);\n" +
                "}\n" +
                "</script>"+


                "<hr>\r\n" +
                "</body>\r\n" +
                "</html>\r\n";
        String header = "HTTP/1.1 200 OK\r\n" +
                "Server: jHTTPServer ver 1.1\r\n" +
                "Last-Modified: Fri, 28 Jul 2000 07:58:55 GMT\r\n" +
                "Content-Length: " + document.length() + "\r\n" +
                "Connection: close\r\n" +
                "Content-Type: text/html; charset=iso-8859-2";
        return header + "\r\n\r\n" + document;
    }
    public jHTTPSMulti(Socket socket){
        System.out.println("Nowy obiekt jHTTPSMulti...");
        this.socket = socket;
        start();
    }
    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            System.out.println("---------------- Pierwsza linia zapytania ----------------");
            System.out.println(in.readLine());
            System.out.println("---------------- Wysylam odpowiedz -----------------------");
            System.out.println(getAnswer());
            System.out.println("---------------- Koniec odpowiedzi -----------------------");
            out.println(getAnswer());
            out.flush();
        } catch (IOException e) {
            System.out.println("Blad IO danych!");
        }
        finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.out.println("Blad zamkniecia gniazda!");
            }
        } // finally
    }
}
class jHTTPApp {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(80);
        try {
            while (true) {
                Socket socket = server.accept();
                new jHTTPSMulti(socket);
            } // while
        } // try
        finally { server.close();}
    } // main
}




public class Zadanie6 {
}
