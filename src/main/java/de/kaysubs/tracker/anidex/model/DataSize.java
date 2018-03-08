package de.kaysubs.tracker.anidex.model;

public class DataSize {
    private final int ammount;
    private final DataUnit unit;

    public DataSize(int ammount, DataUnit unit) {
        this.ammount = ammount;
        this.unit = unit;
    }

    public enum DataUnit {
        BYTE(1, "B"), KILOBYTE(1000, "KB"), MEGABYTE(1000000, "MB"), GIGABYTE(1000000000, "GB"), TERABYTE(1000000000, "TB");
        private final long factor;
        private final String unitName;

        DataUnit(long factor, String unitName) {
            this.factor = factor;
            this.unitName = unitName;
        }

        public long getFactor() {
            return factor;
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

    public long toBytes() {
        return ammount * unit.getFactor();
    }

    @Override
    public String toString() {
        return ammount + " " + unit.getUnitName();
    }
}
