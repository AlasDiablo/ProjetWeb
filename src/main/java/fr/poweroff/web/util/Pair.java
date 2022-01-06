package fr.poweroff.web.util;

import com.google.common.base.Objects;

public class Pair<L, R> {
    private L left;
    private R right;

    public Pair(L left, R right) {
        this.left  = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public R getRight() {
        return right;
    }

    public void setRight(R right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equal(getLeft(), pair.getLeft()) && Objects.equal(getRight(), pair.getRight());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getLeft(), getRight());
    }

    @Override
    public String toString() {
        return "Pair{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
