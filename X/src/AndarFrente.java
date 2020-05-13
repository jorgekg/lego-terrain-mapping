import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class AndarFrente implements Behavior {

	public static Saltos salto = new Saltos();

	public boolean takeControl() {
		return true;
	}

	public void action() {
		// frente
		Motor.A.rotate(750, true);
		Motor.C.rotate(750);
		Saltos s = new Saltos();
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
		UltrasonicSensor sensor = Ultra.instance();
		// logica gira para o lado para verificar se pode virar
		Motor.A.rotate(360, true);
		Motor.C.rotate(-360);
		if (sensor.getDistance() > 25) {
			Saltos s1 = new Saltos();
			s1.setPai(s);
			s1.setDirections(Directions.ESQUERDA);
			s.getFilhos().add(s1);
			Motor.A.rotate(100, true);
			Motor.C.rotate(-100);
			Motor.A.rotate(-100, true);
			Motor.C.rotate(100);
		}
		Motor.A.rotate((360 * 2) * -1, true);
		Motor.C.rotate((360 * 2));
		if (sensor.getDistance() > 25) {
			Saltos s1 = new Saltos();
			s1.setPai(s);
			s1.setDirections(Directions.DIREITA);
			s.getFilhos().add(s1);
			Motor.A.rotate(100, true);
			Motor.C.rotate(-100);
			Motor.A.rotate(-100, true);
			Motor.C.rotate(100);
		}
		salto = s;
		Motor.A.rotate(360, true);
		Motor.C.rotate(-360);
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
