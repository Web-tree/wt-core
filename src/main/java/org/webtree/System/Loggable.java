package org.webtree.System;

import org.slf4j.Logger;

/**
 * @author Max Levicky
 *         Date: 17.03.14
 *         Time: 20:18
 */
public interface Loggable {
    default public Logger getLogger() {
        return Log.getInst(this.getClass());
    }
}
