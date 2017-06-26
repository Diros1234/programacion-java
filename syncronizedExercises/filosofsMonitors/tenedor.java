package filosofsMonitors;

public class tenedor {
	private Boolean lliure = false;
	//private String name ;
	private int tenedor;
	public tenedor(int tenedor){
		this.tenedor = tenedor;
		lliure = true;
	}
	public synchronized void CogerTenedor(String name){
		while(!lliure){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.setLliure(false);
		if(!(MainFilosofs.tenedors.contains(this))){
			MainFilosofs.tenedors.add(this);
		}
		else{
			int i=0;
			for(tenedor a: MainFilosofs.tenedors){
				if(a.hashCode()==this.hashCode()){
					tenedor ten = MainFilosofs.tenedors.get(i);
					ten = this;
					MainFilosofs.tenedors.set(i, ten);
				}
				i+=1;
			}
		}
		
		System.out.println("El filosofo : "+name+" esta comiendo con el tenedor : "+tenedor);
		notifyAll();
	}
	public synchronized void dejarTenedor(String name){
		this.setLliure(true);
		System.out.println("El filosofo : "+name+" ha dejado el tenedor : "+tenedor);
		notifyAll();
	}
	public tenedor() {
		lliure = true;
	}
	public Boolean getLliure() {
		return lliure;
	}
	public void setLliure(Boolean lliure) {
		this.lliure = lliure;
	}
	/*public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}*/
	
}
