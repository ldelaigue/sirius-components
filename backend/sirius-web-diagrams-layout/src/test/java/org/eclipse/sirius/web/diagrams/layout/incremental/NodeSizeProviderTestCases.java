/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.web.diagrams.layout.incremental;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.provider.NodeSizeProvider;
import org.eclipse.sirius.web.diagrams.tests.TestDiagramBuilder;
import org.junit.Test;

/**
 * Test cases for {@link NodeSizeProvider}.
 *
 * @author fbarbin
 */
public class NodeSizeProviderTestCases {

    private static final int HEIGHT_70 = 70;

    private static final int WIDTH_150 = 150;

    private static final int HEIGHT_50 = 50;

    private static final int WIDTH_80 = 80;

    @Test
    public void testNodeSize() {
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider();
        Size size = nodeSizeProvider.getSize(this.createNodeLayoutData(Size.UNDEFINED));
        assertThat(size).extracting(Size::getHeight).isEqualTo(Double.valueOf(HEIGHT_70));
        assertThat(size).extracting(Size::getWidth).isEqualTo(Double.valueOf(WIDTH_150));
    }

    @Test
    public void testNodeSizeWithExistingSize() {
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider();
        Size size = nodeSizeProvider.getSize(this.createNodeLayoutData(Size.of(WIDTH_80, HEIGHT_50)));
        assertThat(size).extracting(Size::getHeight).isEqualTo(Double.valueOf(HEIGHT_50));
        assertThat(size).extracting(Size::getWidth).isEqualTo(Double.valueOf(WIDTH_80));
    }

    private NodeLayoutData createNodeLayoutData(Size size) {
        TestDiagramBuilder testDiagramBuilder = new TestDiagramBuilder();

        NodeLayoutData nodeLayoutData = new NodeLayoutData();
        nodeLayoutData.setSize(size);
        nodeLayoutData.setStyle(testDiagramBuilder.getRectangularNodeStyle());
        return nodeLayoutData;
    }
}