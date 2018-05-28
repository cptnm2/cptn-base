package cptn;

import cptn.util.MathUtil;

import java.util.List;

public class MathUtilTest {

	public void testRandomPick() {
		List<Integer> list = MathUtil.randomPick(10, 3);
	
		for (Integer i: list) {
			System.out.print(i + "  ");
		}
		
		System.out.println();
	}
	
	public void testMassRandomPick() {
		for (int i = 0; i < 20; i++) {
			testRandomPick();
			
			try {
				Thread.sleep(227);
			} catch (InterruptedException e) {
			}
		}
	}

}
