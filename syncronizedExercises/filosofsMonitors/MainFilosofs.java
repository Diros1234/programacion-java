package filosofsMonitors;

import java.util.ArrayList;
import java.util.List;

public class MainFilosofs {
	//hago una lista comun para añadir todos los monitores y controlar su estado
	static List<tenedor> tenedors = new ArrayList<tenedor>();
	public List<tenedor> getFilos() {
		return tenedors;
	}
	public void setFilos(List<tenedor> filos) {
		this.tenedors = filos;
	}
	public static void main(String[] args) {
		
		//añado todos los monitores a esta lista
		tenedor t1 = new tenedor(1);
		tenedors.add(t1);
		tenedor t2 = new tenedor(2);
		tenedors.add(t2);
		tenedor t3 = new tenedor(3);
		tenedors.add(t3);
		tenedor t4 = new tenedor(4);
		tenedors.add(t4);
		
		filosofos f1 = new filosofos(t1,t2,"Daniel");
		filosofos f2= new filosofos(t2,t3,"Falete");
		filosofos f3 = new filosofos(t3,t4,"ballena");
		filosofos f4 = new filosofos(t4,t1,"Foca monje");
		
		f1.start();
		f2.start();
		f3.start();
		f4.start();
	}

}
