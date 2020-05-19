import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class App {
	
	public static boolean temBranco = false;
	
	public static void main(String[] args) {
		Saltos s = new Saltos();
		AndarFrente.salto = s;
		AndarFrente.salto.setDirections(Directions.EM_FRENTE);
		Behavior andar = new AndarFrente();
		Behavior desviar = new NaoBater();
		
		Behavior[] comportamentos = { andar, desviar };
		
		Arbitrator arb = new Arbitrator(comportamentos);
		arb.start();
	}
	
	public static void menorCaminho(int[][] matriz) {
		
	}


}
