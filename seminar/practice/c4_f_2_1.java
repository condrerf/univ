class c4_f_2_1 {

	public static void main(String args[]) {
		double sum = 0;
		for(int i = 0; i < args.length; i += 2) {
			if(args[i + 1].startsWith("nickel")) {
				sum += .05 * Integer.parseInt(args[i]);
			}
			else if(args[i + 1].startsWith("quarter")) {
				sum += .25 * Integer.parseInt(args[i]);
			}
			else if(args[i + 1].startsWith("dime")) {
				sum += .10 * Integer.parseInt(args[i]);
			}
		}
		System.out.println("‡Œv‚Í $" + sum + "‚Å‚·B");
	}
}