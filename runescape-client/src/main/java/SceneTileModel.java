import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedName;

@ObfuscatedName("dd")
@Implements("SceneTileModel")
public final class SceneTileModel {
   @ObfuscatedName("m")
   static int[] field1848;
   @ObfuscatedName("r")
   int[] field1849;
   @ObfuscatedName("a")
   int[] field1850;
   @ObfuscatedName("t")
   int[] field1851;
   @ObfuscatedName("s")
   int[] field1852;
   @ObfuscatedName("p")
   static final int[][] field1853;
   @ObfuscatedName("i")
   int[] field1854;
   @ObfuscatedName("b")
   static int[] field1855;
   @ObfuscatedName("j")
   int[] field1856;
   @ObfuscatedName("q")
   static final int[][] field1857;
   @ObfuscatedName("e")
   @Export("flatShade")
   boolean flatShade;
   @ObfuscatedName("y")
   int[] field1859;
   @ObfuscatedName("z")
   @Export("rotation")
   int rotation;
   @ObfuscatedName("l")
   @Export("underlay")
   int underlay;
   @ObfuscatedName("c")
   @Export("overlay")
   int overlay;
   @ObfuscatedName("k")
   int[] field1863;
   @ObfuscatedName("v")
   int[] field1864;
   @ObfuscatedName("f")
   static int[] field1865;
   @ObfuscatedName("n")
   static int[] field1866;
   @ObfuscatedName("u")
   static int[] field1867;
   @ObfuscatedName("w")
   int[] field1868;
   @ObfuscatedName("o")
   @Export("shape")
   int shape;

   static {
      field1848 = new int[6];
      field1855 = new int[6];
      field1865 = new int[6];
      field1866 = new int[6];
      field1867 = new int[6];
      field1853 = new int[][]{{1, 3, 5, 7}, {1, 3, 5, 7}, {1, 3, 5, 7}, {1, 3, 5, 7, 6}, {1, 3, 5, 7, 6}, {1, 3, 5, 7, 6}, {1, 3, 5, 7, 6}, {1, 3, 5, 7, 2, 6}, {1, 3, 5, 7, 2, 8}, {1, 3, 5, 7, 2, 8}, {1, 3, 5, 7, 11, 12}, {1, 3, 5, 7, 11, 12}, {1, 3, 5, 7, 13, 14}};
      field1857 = new int[][]{{0, 1, 2, 3, 0, 0, 1, 3}, {1, 1, 2, 3, 1, 0, 1, 3}, {0, 1, 2, 3, 1, 0, 1, 3}, {0, 0, 1, 2, 0, 0, 2, 4, 1, 0, 4, 3}, {0, 0, 1, 4, 0, 0, 4, 3, 1, 1, 2, 4}, {0, 0, 4, 3, 1, 0, 1, 2, 1, 0, 2, 4}, {0, 1, 2, 4, 1, 0, 1, 4, 1, 0, 4, 3}, {0, 4, 1, 2, 0, 4, 2, 5, 1, 0, 4, 5, 1, 0, 5, 3}, {0, 4, 1, 2, 0, 4, 2, 3, 0, 4, 3, 5, 1, 0, 4, 5}, {0, 0, 4, 5, 1, 4, 1, 2, 1, 4, 2, 3, 1, 4, 3, 5}, {0, 0, 1, 5, 0, 1, 4, 5, 0, 1, 2, 4, 1, 0, 5, 3, 1, 5, 4, 3, 1, 4, 2, 3}, {1, 0, 1, 5, 1, 1, 4, 5, 1, 1, 2, 4, 0, 0, 5, 3, 0, 5, 4, 3, 0, 4, 2, 3}, {1, 0, 5, 4, 1, 0, 1, 5, 0, 0, 4, 3, 0, 4, 5, 3, 0, 5, 2, 3, 0, 1, 2, 5}};
   }

