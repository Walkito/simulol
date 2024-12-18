package br.com.walkito.simulol.models.enums;

public enum Function {
    TOP("Top"),
    JUNGLE("Jungle"),
    MID("Mid"),
    ADC("ADC"),
    SUPPORT("Support");

    private String function;

    Function(String function){
        this.function = function;
    }

    public String getFunction() {
        return function;
    }
}
