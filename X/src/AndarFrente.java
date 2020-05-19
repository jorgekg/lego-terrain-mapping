import java.util.ArrayList;

import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class AndarFrente implements Behavior {

	public static Saltos salto = new Saltos();
	public static boolean ehDireitaOuEsqerda = false;
	int[] caminho = new int[20];
	public static ArrayList<Directions> directions = new ArrayList<Directions>();

	public boolean takeControl() {
		return true;
	}

	public void action() {
		Saltos s = new Saltos();
		s.setDirections(Directions.EM_FRENTE);
		// seta o salto pai
		s.setPai(salto);
		// adiciona a instancia ao filho
		salto.getFilhos().add(s);
		ColorSensor cs = new ColorSensor(SensorPort.S3);
		if (verificaFimPercurso()) {
			try {
				getMenorCaminho(getPrimeiroNo(salto), 0);
				System.out.println(directions);
				Thread.sleep(10000);
				for (Directions d : directions) {
					if (cs.getLightValue() < 130) {
						break;
					}
					if (d.equals(Directions.EM_FRENTE)) {
						Motor.A.rotate(750, true);
						Motor.C.rotate(750);
					} else if (d.equals(Directions.ESQUERDA)) {
						Motor.A.rotate(360, true);
						Motor.C.rotate(-360);
					} else if (d.equals(Directions.DIREITA)) {
						Motor.A.rotate(-360, true);
						Motor.C.rotate(360);
					}
				}
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// frente
		if (ehDireitaOuEsqerda) {
			Motor.A.rotate(820, true);
			Motor.C.rotate(820);
		} else {
			Motor.A.rotate(750, true);
			Motor.C.rotate(750);
		}
		if (cs.getLightValue() < 130 && !App.temBranco) {
			s.setEhObjetivo(true);
			App.temBranco = true;
		}
		// troca o salto para a instancia atual
		UltrasonicSensor sensor = Ultra.instance();
		// logica gira para o lado para verificar se pode virar
		Motor.A.rotate(360, true);
		Motor.C.rotate(-360);
		if (sensor.getDistance() > 25) {
			Saltos s1 = new Saltos();
			s1.setPai(s);
			s1.setDirections(Directions.ESQUERDA);
			s.getFilhos().add(s1);
		}
		Motor.A.rotate((360 * 2) * -1, true);
		Motor.C.rotate((360 * 2));
		if (sensor.getDistance() > 25) {
			Saltos s1 = new Saltos();
			s1.setPai(s);
			s1.setDirections(Directions.DIREITA);
			s.getFilhos().add(s1);
		}
		salto = s;
		Motor.A.rotate(360, true);
		Motor.C.rotate(-360);
	}

	public void suppress() {

	}

	public static int getMenorCaminho(Saltos salto, int peso) {
		if (salto.isEhObjetivo()) {
			return peso;
		}
		if (salto.getFilhos().size() == 0) {
			return Integer.MAX_VALUE;
		}
		int menorPeso = Integer.MAX_VALUE;
		Saltos menorSalto = null;
		for (Saltos s : salto.getFilhos()) {
			int p = getMenorCaminho(s, peso);
			if (menorPeso > p) {
				menorPeso = p;
				menorSalto = s;
			}
		}
		if (menorSalto != null) {
			directions.add(menorSalto.getDirections());
		}
		return peso + 1;
	}

	public Saltos getPrimeiroNo(Saltos salto) {
		if (salto.getPai() == null) {
			return salto;
		}
		return this.getPrimeiroNo(salto.getPai());
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
			return isFininhs;
		}

		return false;
	}

	private void mudaDirecaoCirculo(Saltos pai) {
		if (pai.getFilhos().size() == 2) {
			pai.setDirections(Directions.DIREITA);
			pai.getFilhos().add(salto);
			return;
		}
		mudaDirecaoCirculo(pai.getPai());
	}

}
