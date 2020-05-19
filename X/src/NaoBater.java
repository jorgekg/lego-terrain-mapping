import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class NaoBater implements Behavior {

	public boolean takeControl() {
		UltrasonicSensor sensor = Ultra.instance();
		return sensor.getDistance() < 20;
	}

	public void action() {
		// logica gira para realmente girar se o pai tem filhos
		for (Saltos s : AndarFrente.salto.getFilhos()) {
			if (s.getDirections().equals(Directions.ESQUERDA)) {
				s.setDirections(Directions.ESQUERDA);
				AndarFrente.salto = s;
				Motor.A.rotate(360, true);
				Motor.C.rotate(-360);
				return;
			}
			if (s.getDirections().equals(Directions.DIREITA)) {
				s.setDirections(Directions.DIREITA);
				AndarFrente.salto = s;
				Motor.A.rotate(-360, true);
				Motor.C.rotate(360);
				AndarFrente.ehDireitaOuEsqerda = true;
				return;
			}
		}
		AndarFrente.salto = AndarFrente.salto.getPai();
		Motor.A.rotate(-750, true);
		Motor.C.rotate(-750);
		action();
	}

	public void suppress() {

	}
}
