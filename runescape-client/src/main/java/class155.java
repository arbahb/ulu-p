import java.io.File;
import net.runelite.mapping.Export;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("eg")
public class class155 {
   @ObfuscatedName("gb")
   @ObfuscatedGetter(
      intValue = -1361029035
   )
   @Export("cameraX")
   static int cameraX;
   @ObfuscatedName("t")
   public static File field2257;
   @ObfuscatedName("o")
   public static class123 field2258;
   @ObfuscatedName("z")
   public static class123 field2259;
   @ObfuscatedName("p")
   public static String field2261;
   @ObfuscatedName("l")
   public static class123 field2262;
   @ObfuscatedName("q")
   public static String field2263;

   static {
      field2258 = null;
      field2259 = null;
      field2262 = null;
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(I)[LModIcon;",
      garbageValue = "-738782061"
   )
   static ModIcon[] method2943() {
      ModIcon[] var0 = new ModIcon[class286.field3798];

      for(int var1 = 0; var1 < class286.field3798; ++var1) {
         ModIcon var2 = var0[var1] = new ModIcon();
         var2.width = class286.field3801;
         var2.originalHeight = class286.field3796;
         var2.offsetX = class286.field3799[var1];
         var2.offsetY = class286.field3800[var1];
         var2.originalWidth = class7.field239[var1];
         var2.height = class226.field3146[var1];
         var2.palette = class278.field3745;
         var2.pixels = class210.field2609[var1];
      }

      XGrandExchangeOffer.method113();
      return var0;
   }
}
