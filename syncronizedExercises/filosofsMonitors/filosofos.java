package filosofsMonitors;

import java.util.ArrayList;
import java.util.List;

public class filosofos extends Thread {
	tenedor t1;
	tenedor t2;
	private String name;
	private int bloqueados = 0;
	public tenedor getT1() {
		return t1;
	}

	public void setT1(tenedor t1) {
		this.t1 = t1;
	}

	public tenedor getT2() {
		return t2;
	}

	public void setT2(tenedor t2) {
		this.t2 = t2;
	}

	public int getBloqueados() {
		return bloqueados;
	}

	public void setBloqueados(int bloqueados) {
		this.bloqueados = bloqueados;
	}
	//se le deben pasar los dos monitores para que se puedan comprobar su estados
	public filosofos(tenedor t1, tenedor t2, String name) {
		this.t1 = t1;
		this.t2 = t2;
		this.name = name;
	}
	//Esta funcion hace un for de la lista comun
	public Boolean comprobarTenedores(){
		boolean confirmar = false;
		for(tenedor a: MainFilosofs.tenedors){
			//si esta bloqueado le sumo 1 a bloqueados
			if(!(a.getLliure())){
				this.setBloqueados(this.getBloqueados()+1);
			}
		}
		if(bloqueados<(MainFilosofs.tenedors.size()-1)){
			confirmar=true;
		}
		return confirmar;
	}
	@Override
	public void run() {
		Boolean confirmar = false;
		Boolean dejar1 = false;
		Boolean dejar2 = false;
		for (;;) {
			
			// una funcion que compruebe que los otros tres filosofos estan
			// comiendo con algun tenedor
			//cuento los tenedors que estan bloqueados si devuelve true ,el filosofo tendra 
			//permiso para coger el tenedor
			confirmar=this.comprobarTenedores();
			//si bloqueados es menor de que el tamaño de la lista -1...entonces puede coger el tenedor
			if(confirmar){
				t1.CogerTenedor(name);
				dejar1 = true;
			}
			
			//pongo a 0 bloqueados
			this.setBloqueados(0);
			try {
				Thread.sleep((long) (Math.random() * 2000 + 1000));
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.comprobarTenedores();
			if(confirmar){
			t2.CogerTenedor(name);
			dejar2=true;
			}
			try {
				Thread.sleep((long) (Math.random() * 2000 + 1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(dejar1){
				t1.dejarTenedor(name);
			}
			
			if(dejar2){
				t2.dejarTenedor(name);
			}
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
