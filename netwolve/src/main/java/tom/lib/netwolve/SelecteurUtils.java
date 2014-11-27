package tom.lib.netwolve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import tom.lib.netwolve.interfaces.Copyable;
import tom.lib.netwolve.interfaces.Mutable;
import tom.lib.netwolve.interfaces.Scorable;

public class SelecteurUtils {

    public final static Comparator<Scorable> COMPARATOR = new Comparator<Scorable>() {
        @Override
        public int compare(Scorable o1, Scorable o2) {
            return o1.compareScore(o2);
        };
    };
   
    public static <A extends Mutable> void mute(A[] population){
        for (A a : population) {
            a.mute();
        }
    }
   
    @SuppressWarnings("unchecked")
    public static <A extends Mutable & Scorable & Copyable<A>> A[] select(A[] population){
        Arrays.sort(population, COMPARATOR);
       
        double sommeScore = 0;
       
        for (int i = 0; i < population.length; i++) {
            sommeScore = sommeScore + population[i].getScore();
        }
       
        ArrayList<A> selected = new ArrayList<A>();
        for (int i = 0; i < population.length; i++) {
            double h = MathUtils.RANDOM.nextFloat()*sommeScore;
            for (int j = 0; j < population.length; j++) {
                if (population[j].getScore() < h){
                    A a = population[j].copy();
                    a.mute();
                    selected.add(a);
                    break;
                }
            }
        }
       
        return selected.toArray((A[]) new Object[population.length]);
    }
   
}
