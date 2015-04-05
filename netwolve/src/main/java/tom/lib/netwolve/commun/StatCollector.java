package tom.lib.netwolve.commun;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

public class StatCollector<E> {

	private Map<String, Statistique> stats;
	private Map<String, Method> methods;
	
	public StatCollector(Class<? extends E> clasz) {
		stats = Maps.newHashMap();
		methods = Maps.newHashMap();
		for (Method method : clasz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(Collectable.class)){
				String name = method.getAnnotation(Collectable.class).name();
				name = "".equals(name)?method.getName():name;
				stats.put(name, new Statistique());
				methods.put(name, method);
			}
		}
	}
	
	public void collect(E element){
		for (Entry<String, Method> entry : methods.entrySet()) {
			String key = entry.getKey();
			Method method = entry.getValue();
			try {
				stats.get(key).add((Double) method.invoke(element));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new NetwolveRuntimeException(e);
			}
		}
	}
	
	public void collect(List<E> elements){
		for (E e : elements) {
			collect(e);
		}
	}
	
	public Statistique get(String property){
		return stats.get(property);
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		stats.forEach((name, stat) -> sb.append(name).append(" : ").append(stat).append("\n"));
		return sb.toString();
	}
}
