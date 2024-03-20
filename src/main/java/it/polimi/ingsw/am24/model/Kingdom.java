package it.polimi.ingsw.am24.model;

public enum Kingdom {
    PLANT,
    ANIMAL,
    FUNGI,
    INSECT;

    public Symbol toSymbol() {
        Symbol ret = null;
        switch (this) {
            case Kingdom.PLANT -> ret = Symbol.PLANT;
            case Kingdom.ANIMAL -> ret = Symbol.ANIMAL;
            case Kingdom.FUNGI -> ret = Symbol.FUNGI;
            case Kingdom.INSECT -> ret = Symbol.INSECT;
        }
        return ret;
    }
}
