import java.io.EOFException;
import java.io.IOException;
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ff")
@Implements("IndexFile")
public final class IndexFile {
   @ObfuscatedName("x")
   static byte[] field2181;
   @ObfuscatedName("d")
   @ObfuscatedSignature(
      signature = "Ldl;"
   )
   @Export("indexFile")
   CacheFile indexFile;
   @ObfuscatedName("k")
   @ObfuscatedSignature(
      signature = "Ldl;"
   )
   @Export("dataFile")
   CacheFile dataFile;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = -228559831
   )
   @Export("index")
   int index;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -69742423
   )
   @Export("maxSize")
   int maxSize;

   static {
      field2181 = new byte[520];
   }

   @ObfuscatedSignature(
      signature = "(ILdl;Ldl;I)V"
   )
   public IndexFile(int var1, CacheFile var2, CacheFile var3, int var4) {
      this.dataFile = null;
      this.indexFile = null;
      this.maxSize = 65000;
      this.index = var1;
      this.dataFile = var2;
      this.indexFile = var3;
      this.maxSize = var4;
   }

   @ObfuscatedName("d")
   @ObfuscatedSignature(
      signature = "(II)[B",
      garbageValue = "-1309257172"
   )
   public byte[] method3136(int var1) {
      CacheFile var2 = this.dataFile;
      synchronized(this.dataFile) {
         try {
            Object var10000;
            if(this.indexFile.method2429() < (long)(var1 * 6 + 6)) {
               var10000 = null;
               return (byte[])var10000;
            } else {
               this.indexFile.seek((long)(var1 * 6));
               this.indexFile.method2431(field2181, 0, 6);
               int var3 = ((field2181[0] & 255) << 16) + (field2181[2] & 255) + ((field2181[1] & 255) << 8);
               int var4 = (field2181[5] & 255) + ((field2181[3] & 255) << 16) + ((field2181[4] & 255) << 8);
               if(var3 < 0 || var3 > this.maxSize) {
                  var10000 = null;
                  return (byte[])var10000;
               } else if(var4 <= 0 || (long)var4 > this.dataFile.method2429() / 520L) {
                  var10000 = null;
                  return (byte[])var10000;
               } else {
                  byte[] var5 = new byte[var3];
                  int var6 = 0;

                  for(int var7 = 0; var6 < var3; ++var7) {
                     if(var4 == 0) {
                        var10000 = null;
                        return (byte[])var10000;
                     }

                     this.dataFile.seek((long)(var4 * 520));
                     int var8 = var3 - var6;
                     if(var8 > 512) {
                        var8 = 512;
                     }

                     this.dataFile.method2431(field2181, 0, var8 + 8);
                     int var9 = (field2181[1] & 255) + ((field2181[0] & 255) << 8);
                     int var10 = (field2181[3] & 255) + ((field2181[2] & 255) << 8);
                     int var11 = ((field2181[5] & 255) << 8) + ((field2181[4] & 255) << 16) + (field2181[6] & 255);
                     int var12 = field2181[7] & 255;
                     if(var9 != var1 || var7 != var10 || var12 != this.index) {
                        var10000 = null;
                        return (byte[])var10000;
                     }

                     if(var11 < 0 || (long)var11 > this.dataFile.method2429() / 520L) {
                        var10000 = null;
                        return (byte[])var10000;
                     }

                     for(int var13 = 0; var13 < var8; ++var13) {
                        var5[var6++] = field2181[var13 + 8];
                     }

                     var4 = var11;
                  }

                  byte[] var18 = var5;
                  return var18;
               }
            }
         } catch (IOException var16) {
            return null;
         }
      }
   }

   @ObfuscatedName("x")
   @ObfuscatedSignature(
      signature = "(I[BII)Z",
      garbageValue = "-1219863226"
   )
   public boolean method3137(int var1, byte[] var2, int var3) {
      CacheFile var4 = this.dataFile;
      synchronized(this.dataFile) {
         if(var3 >= 0 && var3 <= this.maxSize) {
            boolean var5 = this.method3138(var1, var2, var3, true);
            if(!var5) {
               var5 = this.method3138(var1, var2, var3, false);
            }

            return var5;
         } else {
            throw new IllegalArgumentException();
         }
      }
   }

   @ObfuscatedName("k")
   @ObfuscatedSignature(
      signature = "(I[BIZI)Z",
      garbageValue = "-1437699907"
   )
   boolean method3138(int var1, byte[] var2, int var3, boolean var4) {
      CacheFile var5 = this.dataFile;
      synchronized(this.dataFile) {
         try {
            int var6;
            boolean var10000;
            if(var4) {
               if(this.indexFile.method2429() < (long)(var1 * 6 + 6)) {
                  var10000 = false;
                  return var10000;
               }

               this.indexFile.seek((long)(var1 * 6));
               this.indexFile.method2431(field2181, 0, 6);
               var6 = (field2181[5] & 255) + ((field2181[3] & 255) << 16) + ((field2181[4] & 255) << 8);
               if(var6 <= 0 || (long)var6 > this.dataFile.method2429() / 520L) {
                  var10000 = false;
                  return var10000;
               }
            } else {
               var6 = (int)((this.dataFile.method2429() + 519L) / 520L);
               if(var6 == 0) {
                  var6 = 1;
               }
            }

            field2181[0] = (byte)(var3 >> 16);
            field2181[1] = (byte)(var3 >> 8);
            field2181[2] = (byte)var3;
            field2181[3] = (byte)(var6 >> 16);
            field2181[4] = (byte)(var6 >> 8);
            field2181[5] = (byte)var6;
            this.indexFile.seek((long)(var1 * 6));
            this.indexFile.write(field2181, 0, 6);
            int var7 = 0;
            int var8 = 0;

            while(true) {
               if(var7 < var3) {
                  label142: {
                     int var9 = 0;
                     int var10;
                     if(var4) {
                        this.dataFile.seek((long)(var6 * 520));

                        try {
                           this.dataFile.method2431(field2181, 0, 8);
                        } catch (EOFException var16) {
                           break label142;
                        }

                        var10 = (field2181[1] & 255) + ((field2181[0] & 255) << 8);
                        int var11 = (field2181[3] & 255) + ((field2181[2] & 255) << 8);
                        var9 = ((field2181[5] & 255) << 8) + ((field2181[4] & 255) << 16) + (field2181[6] & 255);
                        int var12 = field2181[7] & 255;
                        if(var10 != var1 || var8 != var11 || var12 != this.index) {
                           var10000 = false;
                           return var10000;
                        }

                        if(var9 < 0 || (long)var9 > this.dataFile.method2429() / 520L) {
                           var10000 = false;
                           return var10000;
                        }
                     }

                     if(var9 == 0) {
                        var4 = false;
                        var9 = (int)((this.dataFile.method2429() + 519L) / 520L);
                        if(var9 == 0) {
                           ++var9;
                        }

                        if(var6 == var9) {
                           ++var9;
                        }
                     }

                     if(var3 - var7 <= 512) {
                        var9 = 0;
                     }

                     field2181[0] = (byte)(var1 >> 8);
                     field2181[1] = (byte)var1;
                     field2181[2] = (byte)(var8 >> 8);
                     field2181[3] = (byte)var8;
                     field2181[4] = (byte)(var9 >> 16);
                     field2181[5] = (byte)(var9 >> 8);
                     field2181[6] = (byte)var9;
                     field2181[7] = (byte)this.index;
                     this.dataFile.seek((long)(var6 * 520));
                     this.dataFile.write(field2181, 0, 8);
                     var10 = var3 - var7;
                     if(var10 > 512) {
                        var10 = 512;
                     }

                     this.dataFile.write(var2, var7, var10);
                     var7 += var10;
                     var6 = var9;
                     ++var8;
                     continue;
                  }
               }

               var10000 = true;
               return var10000;
            }
         } catch (IOException var17) {
            return false;
         }
      }
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(ILid;Ljava/lang/String;Ljava/lang/String;IZI)V",
      garbageValue = "1084065139"
   )
   public static void method3146(int var0, IndexDataBase var1, String var2, String var3, int var4, boolean var5) {
      int var6 = var1.getFile(var2);
      int var7 = var1.getChild(var6, var3);
      class210.field2598 = 1;
      class3.field22 = var1;
      class8.field237 = var6;
      class210.field2599 = var7;
      class111.field1588 = var4;
      class20.field322 = var5;
      class164.field2216 = var0;
   }
}
