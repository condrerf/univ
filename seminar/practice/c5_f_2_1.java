class Rock {
	int weight;
	
	Rock() {
		this.weight = (int)(10 * Math.random() + 1);
	}
}

class c5_f_2_1 {

	public static void main(String args[]) {
		int array[] = new int[10], sum = 0;
		for(int i = 0; i < 10; i++) {
			Rock r = new Rock();
			array[i] = r.weight;
			sum += r.weight;
		}
		for (int i = 0; i < 10; i++) {
			System.out.println(i + "�ڂ�Rock�I�u�W�F�N�g�̏d��:" + array[i]);
		}
		System.out.println("Rock�I�u�W�F�N�g�̍��v: " + sum);
	}
}