package org.comixedproject.prestige.data.library

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import org.comixedproject.prestige.model.library.Library

/**
 * <code>LibraryDatabase</code> provides methods for working with persisted instances of {@link Library}.
 *
 * @author Darryl L. Pierce
 */
@Database(entities = [Library::class], version = 1, exportSchema = false)
abstract class LibraryDatabase : RoomDatabase() {
    abstract fun libraryDao(): LibraryDao

    companion object {
        @Volatile
        private var instance: LibraryDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): LibraryDatabase =
            this.instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context, LibraryDatabase::class.java,
                    "LibraryDatabase"
                ).build()
                this.instance = instance
                instance
            }
    }
}