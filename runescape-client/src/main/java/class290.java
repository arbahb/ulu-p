import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("km")
public class class290 {
   @ObfuscatedName("t")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/CharSequence;I)Ljava/lang/String;",
      garbageValue = "425058106"
   )
   public static String method5208(CharSequence var0) {
      String var1 = class252.decodeBase37(class231.encodeBase37(var0));
      if(var1 == null) {
         var1 = "";
      }

      return var1;
   }
}
