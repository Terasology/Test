// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.sample.blocks;

import org.terasology.engine.math.Rotation;
import org.terasology.engine.math.Side;
import org.terasology.engine.math.SideBitFlag;
import org.terasology.engine.world.block.Block;
import org.terasology.engine.world.block.BlockBuilderHelper;
import org.terasology.engine.world.block.BlockUri;
import org.terasology.engine.world.block.family.BlockSections;
import org.terasology.engine.world.block.family.MultiConnectFamily;
import org.terasology.engine.world.block.family.RegisterBlockFamily;
import org.terasology.engine.world.block.family.UpdatesWithNeighboursFamily;
import org.terasology.engine.world.block.loader.BlockFamilyDefinition;
import org.terasology.engine.world.block.shapes.BlockShape;
import org.terasology.gestalt.naming.Name;
import org.terasology.math.geom.Vector3i;

@RegisterBlockFamily("sample:NetworkCable")
@BlockSections({"one_connection", "line_connection", "2d_corner", "3d_corner", "2d_t", "cross", "3d_side",
        "five_connections"})
public class NetworkCableFamily extends MultiConnectFamily implements UpdatesWithNeighboursFamily {

    public NetworkCableFamily(BlockFamilyDefinition definition, BlockShape shape, BlockBuilderHelper builderHelper) {
        super(definition, shape, builderHelper);
    }

    public NetworkCableFamily(BlockFamilyDefinition definition, BlockBuilderHelper builderHelper) {
        super(definition, builderHelper);

        BlockUri blockUri = new BlockUri(definition.getUrn());

        Block block = builderHelper.constructSimpleBlock(definition, new BlockUri(blockUri,
                new Name(String.valueOf(0))), this);
        this.blocks.put((byte) 0, block);

//      this.registerBlock(blockUri, definition, builderHelper, "one_connection", (byte) 0, Rotation.allValues());
        this.registerBlock(blockUri, definition, builderHelper, "one_connection",
                SideBitFlag.getSides(Side.BACK), Rotation.allValues());
        this.registerBlock(blockUri, definition, builderHelper, "line_connection",
                SideBitFlag.getSides(Side.BACK, Side.FRONT), Rotation.allValues());
        this.registerBlock(blockUri, definition, builderHelper, "2d_corner",
                SideBitFlag.getSides(Side.LEFT, Side.BACK), Rotation.allValues());
        this.registerBlock(blockUri, definition, builderHelper, "3d_corner",
                SideBitFlag.getSides(Side.LEFT, Side.BACK, Side.TOP), Rotation.allValues());
        this.registerBlock(blockUri, definition, builderHelper, "2d_t",
                SideBitFlag.getSides(Side.LEFT, Side.BACK, Side.FRONT), Rotation.allValues());
        this.registerBlock(blockUri, definition, builderHelper, "cross",
                SideBitFlag.getSides(Side.RIGHT, Side.LEFT, Side.BACK, Side.FRONT), Rotation.allValues());
        this.registerBlock(blockUri, definition, builderHelper, "3d_side",
                SideBitFlag.getSides(Side.LEFT, Side.BACK, Side.FRONT, Side.TOP), Rotation.allValues());
        this.registerBlock(blockUri, definition, builderHelper, "five_connections",
                SideBitFlag.getSides(Side.LEFT, Side.BACK, Side.FRONT, Side.TOP, Side.BOTTOM), Rotation.allValues());

    }

    @Override
    public byte getConnectionSides() {
        return 63;
    }

    @Override
    public Block getArchetypeBlock() {
        return blocks.get((byte) 0);
    }

    @Override
    protected boolean connectionCondition(Vector3i location, Side side) {
        Vector3i target = side.getAdjacentPos(location);
        return worldProvider.isBlockRelevant(target) && worldProvider.getBlock(target).getBlockFamily() instanceof NetworkCableFamily;
    }
}
