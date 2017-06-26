package ServidorPorPares;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.LineNumberInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableroOthello implements Serializable {
	List<String> tablero = new ArrayList();
	SrvTcpAdivina server;
	Boolean turno=false;
	public static BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));

	public TableroOthello(List<String> tablero, SrvTcpAdivina server) {
		super();
		this.tablero = tablero;
		this.server = server;

		//int numero;
		// for (int i = 0; i < 8; i++) {
		// for (int j = 0; j < 8; j++) {
		// numero = (i * 8) + (j + 1);
		// this.tablero[i][j] = String.valueOf(numero);
		// }
		// }
		for (int i = 1; i < 65; i++) {
			tablero.add(String.valueOf(i));
		}
	}

	// public String[][] getTablero() {
	// return tablero;
	// }
	//
	// public void setTablero(String[][] tablero) {
	// this.tablero = tablero;
	// }
	//
	public SrvTcpAdivina getServer() {
		return server;
	}

	public void setServer(SrvTcpAdivina server) {
		this.server = server;
	}

	public TableroOthello() {
		super();
		for (int i = 1; i < 65; i++) {
			tablero.add(String.valueOf(i));
		}
	}

	public Boolean comprovarCasilla(int numero) {
		Boolean valido = false;
		
		if((tablero.get(numero)!="X" || tablero.get(numero)!="O") || Integer.parseInt(tablero.get(numero))==numero){
			valido=true;
		}
		return valido;
	}

	public String comprova(JugadaOthello jugada) {
		// Aqui comprobamos que la jugada es la correcta
		// INCORRECTO,Si ya esta jugada,devolvemos Incorrecto
		// CORRECTO,si la jugada esta bien hecha
		// CAMPEON SI HA GANADO
		// comprobaciones internas del tablero aqui si ha ganado devolvemos
		// campio !!
		// miramos que no este chafando una ficha...
		
		int numero = jugada.getNumero();
		Boolean libre = this.comprovarCasilla(numero);
		//Como es true no chafa ninguna ficha
		if (libre) {
			//SERIA ASI CON TURNOS IMPLEMENTADOS
			//if(turno){
			//tablero.add(Integer.parseInt(numero), "X");
			//}
//			else{
//				tablero.add(Integer.parseInt(numero), "O");
//			}
			
			tablero.set(numero,"X");
		}
		// }
		// if(tablero[][]){
		//
		// }
		// else if(tablero[][]){
		//
		// }
		// int numero = jugada.getNumero();
		System.out.println("numero : " + numero);
		// if(numero<10)
		return "Campion";
	}
//	public String comprova(JugadaOthello jugada) {
//	    // Aqui comprobamos que la jugada es la correcta
//	    // INCORRECTO,Si ya esta jugada,devolvemos Incorrecto
//	    // CORRECTO,si la jugada esta bien hecha
//	    // CAMPEON SI HA GANADO
//	    // comprobaciones internas del tablero aqui si ha ganado devolvemos
//	    // campio !!
//	    // miramos que no este chafando una ficha...
//
//	    int numero = jugada.getNumero();
//	    Boolean libre = this.comprovarCasilla(numero);
//	    // Como es true no chafa ninguna ficha
//	    if (libre) {
//	      // SERIA ASI CON TURNOS IMPLEMENTADOS
//	      // if(turno){
//	      // tablero.add(Integer.parseInt(numero), "X");
//	      // }
//	      // else{
//	      // tablero.add(Integer.parseInt(numero), "O");
//	      // }
//	      
//	      
//	      //String s1 = "X" si es true el player X, s2= "O"
////	      Al revés si false, para cambiar los players sin spamear el string
//	      
//	      String s1 = "X";
//	      String s2 = "O";
//
//	      if (tablero.get(numero - 1).equals(s2) && tablero.get(numero - 2).equals(s1)) {
//	        tablero.set(numero, s1);
//	        tablero.set(numero - 1, s1);
//	      }
//	      if (tablero.get(numero + 1).equals(s2) && tablero.get(numero + 2).equals(s1)) {
//	        tablero.set(numero, s1);
//	        tablero.set(numero + 1, s1);
//	      }
//
//	      if (tablero.get(numero - 8).equals(s2) && tablero.get(numero - 16).equals(s1)) {
//	        tablero.set(numero, s1);
//	        tablero.set(numero - 8, s1);
//	      }
//
//	      if (tablero.get(numero + 8).equals(s2) && tablero.get(numero + 16).equals(s1)) {
//	        tablero.set(numero, s1);
//	        tablero.set(numero + 8, s1);
//	      }
//
//	      
//
//	      //tablero.set(numero, "X");
//
//	    }
//	    // }
//	    // if(tablero[][]){
//	    //
//	    // }
//	    // else if(tablero[][]){
//	    //
//	    // }
//	    // int numero = jugada.getNumero();
//	    System.out.println("numero : " + numero);
//	    // if(numero<10)
//	    return "Campion";
//	  }
	public void mostrarTablero() {
		int residuo = 1;
		int contador=0;
		for (String a : this.tablero) {
			contador++;
			String spaces = String.format("%4s", a);
			System.out.format(spaces, "");
			if(contador%8==0){
				System.out.println("");
			}
			
		}
	}

	public static void main(String[] args) {
		TableroOthello to = new TableroOthello();
		for (String a : to.tablero) {
			// String s = String.format("%1$#"+b+"s", "");
			String spaces = String.format("%4s", a);
			System.out.format(spaces, "");
		}
		System.out.println("");
	}
}
