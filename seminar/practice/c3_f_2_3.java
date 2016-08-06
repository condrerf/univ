class c3_f_2_3 {

	public static void main(String args[]) {
		for(int i = 1; i < 100; i++) {
			System.out.print(i);
			if(i % 5 == 0) {
				System.out.print("\n");
			}
			else {
				System.out.print("\t");
			}
		}
	}
}