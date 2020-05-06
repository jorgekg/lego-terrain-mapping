import java.util.ArrayList;


public class Saltos {

	private Directions directions;
	private Saltos pai;
	private ArrayList<Saltos> filhos = new ArrayList<Saltos>();
	
	public Saltos getPai() {
		return pai;
	}
	public void setPai(Saltos pai) {
		this.pai = pai;
	}
	public ArrayList<Saltos> getFilhos() {
		return filhos;
	}
	public void setFilhos(ArrayList<Saltos> filhos) {
		this.filhos = filhos;
	}
	public Directions getDirections() {
		return directions;
	}
	public void setDirections(Directions directions) {
		this.directions = directions;
	}
}