import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class AndarFrente implements Behavior {

	public static Saltos salto;

	public boolean takeControl() {
		return true;
	}

	public void action() {
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
		if (verificaFimPercurso()) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		salto = s;
		Motor.A.rotate(100, true);
		Motor.C.rotate(100);
	}

	public void suppress() {

	}

	public boolean verificaFimPercurso() {
		if (salto.getFilhos().size() == 2) {
			boolean isFininhs = salto.getFilhos().get(0).getDirections()
					.equals(Directions.ESQUERDA)
					&& salto.getFilhos().get(1).getDirections()
							.equals(Directions.EM_FRENTE);
			if (isFininhs) {
				mudaDirecaoCirculo(salto);
			}
		}

		return false;
	}
	
	private void mudaDirecaoCirculo(Saltos pai) {
		if (pai.getFilhos().size() == 2) {
			pai.setDirections(Directions.DIREITA);
			return;
		}
		mudaDirecaoCirculo(pai.getPai());
	}

}
