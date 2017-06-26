package ServidorPorPares;

import java.io.Serializable;

public class JugadaOthello implements Serializable{
String name;
int numero;
int indiceTurno;
int numeroJugador;
public int getNumeroJugador() {
	return numeroJugador;
}

public void setNumeroJugador(int numeroJugador) {
	this.numeroJugador = numeroJugador;
}

public JugadaOthello(String name, int numero) {
	super();
	this.name = name;
	this.numero = numero;
}

	public String getName() {
	return name;
}

public JugadaOthello() {
		super();
	}

public void setName(String name) {
	this.name = name;
}

public int getNumero() {
	return numero;
}

public void setNumero(int numero) {
	this.numero = numero;
}

	public JugadaOthello(int numero) {
		// TODO Auto-generated constructor stub
		this.numero = numero;
	}

	public int getIndiceTurno() {
		return indiceTurno;
	}

	public void setIndiceTurno(int indiceTurno) {
		this.indiceTurno = indiceTurno;
	}

	
}
