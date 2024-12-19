
/**
 * A simple class for temporarily modifying the state of a variable.<br>
 * Create an instance of this class with or without a starting value and temporarily change its value by calling <a href="#Influenced.influence">{@link #influence(T)}</a>. <br>
 * To change its value back simply call <a href="#Influenced.Influencer#reset()">{@link Influenced.Influencer#reset() reset()}</a> on the returned Influencer.
 * @author Slymey_
 * @param <T> type of the contained value
 */
public class Influenced<T> {
    /**
     * Is responsible for keeping track of the <a href="#Influenced">{@link Influenced}</a>'s value prior to being influenced.<br>
     * Also tracks the previous Influencer and the next.
     * @param <T> type of the contained value
     */
    public static class Influencer<T> {
        private Influenced<T> influenced;
        private Influencer<T> next;
        private Influencer<T> previous;
        private T previousValue;

        /**
         * @param target variable to influence
         * @param value the new value of the variable
         */
        public Influencer(Influenced<T> target, T value) {
            _reInfluence(target, value);
        }

        /**
         * Gets the pointer to the variable currently being influenced.
         * @return the variable being influenced
         */
        public Influenced<T> getInfluenced() {
            return influenced;
        }

        /**
         * Gets the value of the variable before it was influenced by this Influencer.
         * @return previous value
         */
        public T getPreviousValue() {
            return previousValue;
        }

        /**
         * Resets this Influencer to a clean state (all null) and sets the Influenced's value to its value prior to this Influencer being set.
         * @return whether or not this Influencer was influencing anything
         */
        public synchronized boolean reset() {
            if (influenced == null) return false;

            if (next == null) {
                influenced.value = previousValue;
                influenced.influence = previous;
                if (previous != null) previous.next = null;
            } else {
                next.previousValue = previousValue;
                if (previous != null) previous.next = next;
                next.previous = previous;
            }

            influenced = null;
            previous = null;
            next = null;
            previousValue = null;
            return true;
        }

        /**
         * Allows reuse of the object without creating a new instance. <br>
         * Also resets this Influencer.
         * @param target new target to influence
         * @param value the value to influence it with
         * @return whether or not this Influencer was previously influencing anything
         * @see #Influencer(Influenced<T>, T)
         */
        public synchronized boolean reInfluence(Influenced<T> target, T value) {
            return _reInfluence(target, value);
        }

        private synchronized boolean _reInfluence(Influenced<T> target, T value) {
            boolean wasInfluencing = this.reset();
            synchronized (this) {
                previousValue = target.value;
                target.value = value;
                influenced = target;
                previous = target.influence;
                if (previous != null) {
                    previous.next = this;
                }
                target.influence = this;
            }
            return wasInfluencing;
        }
    }

    public T value = null;
    private Influencer<T> influence = null;

    /**
     * Empty Influenced with a default value null.
     */
    public Influenced() {}

    /**
     * New Influenced with a starting value
     * @param a starting value
     */
    public Influenced(T a) {
        value = a;
    }

    public synchronized T get() {
        return value;
    }

    /**
     * @param v new value
     */
    public synchronized void set(T v) {
        value = v;
    }

    /**
     * Gets the most recent <a href="#Influenced.Influencer">{@link Influencer}</a> on this object.
     * @return current Influencer
     */
    public synchronized Influencer<T> getInfluence() {
        return influence;
    }

    /**
     * Influences this object with a new value and returns the <a href="#Influenced.Influencer">{@link Influencer}</a>.
     * @param val new value of this object
     * @return the Influencer
     */
    public synchronized Influencer<T> influence(T val) {
        return new Influencer<>(this, val);
    }

    /**
     * Removes all <a href="#Influenced.Influencer">{@link Influencers}</a> on this object and preserves its value.
     */
    public synchronized void removeInfluences() {
        Influencer<T> inf = influence;
        while (inf != null) {
            inf.influenced = null;
            inf.previousValue = null;
            inf = inf.previous;
        }
    }

    /**
     * Sets the initial value that will remain after all <a href="#Influenced.Influencer">{@link Influencers}</a> are reset.
     * @param val new initial value
     */
    public synchronized void setInitialValue(T val) {
        Influencer<T> inf = _firstInfluencer();
        if (inf != null) {
            inf.previousValue = val;
        }
    }

    /**
     * Gets the first value of this object prior to being influenced.
     * @return the value
     */
    public synchronized T getInitialValue() {
        Influencer<T> inf = _firstInfluencer();
        if (inf != null) {
            return inf.previousValue;
        }
        return null;
    }

    private Influencer<T> _firstInfluencer() {
        Influencer<T> inf = influence;
        Influencer<T> inf2 = null;
        while (inf != null) {
            inf2 = inf;
            inf = inf2.previous;
        }
        return inf2;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
} 
