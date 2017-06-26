package ServidorPorPares;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.Socket;

public class ThreadServidorOthello implements Runnable,Serializable {
	/*
	 * Thread que gestiona la comunicació de SrvTcPAdivina.java i un cllient
	 * ClientTcpAdivina.java
	 */
	
	//El thread es quien tiene que dar turno al jugador y quien se encarga de pasar el tablero

	Socket clientSocket = null;
	InputStream in = null;
	OutputStream out = null;
	JugadaOthello jo;
	String msgSortint;
	
	// objeto tablero othello
	TableroOthello to;
	SrvTcpAdivina server;
	boolean acabat=false;;
	int numeroJugador;
	Boolean succes =false;
	int indiceTurno;
	
	public ThreadServidorOthello(Socket clientSocket, TableroOthello to,int jugador,SrvTcpAdivina server) throws IOException {
		this.clientSocket = clientSocket;
		this.to = to;
		//coge la respuesta del client socket
		in = clientSocket.getInputStream();
		//envia la respuesta
		out = clientSocket.getOutputStream();
		this.numeroJugador=jugador;
		this.server=server;
		succes=server.añadirTurno();
		this.indiceTurno = server.indiceTurno;
		this.to = server.listaTableros.get(this.indiceTurno);
		System.out.println("Bienvenido Jugador :"+numeroJugador);
		System.out.println("Jugador del servidor :"+server.jugador);
	}
	@Override
	public void run() {
		try {
			Boolean turno = null;
			//Boolean firsTime=true;
			while (!acabat) {
				
				//ENVIAMOS MENSAJE JUGADOR
				System.out.println("Enviamos tablero ...");
				ObjectOutputStream oos = new ObjectOutputStream(out);
				//le añado el servidor para despues poder mirar su indice turno etc
				server.listaTableros.get(this.indiceTurno).setServer(server);
				oos.writeObject(server.listaTableros.get(this.indiceTurno));
				oos.flush();
				//sumo un indice al server
				if(succes){
					server.indiceTurno++;
					succes=false;
				}
				//ESPERAMOS JUGADA CLIENTE
				System.out.println("esperamos a recibir la jugada ...");
				ObjectInputStream ois=new ObjectInputStream(in);
				jo = (JugadaOthello) ois.readObject();
				System.out.println("jugada recibida ...");
				
				//Esto me devolvera el turno que hay actualmente
				turno=server.listaTurnos.get(jo.getIndiceTurno());
				//cambiamos el turno solo si es necesario
				cambiarTurnos(turno,jo.getNumeroJugador(),jo.getIndiceTurno());
				
				//Escribe en el tablero la jugada
				generaResposta(jo);
				
				//quedara en espera el thread con el tablero actualizado
				System.out.println("Comprobamos si el thread quedara en espera");
				comprobarTurno(server.listaTurnos.get(jo.getIndiceTurno()),jo.getNumeroJugador(),jo.getIndiceTurno());
			}
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(msgEntrant + " - intents: " + intentsJugador);
		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//CAMBIAMOS LOS TURNOS SEGUN EL JUGADOR QUE HAYA JUGADO
	public synchronized void cambiarTurnos(Boolean turno,int numeroJugador,int indiceTurno){
		System.out.println("Comprobamos si es necesario cambiar el turno");
		System.out.println("el turno en cambiar turnos es : "+turno);
		System.out.println("NumeroJugador"+numeroJugador);
		if(numeroJugador%2==0 && turno){
			server.listaTurnos.set(indiceTurno, false);
			System.out.println(server.listaTurnos.get(indiceTurno));
			System.out.println("puesto a false");
		}
		else if(numeroJugador%2!=0 && !turno){
			server.listaTurnos.set(indiceTurno, true);
			System.out.println("puesto a true");
		}
		notifyAll();
	}
	
	//SI EL JUGADOR ES INPAR Y SU TURNO ES TRUE NO PODRA JUGAR
	//LO MISMO SUCEDE A LA INVERSA
	public synchronized void comprobarTurno(Boolean turno,int numeroJugador,int indiceTurno) throws InterruptedException{
		System.out.println("El booelano turno es : "+turno);
		while((numeroJugador%2!=0 && server.listaTurnos.get(indiceTurno)) || (numeroJugador%2==0 && !server.listaTurnos.get(indiceTurno))){
			Thread.sleep(1500);
			System.out.println("entro en espera");
			if((numeroJugador%2!=0 && server.listaTurnos.get(indiceTurno))){
				System.out.println(server.listaTurnos.get(indiceTurno));
			}
		}
		notifyAll();
		System.out.println("Salgo de la espera");
	}
	public String generaResposta(JugadaOthello en) {
		String ret = null;
		if (en == null){
			ret = "Benvingut al joc!";
		}
		else if(en!=null) {
			ret = to.comprova(en);
			if (ret.equals("Campio")) {
				acabat = true;
			}
		}
		return ret;
	}
}
