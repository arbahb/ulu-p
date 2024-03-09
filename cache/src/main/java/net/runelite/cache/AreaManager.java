 /*
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
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

package net.runelite.cache;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.runelite.cache.definitions.AreaDefinition;
import net.runelite.cache.definitions.loaders.AreaLoader;
import net.runelite.cache.fs.Archive;
import net.runelite.cache.fs.ArchiveFiles;
import net.runelite.cache.fs.FSFile;
import net.runelite.cache.fs.Index;
import net.runelite.cache.fs.Storage;
import net.runelite.cache.fs.Store;

public class AreaManager {
    private static final Logger LOGGER = Logger.getLogger(AreaManager.class.getName());
    private final Store store;
    private final Map<Integer, AreaDefinition> areas = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public AreaManager(Store store) {
        this.store = store;
    }

    public void load() throws IOException, InterruptedException {
        Storage storage = store.getStorage();
        Index index = store.getIndex(IndexType.CONFIGS);
        Archive archive = index.getArchive(ConfigType.AREA.getId());

        byte[] archiveData = storage.loadArchive(archive);
        ArchiveFiles files = archive.getFiles(archiveData);

        CompletableFuture<?>[] futures = files.getFiles().stream()
            .map(file -> CompletableFuture.runAsync(() -> {
                try {
                    loadAreaDefinition(file);
                } catch (IOException e) {
                    LOGGER.log(Level.WARNING, "Failed to load area definition: " + file.getFileId(), e);
                }
            }, executorService))
            .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures).join();
        executorService.shutdown();
    }

    private void loadAreaDefinition(FSFile file) throws IOException {
        AreaLoader loader = new AreaLoader();
        AreaDefinition area = loader.load(file.getContents(), file.getFileId());
        areas.put(area.id, area);
    }

    public Collection<AreaDefinition> getAreas() {
        return Collections.unmodifiableCollection(areas.values());
    }

    public AreaDefinition getArea(int areaId) {
        return areas.get(areaId);
    }
}
