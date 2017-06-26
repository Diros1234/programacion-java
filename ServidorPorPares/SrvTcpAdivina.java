package ServidorPorPares;


import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SrvTcpAdivina implements Serializable{
/* Servidor TCP que genera un número perquè ClientTcpAdivina.java jugui a encertar-lo 
 * i on la comunicació dels diferents jugador passa per el Thread : ThreadServidorAdivina.java
 * */
	SrvTcpAdivina server;
	int port;
	//objeto tablero
	TableroOthello to;
	int jugador;
	//Aqui guardaremos los turnos de cada par de jugadores
	List<Boolean> listaTurnos = new ArrayList();
	List<TableroOthello> listaTableros = new ArrayList();
	//List<ThreadServidorOthello> listaThreads = new ArrayList();
	//List<clientTcpOthello> listaClients = new ArrayList();
	
	
	//Aqui tendremos una lista de tableros
	
	//Aqui tendremos el indice del turno y del tablero que es por parejas de dos
	int indiceTurno;
	public SrvTcpAdivina(int port) {
		this.port = port;
		to = new TableroOthello();
	}
	
	public void listen() {
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		
		try {
			serverSocket = new ServerSocket(port);
			while(true) { 
				//esperar connexió del client i llançar thread
				clientSocket = serverSocket.accept();
				//Llançar Thread per establir la comunicació
				//Cada vez que se lance un thread se sumara uno a jugador y se enviara
				jugador++;
				System.out.println("jugador sumado !!!");
				System.out.println("jugador son: "+jugador);
				
				ThreadServidorOthello FilServidor = new ThreadServidorOthello(clientSocket, to,jugador,this);
				Thread client = new Thread(FilServidor);
				client.start();
			}
		} catch (IOException ex) {
			Logger.getLogger(SrvTcpAdivina.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	//añadimos turno booleano para los dos jugadores
	public  Boolean añadirTurno(){
		Boolean turno=false;
		Boolean succes=false;
		if((jugador%2)!=0){
			listaTurnos.add(turno);
			listaTableros.add(new TableroOthello());
			System.out.println("turno añadido y tablero añadido");
			System.out.println("Esperando otro jugador");
		}
		else{
			System.out.println("Que empiece la partida");
			succes=true;
		}
		return succes;
	}
	public static void main(String[] args) {
		/*if (args.length != 1) {
            System.err.println("Usage: java SrvTcpAdivina <port number>");
            System.exit(1);
        }*/
        //int port = Integer.parseInt(args[0]);
        SrvTcpAdivina srv = new SrvTcpAdivina(5558);
        srv.listen();
	}
}
