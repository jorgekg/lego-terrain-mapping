import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class AndarFrente implements Behavior {
	
	public static Saltos salto;

	public boolean takeControl() {
		return true;
	}

	public void action() {
		Motor.A.rotate(100, true);
		Motor.C.rotate(100);
		Saltos s = new Saltos();
		// seta o primeiro nó
		if (salto == null) {
			salto = App.saltos;
		}
		s.setDirections(Directions.EM_FRENTE);
		// seta o salto pai
		s.setPai(salto);
		// adiciona a instancia ao filho
		salto.getFilhos().add(s);
		// troca o salto para a instancia atual
		salto = s;
	}

	public void suppress() {
	
	}
	
	public boolean verificaFimPercurso() {
		//da ultima intercessão tem que ser 
		
		return false;
	}

}