   SceneTileModel(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18, int var19) {
      this.flatShade = true;
      if(var7 != var6 || var8 != var6 || var9 != var6) {
         this.flatShade = false;
      }

      this.shape = var1;
      this.rotation = var2;
      this.underlay = var18;
      this.overlay = var19;
      short var20 = 128;
      int var21 = var20 / 2;
      int var22 = var20 / 4;
      int var23 = var20 * 3 / 4;
      int[] var24 = field1853[var1];
      int var25 = var24.length;
      this.field1854 = new int[var25];
      this.field1868 = new int[var25];
      this.field1850 = new int[var25];
      int[] var26 = new int[var25];
      int[] var27 = new int[var25];
      int var28 = var20 * var4;
      int var29 = var5 * var20;

      int var31;
      int var32;
      int var33;
      int var34;
      int var35;
      int var36;
      for(int var30 = 0; var30 < var25; ++var30) {
         var31 = var24[var30];
         if((var31 & 1) == 0 && var31 <= 8) {
            var31 = (var31 - var2 - var2 - 1 & 7) + 1;
         }

         if(var31 > 8 && var31 <= 12) {
            var31 = (var31 - 9 - var2 & 3) + 9;
         }

         if(var31 > 12 && var31 <= 16) {
            var31 = (var31 - 13 - var2 & 3) + 13;
         }

         if(var31 == 1) {
            var32 = var28;
            var33 = var29;
            var34 = var6;
            var35 = var10;
            var36 = var14;
         } else if(var31 == 2) {
            var32 = var28 + var21;
            var33 = var29;
            var34 = var7 + var6 >> 1;
            var35 = var11 + var10 >> 1;
            var36 = var15 + var14 >> 1;
         } else if(var31 == 3) {
            var32 = var28 + var20;
            var33 = var29;
            var34 = var7;
            var35 = var11;
            var36 = var15;
         } else if(var31 == 4) {
            var32 = var28 + var20;
            var33 = var21 + var29;
            var34 = var8 + var7 >> 1;
            var35 = var11 + var12 >> 1;
            var36 = var15 + var16 >> 1;
         } else if(var31 == 5) {
            var32 = var28 + var20;
            var33 = var20 + var29;
            var34 = var8;
            var35 = var12;
            var36 = var16;
         } else if(var31 == 6) {
            var32 = var28 + var21;
            var33 = var20 + var29;
            var34 = var8 + var9 >> 1;
            var35 = var13 + var12 >> 1;
            var36 = var17 + var16 >> 1;
         } else if(var31 == 7) {
            var32 = var28;
            var33 = var20 + var29;
            var34 = var9;
            var35 = var13;
            var36 = var17;
         } else if(var31 == 8) {
            var32 = var28;
            var33 = var21 + var29;
            var34 = var9 + var6 >> 1;
            var35 = var13 + var10 >> 1;
            var36 = var17 + var14 >> 1;
         } else if(var31 == 9) {
            var32 = var28 + var21;
            var33 = var22 + var29;
            var34 = var7 + var6 >> 1;
            var35 = var11 + var10 >> 1;
            var36 = var15 + var14 >> 1;
         } else if(var31 == 10) {
            var32 = var28 + var23;
            var33 = var21 + var29;
            var34 = var8 + var7 >> 1;
            var35 = var11 + var12 >> 1;
            var36 = var15 + var16 >> 1;
         } else if(var31 == 11) {
            var32 = var28 + var21;
            var33 = var23 + var29;
            var34 = var8 + var9 >> 1;
            var35 = var13 + var12 >> 1;
            var36 = var17 + var16 >> 1;
         } else if(var31 == 12) {
            var32 = var22 + var28;
            var33 = var21 + var29;
            var34 = var9 + var6 >> 1;
            var35 = var13 + var10 >> 1;
            var36 = var17 + var14 >> 1;
         } else if(var31 == 13) {
            var32 = var22 + var28;
            var33 = var22 + var29;
            var34 = var6;
            var35 = var10;
            var36 = var14;
         } else if(var31 == 14) {
            var32 = var28 + var23;
            var33 = var22 + var29;
            var34 = var7;
            var35 = var11;
            var36 = var15;
         } else if(var31 == 15) {
            var32 = var28 + var23;
            var33 = var23 + var29;
            var34 = var8;
            var35 = var12;
            var36 = var16;
         } else {
            var32 = var22 + var28;
            var33 = var23 + var29;
            var34 = var9;
            var35 = var13;
            var36 = var17;
         }

         this.field1854[var30] = var32;
         this.field1868[var30] = var34;
         this.field1850[var30] = var33;
         var26[var30] = var35;
         var27[var30] = var36;
      }

      int[] var38 = field1857[var1];
      var31 = var38.length / 4;
      this.field1864 = new int[var31];
      this.field1859 = new int[var31];
      this.field1856 = new int[var31];
      this.field1851 = new int[var31];
      this.field1852 = new int[var31];
      this.field1849 = new int[var31];
      if(var3 != -1) {
         this.field1863 = new int[var31];
      }

      var32 = 0;

      for(var33 = 0; var33 < var31; ++var33) {
         var34 = var38[var32];
         var35 = var38[var32 + 1];
         var36 = var38[var32 + 2];
         int var37 = var38[var32 + 3];
         var32 += 4;
         if(var35 < 4) {
            var35 = var35 - var2 & 3;
         }

         if(var36 < 4) {
            var36 = var36 - var2 & 3;
         }

         if(var37 < 4) {
            var37 = var37 - var2 & 3;
         }

         this.field1864[var33] = var35;
         this.field1859[var33] = var36;
         this.field1856[var33] = var37;
         if(var34 == 0) {
            this.field1851[var33] = var26[var35];
            this.field1852[var33] = var26[var36];
            this.field1849[var33] = var26[var37];
            if(this.field1863 != null) {
               this.field1863[var33] = -1;
            }
         } else {
            this.field1851[var33] = var27[var35];
            this.field1852[var33] = var27[var36];
            this.field1849[var33] = var27[var37];
            if(this.field1863 != null) {
               this.field1863[var33] = var3;
            }
         }
      }

      var33 = var6;
      var34 = var7;
      if(var7 < var6) {
         var33 = var7;
      }

      if(var7 > var7) {
         var34 = var7;
      }

      if(var8 < var33) {
         var33 = var8;
      }

      if(var8 > var34) {
         var34 = var8;
      }

      if(var9 < var33) {
         var33 = var9;
      }

      if(var9 > var34) {
         var34 = var9;
      }

      var33 /= 14;
      var34 /= 14;
   }
}
