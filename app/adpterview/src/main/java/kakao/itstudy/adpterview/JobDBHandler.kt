package kakao.itstudy.adpterview

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class JobDBHelper(context: Context?) :
    SQLiteOpenHelper(context, "jobdb", null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val tableSql = "create table job_data (" +
                "_id integer primary key autoincrement," +
                "name text not null," +
                "content text)"
        db.execSQL(tableSql)
        db.execSQL("insert into job_data (name, content) values ('SI','외부 시스템 개발')")
        db.execSQL("insert into job_data (name, content) values ('SM','시스템 유지 보수')")
        db.execSQL("insert into job_data (name, content) values ('QA','품질 관리 및 테스트')")
        db.execSQL("insert into job_data (name, content) values ('Back-End','서버 프로그램 개발')")
        db.execSQL("insert into job_data (name, content) values ('Front-End','클라이언트 프로그램 개발')")
        db.execSQL("insert into job_data (name, content) values ('Full-Stack','애플리케이션 전체 개발')")
        db.execSQL("insert into job_data (name, content) values ('DevOps','개발환경 과 운영환경 구축')")
    }
    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        if (newVersion == DATABASE_VERSION) {
            db.execSQL("drop table job_data")
            onCreate(db)
        }
    }
}