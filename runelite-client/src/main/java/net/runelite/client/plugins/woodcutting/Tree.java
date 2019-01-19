/*
 * Copyright (c) 2018, Mantautas Jurksa <https://github.com/Juzzed>
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
package net.runelite.client.plugins.woodcutting;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import static net.runelite.api.ObjectID.REDWOOD;
import static net.runelite.api.ObjectID.REDWOOD_29669;
import static net.runelite.api.ObjectID.REDWOOD_29670;
import static net.runelite.api.ObjectID.REDWOOD_29671;

@Getter
enum Tree
{
	REDWOOD_TREE_SPAWN(REDWOOD, REDWOOD_29669, REDWOOD_29670, REDWOOD_29671);

	private final int[] treeIds;

	Tree(int... treeIds)
	{
		this.treeIds = treeIds;
	}

	private static final Map<Integer, Tree> TREES = new HashMap<>();

	static
	{
		for (Tree tree : values())
		{
			for (int treeId : tree.treeIds)
			{
				TREES.put(treeId, tree);
			}
		}
	}

	static Tree findTree(int objectId)
	{
		return TREES.get(objectId);
	}
}