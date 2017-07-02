package u;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BeanUtil {
	/**
	 * 型の異なる同じgetter/setterを持つDTO間のコピー
	 * @param from
	 * @param toClass
	 * @return
	 */
	static public <T> T copyBean(Object from, Class<T> toClass) {
		T to = null;
		try {
			to = toClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		copyBean(from, to);
		return to;
	}

	/**
	 * 型の異なる同じgetter/setterを持つDTO間のコピー
	 * @param from
	 * @param toClass
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static public void copyBean(Object from, Object to) {
		List<Method> getters = getGetterList(from);
		getters.forEach(getter -> {
			getSetter(to, getter.getName()).ifPresent(setter -> {
				try {
					Object v = getter.invoke(from, (Object[]) null);
					Class p = setter.getParameterTypes()[0];
					Class r = getter.getReturnType();
					if (v == null || p.isPrimitive() || r.isPrimitive()) {
						//値がnullまたはプリミティブ型の場合
						setter.invoke(to, v);
					} else {
						if (v instanceof List) {
							Object v2 = invokeList(to, setter, (List) v);
							setter.invoke(to, v2);
						} else {
							if (p.equals(r)) {
								setter.invoke(to, v);
							} else {
								Object v2 = BeanUtil.copyBean(v, p);
								setter.invoke(to, v2);
							}
						}
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| InstantiationException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

			});

		});
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	private static List<Object> invokeList(Object to, Method setter, List<Object> l)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Type t = setter.getGenericParameterTypes()[0];
		if (t instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) t;
			Class tC = (Class) pt.getActualTypeArguments()[0];
			List<Object> l2 = l.stream().map(v -> BeanUtil.copyBean(v, tC)).collect(Collectors.toList());
//			List l2 = new ArrayList();
//			for (Object vv : l) {
//				Object v2 = tC.newInstance();
//				BeanUtil.copyBean(vv, v2);
//				l2.add(v2);
//			}
			return l2;

		} else {
			return l;
		}
	}

	/**
	 * getterメソッドの一覧を取得
	 *
	 * @param o
	 * @return
	 */
	static public List<Method> getGetterList(Object o) {
		List<Method> ret = Arrays.stream(o.getClass().getDeclaredMethods())
				// getXXで引数ゼロのpublicメソッドを抽出
				.filter(m -> m.getName().startsWith("get") && m.getParameterCount() == 0 && m.getReturnType() != null
						&& m.getModifiers() == Modifier.PUBLIC)
				.collect(Collectors.toList());
		return ret;
	}

	/**
	 * 引数のgetter名に対応するsetterを返す
	 *
	 * @param o
	 * @param getterName
	 * @return
	 */
	static private Optional<Method> getSetter(Object o, String getterName) {
		Optional<Method> ret = Arrays.stream(o.getClass().getDeclaredMethods())
				// setXXで引数ゼロのpublicメソッドを抽出
				.filter(m -> m.getName().equals("set" + getterName.substring(3)) && m.getParameterCount() == 1
						&& m.getModifiers() == Modifier.PUBLIC)
				.findFirst();
		return ret;
	}

}
