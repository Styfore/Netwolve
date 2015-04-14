package tom.lib.genetic.enumerations;

public enum FitnessOrder {

	/**
	 * Plus le fitnesse est grand, meilleur est l'individu
	 */
	ASC(1),
	
	/**
	 * Plus le fitnesst est petit, meilleur est l'individu
	 */
	DESC(-1);
	
	private int order;
	private FitnessOrder(int order) {
		this.order = order;
	}
	
	public int getOrder() {
		return order;
	}
}
