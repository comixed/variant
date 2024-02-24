package org.comixedproject.variant.data

import platform.Foundation.NSUUID

/**
 * <code>IdGenerator</code> provides a type that generates unique ids.
 *
 * @author Darryl L. Pierce
 */
actual class IdGenerator actual constructor() {
    private val value = NSUUID()
    actual override fun toString() = value.UUIDString
}