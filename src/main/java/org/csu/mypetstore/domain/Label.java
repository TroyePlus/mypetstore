package org.csu.mypetstore.domain;

public class Label {
    private String label;
    private int count;

    public Label(String label){
        this.label = label;
        this.count = 0;
    }

    public Label(){

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Label{" +
                "label='" + label + '\'' +
                ", count=" + count +
                '}';
    }
}
