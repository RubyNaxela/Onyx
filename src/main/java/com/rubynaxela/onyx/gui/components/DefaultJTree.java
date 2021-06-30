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
import javax.swing.tree.TreePath;
import java.util.HashMap;
import java.util.Map;

public class DefaultJTree extends JTree {

    private final DefaultMutableTreeNode root;
    private final Map<String, TreePath> paths;

    public DefaultJTree(DefaultMutableTreeNode root) {
        super(root);
        this.root = root;
        this.paths = new HashMap<>();
    }

    public DefaultMutableTreeNode getRoot() {
        return root;
    }

    public void registerPath(String key, TreePath path) {
        paths.put(key, path);
    }

    public TreePath getPath(String key) {
        return paths.get(key);
    }
}
