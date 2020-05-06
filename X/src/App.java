import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class App {

	public static Saltos saltos = new Saltos();
	
	public static void main(String[] args) {
		saltos.setDirections(Directions.EM_FRENTE);
		Behavior andar = new AndarFrente();
		Behavior desviar = new NaoBater();
		
		Behavior[] comportamentos = { andar, desviar };
		
		Arbitrator arb = new Arbitrator(comportamentos);
		arb.start();
	}
	
	public static void menorCaminho(int[][] matriz) {
		
	}


}
