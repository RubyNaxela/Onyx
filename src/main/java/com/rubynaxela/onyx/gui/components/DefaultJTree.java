/*
 * Copyright (c) 2021 RubyNaxela
 * All Rights Reserved
 *
 * This file is part of the Onyx project.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 *
 * Written by Jacek Pawelski <rubynaxela@gmail.com>
 */

package com.rubynaxela.onyx.gui.components;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class DefaultJTree extends JTree {

    private final DefaultMutableTreeNode root;

    /**
     * Returns a {@link JTree} with the specified {@link DefaultMutableTreeNode} as its root, which displays the root node.
     * By default, the tree defines a leaf node as any node without children
     *
     * @param root a {@link DefaultMutableTreeNode} object, the tree root
     */
    public DefaultJTree(DefaultMutableTreeNode root) {
        super(root);
        this.root = root;
    }

    public DefaultMutableTreeNode getRoot() {
        return root;
    }
}
