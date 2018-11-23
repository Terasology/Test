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
package org.terasology.Sample.blocks;


import com.google.common.collect.ImmutableList;
import org.terasology.math.*;
import org.terasology.math.geom.Vector3i;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockBuilderHelper;
import org.terasology.world.block.BlockUri;
import org.terasology.world.block.family.*;
import org.terasology.world.block.loader.BlockFamilyDefinition;
import org.terasology.world.block.shapes.BlockShape;

@RegisterBlockFamily("Sample:Speaker")
@BlockSections({"left", "middle", "right"})
public class SpeakerFamily extends MultiConnectFamily implements UpdatesWithNeighboursFamily {
    public static final String LEFT = "left";
    public static final String MIDDLE = "middle";
    public static final String RIGHT = "right";

    ImmutableList<Rotation> Rotation1= ImmutableList.of(
            Rotation.rotate(Yaw.NONE, Pitch.NONE, Roll.NONE),
            Rotation.rotate(Yaw.CLOCKWISE_90, Pitch.NONE, Roll.NONE));

    public SpeakerFamily(BlockFamilyDefinition definition, BlockShape shape, BlockBuilderHelper blockBuilder) {
        super(definition, shape, blockBuilder);
    }

    public SpeakerFamily(BlockFamilyDefinition definition, BlockBuilderHelper blockBuilder) {
        super(definition, blockBuilder);

        BlockUri blockUri = new BlockUri(definition.getUrn());

        this.registerBlock(blockUri, definition, blockBuilder, MIDDLE, (byte) 0, Rotation.allValues());
        this.registerBlock(blockUri, definition, blockBuilder, MIDDLE, SideBitFlag.getSides(Side.LEFT, Side.RIGHT), Rotation.allValues());
        this.registerBlock(blockUri, definition, blockBuilder, LEFT, SideBitFlag.getSides(Side.RIGHT), Rotation1);
        this.registerBlock(blockUri, definition, blockBuilder, RIGHT, SideBitFlag.getSides(Side.LEFT), Rotation1);

    }
    @Override
    protected boolean connectionCondition(Vector3i blockLocation, Side connectSide) {
        Vector3i neighborLocation = new Vector3i(blockLocation);
        neighborLocation.add(connectSide.getVector3i());
        if (worldProvider.isBlockRelevant(neighborLocation)) {
            Block neighborBlock = worldProvider.getBlock(neighborLocation);
            final BlockFamily blockFamily = neighborBlock.getBlockFamily();
            if (blockFamily instanceof SpeakerFamily)
                return true;
        }
        return false;
    }

    @Override
    public byte getConnectionSides() {
        return SideBitFlag.getSides(Side.FRONT, Side.BACK, Side.LEFT, Side.RIGHT);
    }

    @Override
    public Block getArchetypeBlock() {
        return blocks.get((byte) 0);
    }

}
