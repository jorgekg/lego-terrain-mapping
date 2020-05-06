import java.util.ArrayList;

import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class NaoBater implements Behavior {

	static int direction = 1;
	static ArrayList<Integer> directions = new ArrayList<Integer>();

	public boolean takeControl() {
		Saltos s = new Saltos();
		UltrasonicSensor sensor = Ultra.instance();
		Motor.A.rotate(360, true);
		Motor.C.rotate(-360);
		if (sensor.getDistance() > 15) {
			s.setDirections(Directions.ESQUERDA);
			s.setPai(AndarFrente.salto);
			AndarFrente.salto.getFilhos().add(s);
		}
		Motor.A.rotate((360 * 2) * -1, true);
		Motor.C.rotate((360 * 2));
		if (sensor.getDistance() > 15) {
			s.setDirections(Directions.DIREITA);
			s.setPai(AndarFrente.salto);
			AndarFrente.salto.getFilhos().add(s);
		}
		return sensor.getDistance() < 15;
	}


	public void action() {
		UltrasonicSensor sensor = Ultra.instance();
		Motor.A.rotate(360, true);
		Motor.C.rotate(-360);
		if (sensor.getDistance() > 15) {
			for (Saltos s : AndarFrente.salto.getFilhos()) {
				if (s.getDirections().equals(Directions.ESQUERDA)) {
					s.setDirections(Directions.ESQUERDA);
					AndarFrente.salto = s;
				} 
			}
			return;
		}
		Motor.A.rotate((360 * 2) * -1, true);
		Motor.C.rotate((360 * 2));	
		if (sensor.getDistance() > 15) {
			for (Saltos s : AndarFrente.salto.getFilhos()) {
				if (s.getDirections().equals(Directions.DIREITA)) {
					s.setDirections(Directions.DIREITA);
					AndarFrente.salto = s;
				} 
			}
			return;
		}
		AndarFrente.salto = AndarFrente.salto.getPai();
		Motor.A.rotate(-360, true);
		Motor.C.rotate(-360);
		action();
	}

	public void suppress() {

	}
}
