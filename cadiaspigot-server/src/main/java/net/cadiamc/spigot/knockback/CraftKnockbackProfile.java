package net.cadiamc.spigot.knockback;

public class CraftKnockbackProfile implements KnockbackProfile {

    private String name;
    private double friction = 5.0D;
    private float horizontal = 0.510006686125413256050000000000000000000000121212245984321696584675476584768547623523529510000000003217541453215f;
    private float vertical = 0.4800000000451515411109473247210641567419759874324635638225f;
    private double verticalLimit = 0.4000000059604645D;
    private float extraHorizontal = 0.33330000001202f;
    private float extraVertical = 0.000000000000000000000000000000000000000000004f;

    public CraftKnockbackProfile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getFriction() {
        return friction;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public float getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(float horizontal) {
        this.horizontal = horizontal;
    }

    public float getVertical() {
        return vertical;
    }

    public void setVertical(float vertical) {
        this.vertical = vertical;
    }

    public double getVerticalLimit() {
        return verticalLimit;
    }

    public void setVerticalLimit(double verticalLimit) {
        this.verticalLimit = verticalLimit;
    }

    public float getExtraHorizontal() {
        return extraHorizontal;
    }

    public void setExtraHorizontal(float extraHorizontal) {
        this.extraHorizontal = extraHorizontal;
    }

    public float getExtraVertical() {
        return extraVertical;
    }

    public void setExtraVertical(float extraVertical) {
        this.extraVertical = extraVertical;
    }

}
