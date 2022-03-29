package net.cadiamc.spigot.util;

public class PearlUtils {
    private static boolean pearlThroughString;
    private static boolean pearlThroughFenceGates;
    private static boolean pearlThroughCobweb;
    private static boolean pearlThroughSlab;
    private static boolean pearlThroughStairs;
    private static boolean pearlCobbleWall;
    private static boolean pearlParticles;
    private static boolean damagePearls;
    private static boolean pearlAntiGlitch;
    private static boolean pearlCriticalBlock;
    private static boolean suffocationPreventation;
    private static boolean pearlPassFine;
    private static boolean pearlPassFineCancel;
    private static int pearlMaxPass;
    private static String suffocationMessages;
    private static String refundMessage;
    private static String refundCommand;

    public static boolean isPearlThroughString() {
        return PearlUtils.pearlThroughString;
    }

    public static void setPearlThroughString(final boolean pearlThroughString) {
        PearlUtils.pearlThroughString = pearlThroughString;
    }

    public static boolean isPearlThroughFenceGates() {
        return PearlUtils.pearlThroughFenceGates;
    }

    public static void setPearlThroughFenceGates(final boolean pearlThroughFenceGates) {
        PearlUtils.pearlThroughFenceGates = pearlThroughFenceGates;
    }

    public static boolean isPearlThroughCobweb() {
        return PearlUtils.pearlThroughCobweb;
    }

    public static void setPearlThroughCobweb(final boolean pearlThroughCobweb) {
        PearlUtils.pearlThroughCobweb = pearlThroughCobweb;
    }

    public static boolean isPearlThroughSlab() {
        return PearlUtils.pearlThroughSlab;
    }

    public static void setPearlThroughSlab(final boolean pearlThroughSlab) {
        PearlUtils.pearlThroughSlab = pearlThroughSlab;
    }

    public static boolean isPearlThroughStairs() {
        return PearlUtils.pearlThroughStairs;
    }

    public static void setPearlThroughStairs(final boolean pearlThroughStairs) {
        PearlUtils.pearlThroughStairs = pearlThroughStairs;
    }

    public static boolean isPearlParticles() {
        return PearlUtils.pearlParticles;
    }

    public static void setPearlParticles(final boolean pearlParticles) {
        PearlUtils.pearlParticles = pearlParticles;
    }

    public static boolean isDamagePearls() {
        return PearlUtils.damagePearls;
    }

    public static void setDamagePearls(final boolean damagePearls) {
        PearlUtils.damagePearls = damagePearls;
    }

    public static boolean isPearlAntiGlitch() {
        return PearlUtils.pearlAntiGlitch;
    }

    public static void setPearlAntiGlitch(final boolean pearlAntiGlitch) {
        PearlUtils.pearlAntiGlitch = pearlAntiGlitch;
    }

    public static boolean isPearlCriticalBlock() {
        return PearlUtils.pearlCriticalBlock;
    }

    public static void setPearlCriticalBlock(final boolean pearlCriticalBlock) {
        PearlUtils.pearlCriticalBlock = pearlCriticalBlock;
    }

    public static boolean isPearlCobbleWall() {
        return PearlUtils.pearlCobbleWall;
    }

    public static void setPearlCobbleWall(final boolean pearlCobbleWall) {
        PearlUtils.pearlCobbleWall = pearlCobbleWall;
    }

    public static boolean isSuffocationPreventation() {
        return PearlUtils.suffocationPreventation;
    }

    public static void setSuffocationPreventation(final boolean suffocationPreventation) {
        PearlUtils.suffocationPreventation = suffocationPreventation;
    }

    public static String getSuffocationMessages() {
        return PearlUtils.suffocationMessages;
    }

    public static void setSuffocationMessages(final String suffocationMessages) {
        PearlUtils.suffocationMessages = suffocationMessages;
    }

    public static String getRefundMessage() {
        return PearlUtils.refundMessage;
    }

    public static void setRefundMessage(final String refundMessage) {
        PearlUtils.refundMessage = refundMessage;
    }

    public static String getRefundCommand(final String name) {
        return PearlUtils.refundCommand.replace("%player%", name);
    }

    public static void setRefundCommand(final String refundCommand) {
        PearlUtils.refundCommand = refundCommand;
    }

    public static int getPearlMaxPass() {
        return PearlUtils.pearlMaxPass;
    }

    public static void setPearlMaxPass(final int pearlMaxPass) {
        PearlUtils.pearlMaxPass = pearlMaxPass;
    }

    public static boolean isPearlPassFine() {
        return PearlUtils.pearlPassFine;
    }

    public static void setPearlPassFine(final boolean pearlPassFine) {
        PearlUtils.pearlPassFine = pearlPassFine;
    }

    public static boolean isPearlPassFineCancel() {
        return PearlUtils.pearlPassFineCancel;
    }

    public static void setPearlPassFineCancel(final boolean pearlPassFineCancel) {
        PearlUtils.pearlPassFineCancel = pearlPassFineCancel;
    }
}
