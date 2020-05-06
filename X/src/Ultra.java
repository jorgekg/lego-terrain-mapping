import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;


public class Ultra {
	
	public static UltrasonicSensor sensor;
	
	public static ColorSensor colorSensor;
	
	public static ColorSensor instanceColor() {
		if (colorSensor == null) {
			colorSensor = new ColorSensor(SensorPort.S3);
		}
		return colorSensor;
	}
	
	public static UltrasonicSensor instance() {
		if (sensor == null) {
			sensor = new UltrasonicSensor(SensorPort.S2);
		}
		return sensor;
	}

}
