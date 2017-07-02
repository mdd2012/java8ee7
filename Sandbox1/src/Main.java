import java.util.Arrays;
import java.util.Date;
import java.util.List;

import a.Bean1;
import a.Bean1sub;
import b.Bean2;
import u.BeanUtil;

public class Main {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		Bean1 a = new Bean1();
		a.setIntField(123);
		a.setLongField(456);
		a.setIntegerField(789);
		a.setStrField("abc");
		a.setDateField(new Date());
		a.setOnly1("only1");

		Bean1sub as = new Bean1sub();
		as.setIntField(-123);
		as.setLongField(-456);
		as.setIntegerField(-789);
		as.setStrField("-abc");
		as.setDateField(new Date());
		as.setOnly1("-only1");

		a.setSub(as);
		List<Bean1sub> subList = Arrays.asList(as, as, as);
		a.setSubList(subList);

		Bean2 b = new Bean2();
		b.setOnly2("only2");
		BeanUtil.copyBean(a, b);

		System.out.println(a);
		System.out.println(b);
	}

}
