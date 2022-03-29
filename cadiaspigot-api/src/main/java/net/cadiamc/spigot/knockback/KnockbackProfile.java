package net.cadiamc.spigot.knockback;

public interface KnockbackProfile {

    String getName();

    double getFriction();

    void setFriction(double friction);

    float getHorizontal();

    void setHorizontal(float horizontal);

    float getVertical();

    void setVertical(float vertical);

    double getVerticalLimit();

    void setVerticalLimit(double verticalLimit);

    float getExtraHorizontal();

    void setExtraHorizontal(float extraHorizontal);

    float getExtraVertical();

    void setExtraVertical(float extraVertical);

}
