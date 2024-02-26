package kolchy.service;

public interface EventChangeListener<T> {
    void onSupprimerClicked();
    void onModifierClicked(T t);
}
