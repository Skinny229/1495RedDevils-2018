package org.usfirst.frc.team1495.robot.pathgen;

import java.util.Objects;

public class Pair<A, B> {

    public final A first;
    public final A second;

    public Pair(A first, A second) {
        this.first = first;
        this.second = second;
    }

    public boolean equals(Object o) {
		return o instanceof Pair && Objects.equals(first, ((Pair<?, ?>) o).first) && Objects.equals(second, ((Pair<?,?>)o).second);
    }

    public int hashCode() {
        return 31 * Objects.hashCode(first) + Objects.hashCode(second);
    }

    public String toString() {
        return first + "=" + second;
    }
}