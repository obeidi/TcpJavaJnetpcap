import java.util.concurrent.atomic.AtomicReference;

public class BeispielAtomicProp {

	public static void main(String[] args) {
		String initialReference = "initial value referenced";

		AtomicReference<String> atomicStringReference =
		    new AtomicReference<String>(initialReference);

		String newReference = "new value referenced";
		boolean exchanged = atomicStringReference.compareAndSet(initialReference, newReference);
		System.out.println("exchanged: " + exchanged);

		exchanged = atomicStringReference.compareAndSet(initialReference, newReference);
		System.out.println("exchanged: " + exchanged);
		
		exchanged = atomicStringReference.compareAndSet(newReference, initialReference);
		System.out.println("exchanged: " + exchanged);

	}

}
