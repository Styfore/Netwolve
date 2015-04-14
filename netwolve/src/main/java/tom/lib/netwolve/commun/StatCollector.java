package tom.lib.netwolve.commun;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

public class StatCollector<E> {

	private Map<String, Statistique> stats;
	private Map<String, Method> methods;
	private int size = 0;
	
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
	
	public <I extends E> void collect(I element){
		for (Entry<String, Method> entry : methods.entrySet()) {
			String key = entry.getKey();
			Method method = entry.getValue();
			try {
				stats.get(key).add((Double) method.invoke(element));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new NetwolveRuntimeException(e);
			}
		}
		size++;
	}
	
	public <I extends E> void collect(Collection<I> elements){
		for (I e : elements) {
			collect(e);
		}
	}
	
	public Statistique get(String property){
		return stats.get(property);
	}
	
	public Collection<Statistique> get(){
		return stats.values();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		stats.forEach((name, stat) -> sb.append(name).append(" : ").append(stat).append("\n"));
		return sb.toString();
	}

	public int size() {
		return size;
	}
}
