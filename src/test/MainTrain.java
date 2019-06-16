package test;

import java.util.Random;

public class MainTrain {

	public static void main(String[] args) {
		Random r=new Random();
		int port=r.nextInt(1001)+5000;
		Simulator sim=new Simulator(port); // sim_client on port,1, sim_server on port
		
		int rand=r.nextInt(1000);
		
		String[] test1={
				"print test",
				"var breaks = bind controls/flight/speedbrake",
				"var throttle = bind controls/engines/current-engine/throttle",
				"var heading = bind instrumentation/heading-indicator/indicated-heading-deg",
				"var airspeed = bind instrumentation/airspeed-indicator/indicated-speed-kt",
				"var roll = bind instrumentation/attitude-indicator/indicated-roll-deg",
				"var pitch = bind instrumentation/attitude-indicator/internal-pitch-deg",
				"var rudder = bind controls/flight/rudder",
				"var aileron = bind controls/flight/aileron",
				"var elevator = bind controls/flight/elevator",
				"var alt = bind instrumentation/altimeter/indicated-altitude-ft",
				"breaks = 0",
				"throttle = 1",
				"heading = 80.256371",
				"roll = -0.048512",
				"var h0 = heading",
				"pitch = 4.791763",
				"print h0",
				"print heading",
				"while alt < 1000 {",
				"print loop",
				"heading = 79.256371",
				"rudder = (h0 - heading)/20",
				"print rudder",
				"aileron = - roll / 70",
				"print aileron",
				"elevator = pitch / 50",
				"print elevator",
				"print alt",
				"sleep 2050",
				"}",
				"print done"
		};

		
		if(MyInterpreter.interpret(test1)!=rand*5-(8+2))
			System.out.println("failed test1 (-20)");

		sim.close();
		System.out.println("done");
	}}




/*				"openDataServer 5400 10",
				"connect 127.0.0.1 5402",
				"var x = bind simW",
				"var breaks = bind /controls/flight/speedbrake", 
				"var throttle = bind /controls/engines/current-engine/throttle", 
				"var heading = bind /instrumentation/heading-indicator/offset-deg", 
				"var airspeed = bind /instrumentation/airspeed-indicator/indicated-speed-kt", 
				"var roll = bind /instrumentation/attitude-indicator/indicated-roll-deg" , 
				"var pitch = bind /instrumentation/attitude-indicator/internal-pitch-deg" , 
				"var rudder = bind /controls/flight/rudder" ,
				"var aileron = bind /controls/flight/aileron" , 
				"var elevator = bind /controls/flight/elevator" , 
				"var alt = bind /instrumentation/altimeter/indicated-altitude-ft" , 
				"breaks = 0" , 
				"throttle = 1" , 
				"rudder = 0" , 
				"aileron = 1" , 
				"elevator = 0" , 
				"print alt" , 
				"" */



/*

  
 * */
 