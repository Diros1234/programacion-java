package ServidorPorPares;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class clientTcpOthello extends Thread {
	/* CLient TCP que ha endevinar un número pensat per SrvTcpAdivina.java */

	String hostname;
	int port;
	private Socket socket;
	private InputStream in;
	private OutputStream out;
	boolean continueConnected;
	int intents;
	JugadaOthello jo;
	TableroOthello to;
	int indiceTurno;
	int numeroJugador;
	SrvTcpAdivina server;
	Boolean firstTime = true;

	public clientTcpOthello(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		continueConnected = true;
		intents = 0;
	}

	public void run() {
		String serverData;
		String request;
		try {
			// en este momento el jugador lanza peticion al servidor...
			jo = new JugadaOthello();
			socket = new Socket(InetAddress.getByName(hostname), port);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			// el client atén el port fins que decideix finalitzar
			while (continueConnected) {
				// recibimos el tablero des de el thread
				ObjectInputStream ois = new ObjectInputStream(in);
				to = (TableroOthello) ois.readObject();
				this.server = to.server;
				// Cogemos del servidor nuestro indice y nuestro numero de
				// jugador
				//añadimos las vairables al cliente
				añadirVariables();
				
				// mostramos el tablero y devolvemos la jugada
				// siempre que devolvemos jugada devolvemos también el numero
				// del jugador y su indice
				//mostramos tablero
				jo = getRequest(to);
				jo.setNumeroJugador(numeroJugador);
				jo.setIndiceTurno(indiceTurno);
				if(numeroJugador%2==0){
					System.out.println("Esperando al jugador : "+(numeroJugador-1)+"...");
				}
				else{
					System.out.println("Esperando al jugador : "+(numeroJugador+1)+"...");
				}
				
				// processament de les dades rebudes i obtenció d'una nova
				// petició
				// request = getRequest(serverData);
				// enviamos la jugada al servidor
				ObjectOutputStream oos = new ObjectOutputStream(out);
				oos.writeObject(jo);
				oos.flush();
			}
			close(socket);
		} catch (UnknownHostException ex) {
			System.out.println("Error de connexió. No existeix el host: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("Error de connexió indefinit: " + ex.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//solo nos interesa cuando es creado el jugador y por ello cogemos sus valores
	public void añadirVariables() {
		if (firstTime) {
			this.indiceTurno = to.getServer().indiceTurno;
			this.numeroJugador = to.getServer().jugador;
			firstTime = false;
		}
		System.out.println(to.getServer().indiceTurno);
		System.out.println(to.getServer().jugador);
	}

	// subfuncion de JugadaOthello

	public JugadaOthello getRequest(TableroOthello serverData) throws NumberFormatException, IOException {
		String ret;
		JugadaOthello jo = null;
		serverData.mostrarTablero();
		if (serverData.equals("Correcte")) {
			continueConnected = false;
			jo.name = "Campió!";
		} else {
			//Scanner in = new Scanner(System.in);
			System.out.println("Escolleix un numero disponible");
			BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
			int numero = Integer.parseInt(bfr.readLine());
			// Aqui le pasaremos la coordenada con el objeto
			jo = new JugadaOthello(numero - 1);
			// server.listaTurnos.get(indiceTurno).;
			///////////////////////////////////////////////////
			// ret = new String(in.next());
		}
		return jo;
	}

	public boolean mustFinish(String dades) {
		if (dades.equals("exit"))
			return false;
		return true;

	}

	private void close(Socket socket) {
		// si falla el tancament no podem fer gaire cosa, només enregistrar
		// el problema
		try {
			// tancament de tots els recursos
			if (socket != null && !socket.isClosed()) {
				if (!socket.isInputShutdown()) {
					socket.shutdownInput();
				}
				if (!socket.isOutputShutdown()) {
					socket.shutdownOutput();
				}
				socket.close();
			}
		} catch (IOException ex) {
			// enregistrem l'error amb un objecte Logger
			Logger.getLogger(clientTcpOthello.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void main(String[] args) {
		/*
		 * if (args.length != 2) { System.err.println(
		 * "Usage: java ClientTcpAdivina <host name> <port number>");
		 * System.exit(1); }
		 */

		// String hostName = args[0];
		// int portNumber = Integer.parseInt(args[1]);
		clientTcpOthello clientTcp = new clientTcpOthello("localhost", 5558);
		clientTcp.start();
	}
}