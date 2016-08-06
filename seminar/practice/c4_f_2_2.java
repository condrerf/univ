class Rock {
	int weight;
	
	Rock() {
		for(;;) {
			this.weight = Math.random();
			if(this.weight >= 1 && this.weight <= 10) {
				break;
			}
		}
	}
}

class c5_f_2_1 {

	public static void main(String args[]) {
		int array[] = new int[10], sum;
		for(int i = 0; i < 10; i++) {
			Rock r = new Rock();
			array[i] = r.weight;
			sum += r.weight;
		}
		for (int i = 0; i < 10; i++) {
			System.out.println(i + "個目のRockオブジェクトの重さ:" + array[i]);
		}
		System.out.println(Rockオブジェクトの合計" + sum);
}