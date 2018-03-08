package de.kaysubs.tracker.anidex.model;

public class DataSize {
    private final int ammount;
    private final DataUnit unit;

    public DataSize(int ammount, DataUnit unit) {
        this.ammount = ammount;
        this.unit = unit;
    }

    public enum DataUnit {
        BYTE("B"), KILOBYTE("KB"), MEGABYTE("MB"), GIGABYTE("GB"), TERABYTE("TB");
        private final String unitName;

        DataUnit(String unitName) {
            this.unitName = unitName;
        }

        public String getUnitName() {
            return unitName;
        }
    }

    public int getAmmount() {
        return ammount;
    }

    public DataUnit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return ammount + " " + unit.getUnitName();
    }
}
