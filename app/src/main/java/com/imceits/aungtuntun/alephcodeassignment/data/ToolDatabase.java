package com.imceits.aungtuntun.alephcodeassignment.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.imceits.aungtuntun.alephcodeassignment.data.entity.Friends;
import com.imceits.aungtuntun.alephcodeassignment.data.entity.Tools;
import com.imceits.aungtuntun.alephcodeassignment.data.entity.ToolsRent;
import com.imceits.aungtuntun.alephcodeassignment.data.pojos.ToolsDto;
import com.imceits.aungtuntun.alephcodeassignment.data.pojos.ToolsRentDto;

@Database(entities = {Friends.class, Tools.class, ToolsRent.class}, views = {ToolsRentDto.class,
        ToolsDto.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class, EnumConverter.class})
public abstract class ToolDatabase extends RoomDatabase {

    public abstract ToolsRentDao toolsRentDao();


}
