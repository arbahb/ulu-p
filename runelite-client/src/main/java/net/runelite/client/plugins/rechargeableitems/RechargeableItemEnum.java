package net.runelite.client.plugins.rechargeableitems;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import net.runelite.api.ItemID;

enum RechargeableItemEnum
{
	ARCLIGHT(ItemID.ARCLIGHT),
	/**
	 * The Barrows items without _[number] at the end are in perfect condition, thus not needed in this plugin
	 */
	// Helm
	BARROWS_AHRIMS_HOOD_0(ItemID.AHRIMS_HOOD_0),
	BARROWS_AHRIMS_HOOD_25(ItemID.AHRIMS_HOOD_25),
	BARROWS_AHRIMS_HOOD_50(ItemID.AHRIMS_HOOD_50),
	BARROWS_AHRIMS_HOOD_75(ItemID.AHRIMS_HOOD_75),
	BARROWS_AHRIMS_HOOD_100(ItemID.AHRIMS_HOOD_100),
	BARROWS_DHAROKS_HELM_0(ItemID.DHAROKS_HELM_0),
	BARROWS_DHAROKS_HELM_25(ItemID.DHAROKS_HELM_25),
	BARROWS_DHAROKS_HELM_50(ItemID.DHAROKS_HELM_50),
	BARROWS_DHAROKS_HELM_75(ItemID.DHAROKS_HELM_75),
	BARROWS_DHAROKS_HELM_100(ItemID.DHAROKS_HELM_100),
	BARROWS_GUTHANS_HELM_0(ItemID.GUTHANS_HELM_0),
	BARROWS_GUTHANS_HELM_25(ItemID.GUTHANS_HELM_25),
	BARROWS_GUTHANS_HELM_50(ItemID.GUTHANS_HELM_50),
	BARROWS_GUTHANS_HELM_75(ItemID.GUTHANS_HELM_75),
	BARROWS_GUTHANS_HELM_100(ItemID.GUTHANS_HELM_100),
	BARROWS_KARILS_COIF_0(ItemID.KARILS_COIF_0),
	BARROWS_KARILS_COIF_25(ItemID.KARILS_COIF_25),
	BARROWS_KARILS_COIF_50(ItemID.KARILS_COIF_50),
	BARROWS_KARILS_COIF_75(ItemID.KARILS_COIF_75),
	BARROWS_KARILS_COIF_100(ItemID.KARILS_COIF_100),
	BARROWS_TORAGS_HELM_0(ItemID.TORAGS_HELM_0),
	BARROWS_TORAGS_HELM_25(ItemID.TORAGS_HELM_25),
	BARROWS_TORAGS_HELM_50(ItemID.TORAGS_HELM_50),
	BARROWS_TORAGS_HELM_75(ItemID.TORAGS_HELM_75),
	BARROWS_TORAGS_HELM_100(ItemID.TORAGS_HELM_100),
	BARROWS_VERACS_HELM_0(ItemID.VERACS_HELM_0),
	BARROWS_VERACS_HELM_25(ItemID.VERACS_HELM_25),
	BARROWS_VERACS_HELM_50(ItemID.VERACS_HELM_50),
	BARROWS_VERACS_HELM_75(ItemID.VERACS_HELM_75),
	BARROWS_VERACS_HELM_100(ItemID.VERACS_HELM_100),
	// Body
	BARROWS_AHRIMS_ROBETOP_0(ItemID.AHRIMS_ROBETOP_0),
	BARROWS_AHRIMS_ROBETOP_25(ItemID.AHRIMS_ROBETOP_25),
	BARROWS_AHRIMS_ROBETOP_50(ItemID.AHRIMS_ROBETOP_50),
	BARROWS_AHRIMS_ROBETOP_75(ItemID.AHRIMS_ROBETOP_75),
	BARROWS_AHRIMS_ROBETOP_100(ItemID.AHRIMS_ROBETOP_100),
	BARROWS_DHAROKS_PLATEBODY_0(ItemID.DHAROKS_PLATEBODY_0),
	BARROWS_DHAROKS_PLATEBODY_25(ItemID.DHAROKS_PLATEBODY_25),
	BARROWS_DHAROKS_PLATEBODY_50(ItemID.DHAROKS_PLATEBODY_50),
	BARROWS_DHAROKS_PLATEBODY_75(ItemID.DHAROKS_PLATEBODY_75),
	BARROWS_DHAROKS_PLATEBODY_100(ItemID.DHAROKS_PLATEBODY_100),
	BARROWS_GUTHANS_PLATEBODY_0(ItemID.GUTHANS_PLATEBODY_0),
	BARROWS_GUTHANS_PLATEBODY_25(ItemID.GUTHANS_PLATEBODY_25),
	BARROWS_GUTHANS_PLATEBODY_50(ItemID.GUTHANS_PLATEBODY_50),
	BARROWS_GUTHANS_PLATEBODY_75(ItemID.GUTHANS_PLATEBODY_75),
	BARROWS_GUTHANS_PLATEBODY_100(ItemID.GUTHANS_PLATEBODY_100),
	BARROWS_KARILS_LEATHERTOP_0(ItemID.KARILS_LEATHERTOP_0),
	BARROWS_KARILS_LEATHERTOP_25(ItemID.KARILS_LEATHERTOP_25),
	BARROWS_KARILS_LEATHERTOP_50(ItemID.KARILS_LEATHERTOP_50),
	BARROWS_KARILS_LEATHERTOP_75(ItemID.KARILS_LEATHERTOP_75),
	BARROWS_KARILS_LEATHERTOP_100(ItemID.KARILS_LEATHERTOP_100),
	BARROWS_TORAGS_PLATEBODY_0(ItemID.TORAGS_PLATEBODY_0),
	BARROWS_TORAGS_PLATEBODY_25(ItemID.TORAGS_PLATEBODY_25),
	BARROWS_TORAGS_PLATEBODY_50(ItemID.TORAGS_PLATEBODY_50),
	BARROWS_TORAGS_PLATEBODY_75(ItemID.TORAGS_PLATEBODY_75),
	BARROWS_TORAGS_PLATEBODY_100(ItemID.TORAGS_PLATEBODY_100),
	BARROWS_VERACS_BRASSARD_0(ItemID.VERACS_BRASSARD_0),
	BARROWS_VERACS_BRASSARD_25(ItemID.VERACS_BRASSARD_25),
	BARROWS_VERACS_BRASSARD_50(ItemID.VERACS_BRASSARD_50),
	BARROWS_VERACS_BRASSARD_75(ItemID.VERACS_BRASSARD_75),
	BARROWS_VERACS_BRASSARD_100(ItemID.VERACS_BRASSARD_100),
	// Legs
	BARROWS_AHRIMS_ROBESKIRT_0(ItemID.AHRIMS_ROBESKIRT_0),
	BARROWS_AHRIMS_ROBESKIRT_25(ItemID.AHRIMS_ROBESKIRT_25),
	BARROWS_AHRIMS_ROBESKIRT_50(ItemID.AHRIMS_ROBESKIRT_50),
	BARROWS_AHRIMS_ROBESKIRT_75(ItemID.AHRIMS_ROBESKIRT_75),
	BARROWS_AHRIMS_ROBESKIRT_100(ItemID.AHRIMS_ROBESKIRT_100),
	BARROWS_DHAROKS_PLATELEGS_0(ItemID.DHAROKS_PLATELEGS_0),
	BARROWS_DHAROKS_PLATELEGS_25(ItemID.DHAROKS_PLATELEGS_25),
	BARROWS_DHAROKS_PLATELEGS_50(ItemID.DHAROKS_PLATELEGS_50),
	BARROWS_DHAROKS_PLATELEGS_75(ItemID.DHAROKS_PLATELEGS_75),
	BARROWS_DHAROKS_PLATELEGS_100(ItemID.DHAROKS_PLATELEGS_100),
	BARROWS_GUTHANS_CHAINSKIRT_0(ItemID.GUTHANS_CHAINSKIRT_0),
	BARROWS_GUTHANS_CHAINSKIRT_25(ItemID.GUTHANS_CHAINSKIRT_25),
	BARROWS_GUTHANS_CHAINSKIRT_50(ItemID.GUTHANS_CHAINSKIRT_50),
	BARROWS_GUTHANS_CHAINSKIRT_75(ItemID.GUTHANS_CHAINSKIRT_75),
	BARROWS_GUTHANS_CHAINSKIRT_100(ItemID.GUTHANS_CHAINSKIRT_100),
	BARROWS_KARILS_LEATHERSKIRT_0(ItemID.KARILS_LEATHERSKIRT_0),
	BARROWS_KARILS_LEATHERSKIRT_25(ItemID.KARILS_LEATHERSKIRT_25),
	BARROWS_KARILS_LEATHERSKIRT_50(ItemID.KARILS_LEATHERSKIRT_50),
	BARROWS_KARILS_LEATHERSKIRT_75(ItemID.KARILS_LEATHERSKIRT_75),
	BARROWS_KARILS_LEATHERSKIRT_100(ItemID.KARILS_LEATHERSKIRT_100),
	BARROWS_TORAGS_PLATELEGS_0(ItemID.TORAGS_PLATELEGS_0),
	BARROWS_TORAGS_PLATELEGS_25(ItemID.TORAGS_PLATELEGS_25),
	BARROWS_TORAGS_PLATELEGS_50(ItemID.TORAGS_PLATELEGS_50),
	BARROWS_TORAGS_PLATELEGS_75(ItemID.TORAGS_PLATELEGS_75),
	BARROWS_TORAGS_PLATELEGS_100(ItemID.TORAGS_PLATELEGS_100),
	BARROWS_VERACS_PLATESKIRT_0(ItemID.VERACS_PLATESKIRT_0),
	BARROWS_VERACS_PLATESKIRT_25(ItemID.VERACS_PLATESKIRT_25),
	BARROWS_VERACS_PLATESKIRT_50(ItemID.VERACS_PLATESKIRT_50),
	BARROWS_VERACS_PLATESKIRT_75(ItemID.VERACS_PLATESKIRT_75),
	BARROWS_VERACS_PLATESKIRT_100(ItemID.VERACS_PLATESKIRT_100),
	// WEAPONS
	BARROWS_AHRIMS_STAFF_0(ItemID.AHRIMS_STAFF_0),
	BARROWS_AHRIMS_STAFF_25(ItemID.AHRIMS_STAFF_25),
	BARROWS_AHRIMS_STAFF_50(ItemID.AHRIMS_STAFF_50),
	BARROWS_AHRIMS_STAFF_75(ItemID.AHRIMS_STAFF_75),
	BARROWS_AHRIMS_STAFF_100(ItemID.AHRIMS_STAFF_100),
	BARROWS_DHAROKS_GREATEAXE_0(ItemID.DHAROKS_GREATAXE_0),
	BARROWS_DHAROKS_GREATEAXE_25(ItemID.DHAROKS_GREATAXE_25),
	BARROWS_DHAROKS_GREATEAXE_50(ItemID.DHAROKS_GREATAXE_50),
	BARROWS_DHAROKS_GREATEAXE_75(ItemID.DHAROKS_GREATAXE_75),
	BARROWS_DHAROKS_GREATEAXE_100(ItemID.DHAROKS_GREATAXE_100),
	BARROWS_GUTHANS_WARSPEAR_0(ItemID.GUTHANS_WARSPEAR_0),
	BARROWS_GUTHANS_WARSPEAR_25(ItemID.GUTHANS_WARSPEAR_25),
	BARROWS_GUTHANS_WARSPEAR_50(ItemID.GUTHANS_WARSPEAR_50),
	BARROWS_GUTHANS_WARSPEAR_75(ItemID.GUTHANS_WARSPEAR_75),
	BARROWS_GUTHANS_WARSPEAR_100(ItemID.GUTHANS_WARSPEAR_100),
	BARROWS_KARILS_CROSSBOW_0(ItemID.KARILS_CROSSBOW_0),
	BARROWS_KARILS_CROSSBOW_25(ItemID.KARILS_CROSSBOW_25),
	BARROWS_KARILS_CROSSBOW_50(ItemID.KARILS_CROSSBOW_50),
	BARROWS_KARILS_CROSSBOW_75(ItemID.KARILS_CROSSBOW_75),
	BARROWS_KARILS_CROSSBOW_100(ItemID.KARILS_CROSSBOW_100),
	BARROWS_TORAGS_HAMMERS_0(ItemID.TORAGS_HAMMERS_0),
	BARROWS_TORAGS_HAMMERS_25(ItemID.TORAGS_HAMMERS_25),
	BARROWS_TORAGS_HAMMERS_50(ItemID.TORAGS_HAMMERS_50),
	BARROWS_TORAGS_HAMMERS_75(ItemID.TORAGS_HAMMERS_75),
	BARROWS_TORAGS_HAMMERS_100(ItemID.TORAGS_HAMMERS_100),
	BARROWS_VERACS_FLAIL_0(ItemID.VERACS_FLAIL_0),
	BARROWS_VERACS_FLAIL_25(ItemID.VERACS_FLAIL_25),
	BARROWS_VERACS_FLAIL_50(ItemID.VERACS_FLAIL_50),
	BARROWS_VERACS_FLAIL_75(ItemID.VERACS_FLAIL_75),
	BARROWS_VERACS_FLAIL_100(ItemID.VERACS_FLAIL_100),
	/**
	 * End of barrows
	 */
	CRYSTAL_BOW(ItemID.CRYSTAL_BOW),
	CRYSTAL_SHIELD(ItemID.CRYSTAL_SHIELD),
	SANGUINESTI_STAFF(ItemID.SANGUINESTI_STAFF),
	SANGUINESTI_STAFF_UNCHARGED(ItemID.SANGUINESTI_STAFF_UNCHARGED),
	SCYTHE_OF_VITUR(ItemID.SCYTHE_OF_VITUR),
	SCYTHE_OF_VITUR_22664(ItemID.SCYTHE_OF_VITUR_22664),
	SCYTHE_OF_VITUR_UNCHARGED(ItemID.SCYTHE_OF_VITUR_UNCHARGED),
	TRIDENT_OF_THE_SEAS(ItemID.TRIDENT_OF_THE_SEAS),
	TRIDENT_OF_THE_SEAS_E(ItemID.TRIDENT_OF_THE_SEAS_E),
	TRIDENT_OF_THE_SEAS_FULL(ItemID.TRIDENT_OF_THE_SEAS_FULL),
	TRIDENT_OF_THE_SWAMP(ItemID.TRIDENT_OF_THE_SWAMP),
	TRIDENT_OF_THE_SWAMP_E(ItemID.TRIDENT_OF_THE_SWAMP_E),
	;

	private static final Map<Integer, RechargeableItemEnum> lookup = new HashMap<>();

	static
	{
		for (RechargeableItemEnum item : RechargeableItemEnum.values())
		{
			lookup.put(item.getId(), item);
		}
	}

	@Getter
	private final int id;

	RechargeableItemEnum(int id)
	{
		this.id = id;
	}

	public static boolean containsItem(int itemId)
	{
		return lookup.containsKey(itemId);
	}

	public static RechargeableItemEnum getRechargeableItemEnum(int itemId)
	{
		return lookup.get(itemId);
	}
}
