package com.imceits.aungtuntun.toolsbox.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.imceits.aungtuntun.toolsbox.R
import com.imceits.aungtuntun.toolsbox.data.entity.Friends
import com.imceits.aungtuntun.toolsbox.data.entity.Tools
import com.imceits.aungtuntun.toolsbox.data.entity.ToolsRent
import com.imceits.aungtuntun.toolsbox.data.views.ToolsDto
import com.imceits.aungtuntun.toolsbox.data.views.ToolsRentDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Tools::class, Friends::class, ToolsRent::class], views = [ToolsDto::class, ToolsRentDto::class],
    version = 1, exportSchema = false)
@TypeConverters(value = [DateConverter::class, EnumConverter::class])
abstract class ToolDatabase: RoomDatabase() {

    abstract fun toolsRentDao(): ToolsRentDao

    companion object {

        @Volatile
        private var INSTANCE: ToolDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): ToolDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance
                synchronized(this) {
                    val instance = Room.databaseBuilder(context.applicationContext,
                        ToolDatabase::class.java, "Tools.db")
                        .allowMainThreadQueries()
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                INSTANCE?.let {
                                    scope.launch {
                                        val toolsRentDao: ToolsRentDao = it.toolsRentDao()
                                        val friends: List<Friends> = getFriendList()
                                        toolsRentDao.insertFriends(friends)

                                        val tools: List<Tools> = getToolsList()
                                        toolsRentDao.insert(tools)
                                    }
                                }
                             /*   val numberOfThreads = 4
                                val executors: ExecutorService = Executors.newFixedThreadPool(numberOfThreads)
                                executors.execute {
                                    val toolsRentDao: ToolsRentDao = getInstance(context, scope).toolsRentDao()
                                    val friends: List<Friends> = getFriendList()
                                    toolsRentDao.insertFriends(friends)

                                    val tools: List<Tools> = getToolsList()
                                    toolsRentDao.insert(tools)
                                }*/
                            }
                        }).build()
                    INSTANCE = instance
                    return instance
                }
        }

        fun getToolsList(): List<Tools> {
            val names = arrayOf("Wrench", "Cutter", "Pliers", "Screwdriver", "Welding machine",
                "Welding glasses", "Hammer", "Measuring Tape", "Alan key set", "Air compressor")
            val counts = arrayOf(6, 15, 12, 13, 3, 7, 4, 9, 4, 2)
            val resIds = arrayOf(R.drawable.ic_wrench, R.drawable.ic_cutter, R.drawable.ic_plier,
                R.drawable.ic_screw_driver, R.drawable.ic_welding_machine, R.drawable.ic_welding_glass,
                R.drawable.ic_hammer, R.drawable.ic_measuring_tape, R.drawable.ic_allen_key_set, R.drawable.ic_air_compressor)
            val tools: MutableList<Tools> = mutableListOf()
            for (i in names.indices) {
                val tool = Tools(0, name = names[i], image = resIds[i].toString(), count = counts[i] )
                tools.add(tool)
            }
            return tools
        }

        fun getFriendList() : List<Friends> {
            val names = arrayOf("Brian", "Luke", "Letty", "Shaw", "Parker")
            val friendList: MutableList<Friends> = mutableListOf()
            for (name in names) {
                val friend = Friends(0, name)
                friendList.add(friend)
            }
            return friendList
        }
    }

}