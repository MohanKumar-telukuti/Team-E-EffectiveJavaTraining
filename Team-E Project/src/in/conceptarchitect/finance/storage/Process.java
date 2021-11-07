package in.conceptarchitect.finance.storage;

@FunctionalInterface
public interface Process<T> {
	
	void process(T object);
	
	default boolean initialize() {
		return true;
	}
	default void close() {
		
	}
}
