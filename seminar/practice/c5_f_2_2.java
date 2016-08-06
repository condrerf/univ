class Trapezoid {
	double base1, base2, height;
	
	Trapezoid(double base1, double base2, double height) {
		this.base1 = base1;
		this.base2 = base2;
		this.height = height;
	}
	
	void getArea() {
		System.out.println("基底1: " + this.base1 + "、基底2: " + this.base2 + "、高さ: " + this.height + "の台形の面積は " + (this.base1 + this.base2) * this.height / 2 + "です。");
	}
}

class c5_f_2_2 {

	public static void main(String args[]) {
		double base1 = Double.valueOf(args[0]).doubleValue();
		double base2 = Double.valueOf(args[1]).doubleValue();
		double height = Double.valueOf(args[2]).doubleValue();
		Trapezoid tz = new Trapezoid(base1, base2, height);
		tz.getArea();
	}
}