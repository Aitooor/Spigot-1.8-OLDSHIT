package net.minecraft.server;

public class BlockCarpet extends Block {
    public static final BlockStateEnum<EnumColor> COLOR = BlockStateEnum.of("color", EnumColor.class);

    protected BlockCarpet() {
        super(Material.WOOL);
        this.j(this.blockStateList.getBlockData().set(COLOR, EnumColor.WHITE));
        this.a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.a(true);
        this.a(CreativeModeTab.c);
        this.b(0);
    }

    public MaterialMapColor g(IBlockData var1) {
        return ((EnumColor)var1.get(COLOR)).e();
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public void j() {
        this.b(0);
    }

    public void updateShape(IBlockAccess var1, BlockPosition var2) {
        this.b(0);
    }

    protected void b(int var1) {
        this.a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    }

    public boolean canPlace(World var1, BlockPosition var2) {
        return super.canPlace(var1, var2) && this.e(var1, var2);
    }

    public void doPhysics(World var1, BlockPosition var2, IBlockData var3, Block var4) {
        this.e(var1, var2, var3);
    }

    private boolean e(World var1, BlockPosition var2, IBlockData var3) {
        if (!this.e(var1, var2)) {
            this.b(var1, var2, var3, 0);
            var1.setAir(var2);
            return false;
        } else {
            return true;
        }
    }

    private boolean e(World var1, BlockPosition var2) {
        return !var1.isEmpty(var2.down());
    }

    public int getDropData(IBlockData var1) {
        return ((EnumColor)var1.get(COLOR)).getColorIndex();
    }

    public IBlockData fromLegacyData(int var1) {
        return this.getBlockData().set(COLOR, EnumColor.fromColorIndex(var1));
    }

    public int toLegacyData(IBlockData var1) {
        return ((EnumColor)var1.get(COLOR)).getColorIndex();
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[]{COLOR});
    }
}
