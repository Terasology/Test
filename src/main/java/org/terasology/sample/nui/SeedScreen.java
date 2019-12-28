/*
 * Copyright 2017 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.nui;

import org.terasology.registry.In;
import org.terasology.rendering.nui.CoreScreenLayer;
import org.terasology.rendering.nui.widgets.UIButton;
import org.terasology.rendering.nui.widgets.UIText;
import org.terasology.world.WorldProvider;

public class SeedScreen extends CoreScreenLayer {
    private UIText seedText;
    @In
    private WorldProvider provider;
    @Override
    public void initialise() {
        seedText = find("seedText", UIText.class);
        UIButton seedUpdateButton = find("seedUpdateButton", UIButton.class);
        if (seedText != null && seedUpdateButton != null) {
            seedUpdateButton.subscribe(button -> {
                seedText.setText("The seed of this world is : " + provider.getSeed());
            });
        }
    }
}
