/*
 * Copyright (c) 2018, Robb <rla@navadrag.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.keyremapping;

import java.awt.event.KeyEvent;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ModifierlessKeybind;

@ConfigGroup("keyremapping")
public interface KeyRemappingConfig extends Config
{

	@ConfigItem(
			keyName = "functionKeyRemap",
			name = "Function Key Remapping",
			description = "Configures whether Function Keys are Remapped to 1 through 0 (F10) and - + (F11, F12)",
			position = 1
	)
	default boolean functionKeyRemap()
	{
		return true;
	}


	@ConfigItem(
			keyName = "wasdCameraRemap",
			name = "WASD Camera",
			description = "Configures whether the camera movement is remapped to the WASD keys",
			position = 2
	)
	default boolean wasdCameraRemap()
	{
		return true;
	}

	@ConfigItem(
		position = 3,
		keyName = "up",
		name = "Camera Up key",
		description = "The key which will replace up."
	)
	default ModifierlessKeybind up()
	{
		return new ModifierlessKeybind(KeyEvent.VK_W, 0);
	}

	@ConfigItem(
		position = 4,
		keyName = "down",
		name = "Camera Down key",
		description = "The key which will replace down."
	)
	default ModifierlessKeybind down()
	{
		return new ModifierlessKeybind(KeyEvent.VK_S, 0);
	}

	@ConfigItem(
		position = 5,
		keyName = "left",
		name = "Camera Left key",
		description = "The key which will replace left."
	)
	default ModifierlessKeybind left()
	{
		return new ModifierlessKeybind(KeyEvent.VK_A, 0);
	}

	@ConfigItem(
		position = 6,
		keyName = "right",
		name = "Camera Right key",
		description = "The key which will replace right."
	)
	default ModifierlessKeybind right()
	{
		return new ModifierlessKeybind(KeyEvent.VK_D, 0);
	}

}
