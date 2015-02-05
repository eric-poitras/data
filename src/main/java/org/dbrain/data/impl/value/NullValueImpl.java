/*
 * Copyright [2015] [Eric Poitras]
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package org.dbrain.data.impl.value;

import org.dbrain.data.DataCoercionException;
import org.dbrain.data.Value;

/**
 * Wrap a scalar value;
 */
public final class NullValueImpl implements Value {

    public static final Value NULL = new NullValueImpl();

    private NullValueImpl() {
    }

    public Object getObject() {
        return null;
    }

    @Override
    public MapValueImpl getMap() {
        throw new DataCoercionException( "Cannot cast null to Map." );
    }

    @Override
    public ListValueImpl getList() {
        throw new DataCoercionException( "Cannot cast null to List." );
    }

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        return true;

    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "null";
    }
}
