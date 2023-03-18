package org.comixedproject.prestige.data.library

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import org.comixedproject.prestige.model.library.Library

/**
 * <code>LibraryDao</code> provides methods to store and retrieve instances of {@link Library}.
 *
 * @author Darryl L. Pierce
 */
@Dao
abstract class LibraryDao {
    @Query(
        """
        SELECT * FROM Libaries
        """
    )
    abstract fun loadAll()

    @Insert
    abstract suspend fun create(library: Library): Long

    @Update
    abstract suspend fun update(library: Library)
}