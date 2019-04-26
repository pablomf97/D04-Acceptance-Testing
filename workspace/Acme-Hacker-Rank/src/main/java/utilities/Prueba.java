
package utilities;

import java.util.Date;
import java.util.Random;

public class Prueba {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		// TODO Auto-generated method stub
		final Date d = new Date();
		final Random rand = new Random();
		rand.setSeed(d.getTime());
		final int i = (1000 + rand.nextInt(9000));
		System.out.println(i);

	}

}
